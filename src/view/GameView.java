package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import controller.SoundController;
import model.GameStateModel;
import model.PlayerModel;

//author: L2P-Academy

public class GameView extends JFrame {
	// final attributes for map design
	private static final int ROWS = 16;
	private static final int COLS = 32;
	private final Map<String, ImageIcon> iconCache = new HashMap<>();

	// standard attributes
	private int playerRow = 8;
	private int playerCol = 16;
	private int tileSize;
	private int selectedToolIndex = 0; // TODO?

	// overlay for animation
	private JLayeredPane worldLayeredPane;

	// basic player directions for game start
	private enum PlayerDirections {
		LEFT, RIGHT, UP, DOWN
	}

	private PlayerDirections currentDirection = PlayerDirections.DOWN;

	private GameStateModel gameState;
	private PlayerModel player;
	private SoundController soundController;

	// UI attributes
	private JPanel tilePnl, taskPnl, topPnl;
	private JLabel timeLbl, playerLbl;
	private JProgressBar detectionBar;
	private Timer gameTimer;
	private int currentVolume = 100;
	private int elapsedSeconds = 0;
	private JLabel[][] worldLabels = new JLabel[ROWS][COLS];

	// new-game Constructor (standard) | TODO: add SoundController to parameter
	public GameView() {
		//this.soundController = soundController;
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		setUndecorated(true);

		// top Panel for player detection and time
		topPnl = new JPanel(new BorderLayout());
		topPnl.setPreferredSize(new Dimension(0, 130));
		topPnl.setBackground(Color.DARK_GRAY);

		// screen size / resolution
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		tileSize = calculateTileSize(screenSize.width, screenSize.height);
		
		// task panel & detection bar
		taskPnl = new JPanel();		
		detectionBar = new JProgressBar(0, 100);
		
		// timer
		timeLbl = new JLabel("Zeit: 00:00");
		timeLbl.setForeground(Color.WHITE);
		timeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLbl.setAlignmentX(RIGHT_ALIGNMENT);
		startGameTimer();

		topPnl.add(taskPnl, BorderLayout.WEST);
		topPnl.add(detectionBar, BorderLayout.CENTER);
		topPnl.add(timeLbl, BorderLayout.EAST);

		// game field calculation & creation
		Dimension worldDimension = new Dimension(COLS * tileSize, ROWS * tileSize);

		tilePnl = new JPanel(new GridLayout(ROWS, COLS));
		tilePnl.setOpaque(false);
		tilePnl.setBounds(0, 0, worldDimension.width, worldDimension.height);
		applyPreferredWorldSize();

		worldLayeredPane = new JLayeredPane();
		worldLayeredPane.setPreferredSize(worldDimension);
		
		// create world tiles
		for (int row = 0; row < ROWS; row++) {
		    for (int col = 0; col < COLS; col++) {

		        JLabel tile = new JLabel();

		        tile.setOpaque(true);
		        tile.setBackground(new Color(45, 90, 45));
		        tile.setLayout(new BorderLayout());

		        worldLabels[row][col] = tile;
		        tilePnl.add(tile);
		    }
		}

		// add tile panel to layered pane
		worldLayeredPane.add(tilePnl, JLayeredPane.DEFAULT_LAYER);

		// add world to game window
		getContentPane().add(worldLayeredPane, BorderLayout.CENTER);

		// standard player creation
		player = new PlayerModel(playerRow, playerCol, 1, "Test Player");
		playerLbl = new JLabel();
		playerLbl.setText("P1");
		playerLbl.setForeground(Color.WHITE);
		playerLbl.setFont(new Font("Arial", Font.BOLD, 24));
		
		playerLbl.setPreferredSize(new Dimension(tileSize, tileSize));
		playerLbl.setOpaque(false);
		playerLbl.setHorizontalAlignment(SwingConstants.CENTER);
		playerLbl.setVerticalAlignment(SwingConstants.CENTER);

		// load player gif by URL TODO:
//		ImageIcon playerIcon = getScaledIcon("TODO!!!!", (int) (tileSize * 0.7), true);
//		playerLbl.setIcon(playerIcon);

		// add player to world map and refresh
		worldLabels[playerRow][playerCol].setLayout(new BorderLayout());
		worldLabels[playerRow][playerCol].add(playerLbl, BorderLayout.CENTER);
		worldLabels[playerRow][playerCol].revalidate();
		worldLabels[playerRow][playerCol].repaint();

		// movement keys (WASD)
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0),
				"moveUp");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0),
				"moveLeft");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0),
				"moveDown");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0),
				"moveRight");

		// pause menu key ("ESC")
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"),
				"showPauseMenu");

		// action key ("E")
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("E"), "playerAction");

		// action maps
		// pause action
		getRootPane().getActionMap().put("showPauseMenu", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPauseMenu();
			}
		});

		// player movement
		getRootPane().getActionMap().put("moveLeft", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				turnOrMove(PlayerDirections.LEFT);
			}
		});

		getRootPane().getActionMap().put("moveRight", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				turnOrMove(PlayerDirections.RIGHT);
			}
		});

		getRootPane().getActionMap().put("moveUp", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				turnOrMove(PlayerDirections.UP);
			}
		});

		getRootPane().getActionMap().put("moveDown", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				turnOrMove(PlayerDirections.DOWN);
			}
		});

		// TODO: player action with E?

		getContentPane().setBackground(new Color(0, 50, 0));
		getContentPane().add(topPnl, BorderLayout.NORTH);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	protected void turnOrMove(PlayerDirections wantedDirection) {
		switch (wantedDirection) {
		case LEFT:
			movePlayer(0, -1);
			break;
		case RIGHT:
			movePlayer(0, 1);
			break;
		case UP:
			movePlayer(-1, 0);
			break;
		case DOWN:
			movePlayer(1, 0);
			break;
		}
	}

	private void movePlayer(int dRow, int dCol) {
		int newRow = playerRow + dRow;
		int newCol = playerCol + dCol;

		if (!isInside(newRow, newCol)) {
			return;
		}

		if (playerLbl != null && worldLabels[playerRow][playerCol] != null) {
			worldLabels[playerRow][playerCol].remove(playerLbl);
			worldLabels[playerRow][playerCol].revalidate();
			worldLabels[playerRow][playerCol].repaint();
		}

		playerRow = newRow;
		playerCol = newCol;
		player.setPositionX(playerCol);
		player.setPositionY(playerRow);

		worldLabels[playerRow][playerCol].setLayout(new BorderLayout());
		worldLabels[playerRow][playerCol].add(playerLbl, BorderLayout.CENTER);
		worldLabels[playerRow][playerCol].setComponentZOrder(playerLbl, 0);
		worldLabels[playerRow][playerCol].revalidate();
		worldLabels[playerRow][playerCol].repaint();
	}

	// helper method for game borders
	private boolean isInside(int row, int col) {
		return row >= 0 && row < ROWS && col >= 0 && col < COLS;
	}

	protected void showPauseMenu() {
		// TODO!

	}

	private ImageIcon getScaledIcon(String path, int size, boolean isAnimatedGif) {
		String key = path + "@" + size + (isAnimatedGif ? "#gif" : "#static");
		ImageIcon cached = iconCache.get(key);
		if (cached != null)
			return cached;

		URL url = getClass().getResource(path);
		if (url == null)
			return null;

		if (isAnimatedGif) {
			ImageIcon raw = new ImageIcon(url);
			Image scaled = raw.getImage().getScaledInstance(size, size, Image.SCALE_FAST);
			ImageIcon result = new ImageIcon(scaled);
			iconCache.put(key, result);
			return result;
		} else {
			try {
				BufferedImage src = ImageIO.read(url);
				BufferedImage dst = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = dst.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
				g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				g.drawImage(src, 0, 0, size, size, null);
				g.dispose();
				ImageIcon result = new ImageIcon(dst);
				iconCache.put(key, result);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				ImageIcon fallback = new ImageIcon(url);
				iconCache.put(key, fallback);
				return fallback;
			}
		}
	}

	private void applyPreferredWorldSize() {
		Dimension preferredDim = new Dimension(COLS * tileSize, ROWS * tileSize);
		tilePnl.setPreferredSize(preferredDim);
		tilePnl.setMinimumSize(preferredDim);
		tilePnl.setMaximumSize(preferredDim);
		tilePnl.revalidate();
		tilePnl.repaint();
	}

	private void startGameTimer() {
		gameTimer = new Timer(1000, e -> {
			elapsedSeconds++;
			int minutes = elapsedSeconds / 60;
			int seconds = elapsedSeconds % 60;
			timeLbl.setText(String.format("Zeit: %02d:%02d", minutes, seconds));
		});
		gameTimer.start();
	}

	private int calculateTileSize(int screenWidth, int screenHeight) {
		int topHeight = (topPnl != null) ? topPnl.getPreferredSize().height : 0;

		int availableWidth = screenWidth;
		int availableHeight = screenHeight - topHeight;

		int tileByWidth = availableWidth / COLS;
		int tileByHeight = availableHeight / ROWS;

		int size = Math.min(tileByWidth, tileByHeight);

		return Math.max(16, size);
	}

}

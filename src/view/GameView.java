package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import model.PlayerModel;

public class GameView extends JFrame {

    private static final int ROWS = 16;
    private static final int COLS = 32;
    private static final int WORLD_WIDTH = 1000;
    private static final int WORLD_HEIGHT = 600;

    private static final Color FLOOR = new Color(35, 67, 40);
    private static final Color FLOOR_GRID = new Color(55, 85, 58);
    private static final Color PANEL = new Color(18, 29, 26);
    private static final Color OUTLINE = new Color(93, 151, 117);
    private static final Color CYAN = new Color(100, 228, 208);
    private static final Color WHITE = new Color(236, 245, 238);

    private static final Rectangle LEFT_SERVER = new Rectangle(250, 130, 115, 270);
    private static final Rectangle RIGHT_SERVER = new Rectangle(655, 130, 115, 340);
    private static final Rectangle TERMINAL = new Rectangle(427, 145, 135, 78);

    private enum PlayerDirections {
        LEFT, RIGHT, UP, DOWN
    }

    private int playerRow = 8;
    private int playerCol = 16;
    private PlayerDirections currentDirection = PlayerDirections.DOWN;

    private final PlayerModel player;
    private final GameField gameField;
    private final JLabel timeLbl;
    private final JLabel taskLbl;
    private final JProgressBar detectionBar;
    private final JProgressBar leftServerHeat;
    private final JProgressBar rightServerHeat;
    private final Timer gameTimer;
    private int elapsedSeconds;

    public GameView() {
        setTitle("ThisIsFine - Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(PANEL);

        player = new PlayerModel(playerRow, playerCol, 1, "Test Player");

        JPanel topPnl = new JPanel(new BorderLayout(16, 0));
        topPnl.setBackground(PANEL);
        topPnl.setPreferredSize(new Dimension(0, 94));
        topPnl.setBorder(BorderFactory.createEmptyBorder(15, 20, 14, 20));

        JPanel taskPnl = new JPanel(new GridLayout(2, 1, 0, 2));
        taskPnl.setOpaque(false);
        taskPnl.setPreferredSize(new Dimension(245, 0));

        JLabel taskHeading = new JLabel("AUFGABE");
        taskHeading.setForeground(CYAN);
        taskHeading.setFont(new Font(Font.MONOSPACED, Font.BOLD, 19));

        taskLbl = new JLabel("Noch keine Aufgabe");
        taskLbl.setForeground(WHITE);
        taskLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        taskPnl.add(taskHeading);
        taskPnl.add(taskLbl);

        detectionBar = new JProgressBar(0, 100);
        detectionBar.setValue(0);
        detectionBar.setString("DETECTION");
        detectionBar.setStringPainted(true);
        detectionBar.setForeground(new Color(236, 156, 73));
        detectionBar.setBackground(new Color(42, 55, 45));
        detectionBar.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        detectionBar.setBorder(BorderFactory.createLineBorder(OUTLINE, 2));

        timeLbl = new JLabel("00:00:00", SwingConstants.RIGHT);
        timeLbl.setForeground(WHITE);
        timeLbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, 31));
        timeLbl.setPreferredSize(new Dimension(185, 0));

        topPnl.add(taskPnl, BorderLayout.WEST);
        topPnl.add(detectionBar, BorderLayout.CENTER);
        topPnl.add(timeLbl, BorderLayout.EAST);

        leftServerHeat = createHeatBar(25);
        rightServerHeat = createHeatBar(63);

        gameField = new GameField();
        gameField.add(leftServerHeat);
        gameField.add(rightServerHeat);

        add(topPnl, BorderLayout.NORTH);
        add(gameField, BorderLayout.CENTER);
        installKeys();

        gameTimer = new Timer(1000, e -> {
            elapsedSeconds++;
            int hours = elapsedSeconds / 3600;
            int minutes = elapsedSeconds / 60 % 60;
            int seconds = elapsedSeconds % 60;
            timeLbl.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        });
        gameTimer.start();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JProgressBar createHeatBar(int heat) {
        JProgressBar bar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
        bar.setValue(heat);
        bar.setStringPainted(false);
        bar.setBackground(new Color(13, 25, 23));
        bar.setForeground(heatColor(heat));
        bar.setBorder(BorderFactory.createLineBorder(OUTLINE, 2));
        return bar;
    }

    private static Color heatColor(int heat) {
        if (heat >= 75) {
            return new Color(231, 76, 59);
        }
        if (heat >= 45) {
            return new Color(245, 161, 64);
        }
        return new Color(93, 212, 113);
    }

    public void setServerHeat(int serverNumber, int heat) {
        if (serverNumber != 1 && serverNumber != 2) {
            throw new IllegalArgumentException("serverNumber muss 1 oder 2 sein");
        }
        int value = Math.max(0, Math.min(100, heat));
        SwingUtilities.invokeLater(() -> {
            JProgressBar bar = serverNumber == 1 ? leftServerHeat : rightServerHeat;
            bar.setValue(value);
            bar.setForeground(heatColor(value));
        });
    }

    public void setDetection(int detection) {
        int value = Math.max(0, Math.min(100, detection));
        SwingUtilities.invokeLater(() -> {
            detectionBar.setValue(value);
            detectionBar.setString("DETECTION  " + value + "%");
        });
    }

    public void setTaskText(String task) {
        SwingUtilities.invokeLater(() -> taskLbl.setText(task == null ? "" : task));
    }

    private void installKeys() {
        registerMove(KeyEvent.VK_W, "moveUp", PlayerDirections.UP);
        registerMove(KeyEvent.VK_A, "moveLeft", PlayerDirections.LEFT);
        registerMove(KeyEvent.VK_S, "moveDown", PlayerDirections.DOWN);
        registerMove(KeyEvent.VK_D, "moveRight", PlayerDirections.RIGHT);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "showPauseMenu");
        getRootPane().getActionMap().put("showPauseMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPauseMenu();
            }
        });
    }

    private void registerMove(int key, String actionName, PlayerDirections direction) {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(key, 0), actionName);
        getRootPane().getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnOrMove(direction);
            }
        });
    }

    private void turnOrMove(PlayerDirections wantedDirection) {
        currentDirection = wantedDirection;
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
        gameField.repaint();
    }

    private void movePlayer(int dRow, int dCol) {
        int newRow = playerRow + dRow;
        int newCol = playerCol + dCol;
        if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS) {
            return;
        }

        int x = Math.round((newCol + 0.5f) * WORLD_WIDTH / COLS);
        int y = Math.round((newRow + 0.5f) * WORLD_HEIGHT / ROWS);
        Rectangle footprint = new Rectangle(x - 10, y - 14, 20, 28);
        if (footprint.intersects(LEFT_SERVER)
                || footprint.intersects(RIGHT_SERVER)
                || footprint.intersects(TERMINAL)) {
            return;
        }

        playerRow = newRow;
        playerCol = newCol;
        player.setPositionX(playerCol);
        player.setPositionY(playerRow);
        gameField.repaint();
    }

    protected void showPauseMenu() {
        gameTimer.stop();
        Object[] options = {"Fortsetzen", "Beenden"};
        int result = JOptionPane.showOptionDialog(
                this, "Spiel pausiert", "Pause", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (result == 1) {
            dispose();
        } else {
            gameTimer.start();
        }
    }

    @Override
    public void dispose() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        super.dispose();
    }

    private class GameField extends JPanel {

        GameField() {
            setLayout(null);
            setBackground(FLOOR);
            setPreferredSize(new Dimension(WORLD_WIDTH, WORLD_HEIGHT));
        }

        private int px(double x) {
            return (int) Math.round(x * getWidth() / WORLD_WIDTH);
        }

        private int py(double y) {
            return (int) Math.round(y * getHeight() / WORLD_HEIGHT);
        }

        @Override
        public void doLayout() {
            super.doLayout();
            placeHeatBar(leftServerHeat, LEFT_SERVER);
            placeHeatBar(rightServerHeat, RIGHT_SERVER);
        }

        private void placeHeatBar(JProgressBar bar, Rectangle server) {
            bar.setBounds(px(server.x + server.width / 2.0 - 13), py(server.y + 16),
                    Math.max(12, px(26)), Math.max(24, py(server.height - 32)));
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.scale(getWidth() / (double) WORLD_WIDTH, getHeight() / (double) WORLD_HEIGHT);

            drawFloor(g);
            drawCameraCone(g);
            drawServer(g, LEFT_SERVER);
            drawServer(g, RIGHT_SERVER);
            drawTerminal(g);
            drawExtinguisher(g);
            drawExit(g);
            drawCamera(g);
            drawPlayer(g);
            g.dispose();
        }

        private void drawFloor(Graphics2D g) {
            g.setColor(FLOOR);
            g.fillRect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
            g.setColor(FLOOR_GRID);
            g.setStroke(new BasicStroke(0.6f));
            for (int x = 0; x <= WORLD_WIDTH; x += 31) {
                g.drawLine(x, 0, x, WORLD_HEIGHT);
            }
            for (int y = 0; y <= WORLD_HEIGHT; y += 37) {
                g.drawLine(0, y, WORLD_WIDTH, y);
            }
            g.setColor(OUTLINE);
            g.setStroke(new BasicStroke(3f));
            g.drawRect(3, 3, WORLD_WIDTH - 7, WORLD_HEIGHT - 7);
        }

        private void drawCameraCone(Graphics2D g) {
            Polygon cone = new Polygon(
                    new int[]{979, 865, 465},
                    new int[]{572, 90, 438}, 3);
            g.setColor(new Color(232, 169, 74, 28));
            g.fillPolygon(cone);
            g.setColor(new Color(232, 169, 74, 155));
            g.setStroke(new BasicStroke(2f));
            g.drawLine(979, 572, 865, 90);
            g.drawLine(979, 572, 465, 438);
        }

        private void drawServer(Graphics2D g, Rectangle rect) {
            g.setColor(new Color(7, 28, 28));
            g.fillRoundRect(rect.x, rect.y, rect.width, rect.height, 18, 18);
            g.setColor(OUTLINE);
            g.setStroke(new BasicStroke(3f));
            g.drawRoundRect(rect.x, rect.y, rect.width, rect.height, 18, 18);

            g.setColor(new Color(20, 42, 39));
            g.fillRoundRect(rect.x + 12, rect.y + 9, rect.width - 24, rect.height - 18, 12, 12);
            g.setColor(new Color(70, 117, 106));
            for (int y = rect.y + 29; y < rect.y + rect.height - 20; y += 19) {
                g.fillRoundRect(rect.x + 16, y, 19, 5, 3, 3);
                g.fillRoundRect(rect.x + rect.width - 35, y, 19, 5, 3, 3);
            }
        }

        private void drawTerminal(Graphics2D g) {
            g.setColor(new Color(8, 21, 19));
            g.fillRoundRect(TERMINAL.x, TERMINAL.y, TERMINAL.width, TERMINAL.height, 14, 14);
            g.setColor(CYAN);
            g.setStroke(new BasicStroke(3f));
            g.drawRoundRect(TERMINAL.x, TERMINAL.y, TERMINAL.width, TERMINAL.height, 14, 14);
            g.setColor(new Color(62, 195, 103));
            g.setStroke(new BasicStroke(2f));
            g.drawLine(TERMINAL.x + 18, TERMINAL.y + 25, TERMINAL.x + 91, TERMINAL.y + 25);
            g.drawLine(TERMINAL.x + 18, TERMINAL.y + 40, TERMINAL.x + 107, TERMINAL.y + 40);
            g.drawLine(TERMINAL.x + 18, TERMINAL.y + 55, TERMINAL.x + 72, TERMINAL.y + 55);
        }

        private void drawExtinguisher(Graphics2D g) {
            g.setColor(new Color(183, 40, 42));
            g.fillRoundRect(19, 530, 26, 55, 11, 11);
            g.setColor(new Color(245, 101, 96));
            g.setStroke(new BasicStroke(2.5f));
            g.drawRoundRect(19, 530, 26, 55, 11, 11);
            g.drawLine(32, 530, 32, 522);
            g.drawLine(24, 522, 42, 522);
            g.setColor(WHITE);
            g.fillRoundRect(23, 550, 18, 13, 3, 3);
            g.setColor(new Color(20, 26, 23));
            g.drawArc(35, 525, 22, 34, 0, 140);
        }

        private void drawExit(Graphics2D g) {
            g.setColor(new Color(4, 17, 16));
            g.fillRoundRect(435, 568, 130, 29, 10, 10);
            g.setColor(new Color(92, 227, 141));
            g.setStroke(new BasicStroke(3f));
            g.drawRoundRect(435, 568, 130, 29, 10, 10);
            g.drawLine(492, 580, 501, 587);
            g.drawLine(501, 587, 510, 580);
        }

        private void drawCamera(Graphics2D g) {
            g.setColor(new Color(12, 21, 24));
            g.fillOval(957, 551, 37, 37);
            g.setColor(new Color(234, 181, 78));
            g.setStroke(new BasicStroke(3f));
            g.drawOval(957, 551, 37, 37);
            g.setColor(new Color(224, 88, 67));
            g.fillOval(970, 563, 11, 11);
        }

        private void drawPlayer(Graphics2D g) {
            int x = Math.round((playerCol + 0.5f) * WORLD_WIDTH / COLS);
            int y = Math.round((playerRow + 0.5f) * WORLD_HEIGHT / ROWS);

            g.setColor(new Color(0, 0, 0, 110));
            g.fillOval(x - 19, y + 20, 38, 12);
            g.setColor(WHITE);
            g.setStroke(new BasicStroke(3.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawOval(x - 9, y - 29, 18, 18);
            g.drawLine(x, y - 10, x, y + 14);
            g.drawLine(x, y - 4, x - 16, y + 7);
            g.drawLine(x, y - 4, x + 16, y + 7);
            g.drawLine(x, y + 14, x - 15, y + 30);
            g.drawLine(x, y + 14, x + 15, y + 30);

            g.setColor(CYAN);
            g.setStroke(new BasicStroke(2.7f));
            if (currentDirection == PlayerDirections.UP) {
                g.drawLine(x, y - 35, x, y - 45);
            } else if (currentDirection == PlayerDirections.DOWN) {
                g.drawLine(x, y + 32, x, y + 42);
            } else if (currentDirection == PlayerDirections.LEFT) {
                g.drawLine(x - 19, y - 3, x - 29, y - 3);
            } else {
                g.drawLine(x + 19, y - 3, x + 29, y - 3);
            }
        }
    }
}

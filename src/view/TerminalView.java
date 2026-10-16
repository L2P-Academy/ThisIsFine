package view;

import model.CryptoModel;
import model.CryptoModel.TerminalLine;
import controller.SoundController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class TerminalView extends JFrame {

    private static final Color MATRIX_GREEN = new Color(45, 240, 35);
    private static final Color TERMINAL_BLACK = new Color(3, 17, 5);
    private static final Color CASE_GREEN = new Color(0, 95, 12);
    private static final Color LIGHT_GREEN = new Color(166, 197, 142);
    private static final DefaultHighlighter.DefaultHighlightPainter HOVER_PAINTER =
        new DefaultHighlighter.DefaultHighlightPainter(new Color(30, 95, 40));

    private final SoundController soundController;
    private final CryptoModel model;
    private final JLabel attemptsLabel;
    private final JLabel statusLabel;
    private final JTextArea leftText;
    private final JTextArea rightText;
    private JTextArea highlightedArea;
    private int highlightedStart = -1;
    private int highlightedEnd = -1;

    public TerminalView(SoundController soundController) {
        this(new CryptoModel(),soundController);
    }

    public TerminalView(CryptoModel model,SoundController soundController) {
    	this.soundController=soundController;
        this.model = model;

        setTitle("ThisIsFine - Terminal");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.setBackground(LIGHT_GREEN);
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel northLabel = titleLabel("----- ADMIN-TERMINAL -----", 38);
        JLabel companyLabel = titleLabel("FineIndustries Inc.", 24);
        JLabel serverLabel = titleLabel("--- Server 1 & 2 ---", 18);
        northPanel.add(northLabel);
        northPanel.add(companyLabel);
        northPanel.add(serverLabel);

        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(TERMINAL_BLACK);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel screen = new JPanel(new BorderLayout(0, 14));
        screen.setBackground(TERMINAL_BLACK);
        screen.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel screenHeader = new JPanel();
        screenHeader.setLayout(new BoxLayout(screenHeader, BoxLayout.Y_AXIS));
        screenHeader.setBackground(TERMINAL_BLACK);
        screenHeader.add(terminalLabel("FINE INDUSTRIES (TM) TERMLINK PROTOCOL", 21));
        screenHeader.add(terminalLabel("ENTER PASSWORD", 21));
        attemptsLabel = terminalLabel("", 20);
        screenHeader.add(attemptsLabel);

        JPanel textColumns = new JPanel(new GridLayout(1, 2, 22, 0));
        textColumns.setBackground(TERMINAL_BLACK);
        leftText = makeTextArea(model.getLeftLines());
        rightText = makeTextArea(model.getRightLines());
        textColumns.add(leftText);
        textColumns.add(rightText);

        installWordClicks(leftText, model.getLeftLines());
        installWordClicks(rightText, model.getRightLines());

        statusLabel = terminalLabel("BREACH DETECTED - VERIFY YOURSELF OR ELSE..!", 18);
        screen.add(screenHeader, BorderLayout.NORTH);
        screen.add(textColumns, BorderLayout.CENTER);
        screen.add(statusLabel, BorderLayout.SOUTH);
        middlePanel.add(screen, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(TERMINAL_BLACK);
        southPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 12));
        JButton powerButton = new JButton("PWR");
        beautifyButton(powerButton);
        powerButton.addActionListener(e -> dispose());

        southPanel.add(powerButton, BorderLayout.EAST);

        add(northPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        updateAttempts();
        setLocationRelativeTo(null);
        setVisible(true);
        
        //Music
        
        
    }

    private static JLabel titleLabel(String text, int size) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, size));
        label.setForeground(new Color(0, 75, 0));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private static JLabel terminalLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, size));
        label.setForeground(MATRIX_GREEN);
        return label;
    }

    private static JTextArea makeTextArea(List<TerminalLine> rows) {
        StringBuilder output = new StringBuilder();
        for (TerminalLine row : rows) {
            if (output.length() > 0) {
                output.append('\n');
            }
            output.append(row.getText());
        }

        JTextArea area = new JTextArea(output.toString());
        area.setEditable(false);
        area.setFocusable(false);
        area.setLineWrap(false);
        area.setFont(new Font(Font.MONOSPACED, Font.BOLD, 23));
        area.setBackground(TERMINAL_BLACK);
        area.setForeground(MATRIX_GREEN);
        area.setCaretColor(MATRIX_GREEN);
        area.setBorder(BorderFactory.createEmptyBorder());
        return area;
    }

    private void installWordClicks(JTextArea area, List<TerminalLine> lines) {
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                highlightAt(area, lines, e.getPoint());
                area.setCursor(!model.isGameOver() && wordAt(area, lines, e.getPoint()) != null
                    ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                    : Cursor.getDefaultCursor());
                
                //play typing sound if hovering over Word
                if(!model.isGameOver() && wordAt(area, lines, e.getPoint()) != null && !soundController.typingClip.isActive()) {
                	soundController.playSfx(soundController.typingClip);
                }
                if(soundController.typingClip.isRunning() && wordAt(area,lines, e.getPoint())==null) {
                	soundController.stopSfx(soundController.typingClip);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                clearHighlight();
                area.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!SwingUtilities.isLeftMouseButton(e) || model.isGameOver()) {
                    return;
                }

                String word = wordAt(area, lines, e.getPoint());
                if (word == null) {
                    return;
                }

                String feedback = model.submitGuess(word);
                statusLabel.setText(feedback);
                statusLabel.setForeground(model.isSolved()
                    ? MATRIX_GREEN : new Color(255, 185, 55));
                updateAttempts();
                soundController.typingClip.stop();
                soundController.playButtonClip(soundController.buttonClip);
                if(model.isGameOver()) {
                	if(model.isSolved()) {soundController.playRandomSuccessClip();
                	}else {soundController.playSfx(soundController.failClip);}
                }
            }
        };

        area.addMouseListener(listener);
        area.addMouseMotionListener(listener);
    }

    private static int characterOffsetAt(JTextArea area, List<TerminalLine> lines, Point point) {
        int offset = area.viewToModel2D(point);
        if (offset < 0) {
            return -1;
        }

        try {
            Rectangle2D rect = area.modelToView2D(offset);
            if (rect == null) {
                return -1;
            }
            if (point.x < rect.getX() && offset > 0) {
                offset--;
                rect = area.modelToView2D(offset);
            }

            int rowIndex = area.getLineOfOffset(offset);
            if (rowIndex >= lines.size()) {
                return -1;
            }

            int characterIndex = offset - area.getLineStartOffset(rowIndex);
            String text = lines.get(rowIndex).getText();
            int symbolStart = text.indexOf("  ") + 2;

            if (symbolStart < 2 || characterIndex < symbolStart || characterIndex >= text.length()) {
                return -1;
            }

            int charWidth = area.getFontMetrics(area.getFont()).charWidth('W');
            if (!new Rectangle2D.Double(rect.getX(), rect.getY(), charWidth, rect.getHeight()).contains(point)) {
                return -1;
            }

            return offset;
        } catch (BadLocationException ex) {
            return -1;
        }
    }

    private static String wordAt(JTextArea area, List<TerminalLine> lines, Point point) {
        int offset = characterOffsetAt(area, lines, point);
        if (offset < 0) {
            return null;
        }

        try {
            int rowIndex = area.getLineOfOffset(offset);
            int characterIndex = offset - area.getLineStartOffset(rowIndex);
            TerminalLine line = lines.get(rowIndex);
            if (line.getWord() != null
                    && characterIndex >= line.getWordStart()
                    && characterIndex < line.getWordStart() + CryptoModel.WORD_LENGTH) {
                return line.getWord();
            }
        } catch (BadLocationException ex) {
            return null;
        }
        return null;
    }

    private void highlightAt(JTextArea area, List<TerminalLine> lines, Point point) {
        int offset = characterOffsetAt(area, lines, point);
        if (offset < 0) {
            clearHighlight();
            return;
        }

        try {
            int rowIndex = area.getLineOfOffset(offset);
            int characterIndex = offset - area.getLineStartOffset(rowIndex);
            TerminalLine line = lines.get(rowIndex);
            int start = offset;
            int end = offset + 1;

            if (line.getWord() != null
                    && characterIndex >= line.getWordStart()
                    && characterIndex < line.getWordStart() + CryptoModel.WORD_LENGTH) {
                start = area.getLineStartOffset(rowIndex) + line.getWordStart();
                end = start + CryptoModel.WORD_LENGTH;
            }

            if (highlightedArea == area && highlightedStart == start && highlightedEnd == end) {
                return;
            }

            clearHighlight();
            area.getHighlighter().addHighlight(start, end, HOVER_PAINTER);
            highlightedArea = area;
            highlightedStart = start;
            highlightedEnd = end;
        } catch (BadLocationException ex) {
            clearHighlight();
        }
    }

    private void clearHighlight() {
        leftText.getHighlighter().removeAllHighlights();
        rightText.getHighlighter().removeAllHighlights();
        highlightedArea = null;
        highlightedStart = -1;
        highlightedEnd = -1;
    }

    private void updateAttempts() {
        int left = model.getAttemptsLeft();

        StringBuilder blocks = new StringBuilder();
        for (int i = 0; i < CryptoModel.MAX_ATTEMPTS; i++) {
            blocks.append(i < left ? "■ " : "□ ");
        }
        attemptsLabel.setText(left + " ATTEMPT(S) LEFT: " + blocks);
    }

    public static void beautifyButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(255, 148, 17));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Times New Roman", Font.PLAIN, 36));

        Border border = BorderFactory.createLineBorder(new Color(25, 100, 156), 2);
        Border roundedBorder = BorderFactory.createCompoundBorder(border,
            BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setBorder(BorderFactory.createCompoundBorder(roundedBorder,
            BorderFactory.createEmptyBorder(5, 15, 5, 15)));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(25, 100, 156));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 148, 17));
            }
        });
    }
}

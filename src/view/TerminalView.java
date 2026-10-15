package view;

// author: M4ntis, ted88888888

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class TerminalView extends JFrame {
    private JFrame TerminalView;
    private JLabel northLabel, southLabel;
    private JPanel middlePanel, southPanel;
    private JButton powerButton;
    
    private int maxAttempts;
    private int currentAttempts;

    //constructor
    public TerminalView() {
        setTitle("ThisIsFine");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // container
        southPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel();
        middlePanel.setBackground(Color.green);
        
        //components       
        northLabel = new JLabel("--ADMIN-TERMINAL--", SwingConstants.CENTER);
        northLabel.setFont(new Font("Arial", Font.BOLD, 90));
        southLabel = new JLabel("Verbleibende Versuche: ");
        southLabel.setFont(new Font("Arial", Font.BOLD, 30));
        powerButton = new JButton("Power");
        
        //actions, layout
        southPanel.add(southLabel, BorderLayout.WEST);
        southPanel.add(powerButton, BorderLayout.EAST);
        beautifyButton(powerButton);

        add(northLabel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(middlePanel, BorderLayout.CENTER);
    }

	public static void beautifyButton(JButton button) {
		button.setFocusPainted(false);
		button.setBackground(new Color(255, 148, 17));
		button.setForeground(Color.BLACK);
		button.setFont(new Font("Times New Roman", 0, 48));

		// Rounded Corners
		Border border = BorderFactory.createLineBorder(new Color(25, 100, 156), 2);
		Border roundedBorder = BorderFactory.createCompoundBorder(border,
				BorderFactory.createEmptyBorder(10, 20, 10, 20));
		button.setBorder(
				BorderFactory.createCompoundBorder(roundedBorder, BorderFactory.createEmptyBorder(5, 15, 5, 15)));

		// color change when MouseOver is happening
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(25, 100, 156));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(255, 148, 17));
			}
		});
	}
}
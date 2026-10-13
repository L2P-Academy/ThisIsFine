package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MenuView extends JFrame {
	// attributes
	
	private JPanel buttonPanel, backgroundPanel;
	private JButton newGameBtn, loadGameBtn, settingsBtn, exitBtn;
	private String bgPath = "/img/ThisIsFine_bg.png";

	// constructor
	public MenuView() {
		// frame attributes
		setTitle("This is Fine! - Hauptmenü");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setAlwaysOnTop(true);

		// container
		buttonPanel = new JPanel(new FlowLayout());
		ImageIcon bgIcon = new ImageIcon(getClass().getResource(bgPath));
		backgroundPanel = new BackgroundPanel(bgIcon.getImage());
		backgroundPanel.setLayout(new BorderLayout());

		// components
		newGameBtn = new JButton("Neues Spiel");
		beautifyButton(newGameBtn);
		loadGameBtn = new JButton("Spiel laden");
		beautifyButton(loadGameBtn);
		settingsBtn = new JButton("Optionen");
		beautifyButton(settingsBtn);
		exitBtn = new JButton("Beenden");
		beautifyButton(exitBtn);

		// layout
		buttonPanel.add(newGameBtn);
		buttonPanel.add(loadGameBtn);
		buttonPanel.add(settingsBtn);
		buttonPanel.add(exitBtn);
		buttonPanel.setOpaque(false);

		backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

		// actions
		exitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		getContentPane().add(backgroundPanel);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// methods
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

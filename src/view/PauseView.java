package view;

//author: djanix0340 

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JLabel;


public class PauseView extends JFrame{
	
	//Attribute
	private JButton resumeBtn, restartBtn, saveBtn, exitBtn;
	private JPanel buttonPanel, backgroundPanel;
	private String bgPath = "/img/PauseView_bg.png";
	
	
	//Constructor
	public PauseView() {
		//frame attributes
		setTitle("This is Fine! - Pause");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setAlwaysOnTop(true);
		
		//container
		buttonPanel = new JPanel(new GridLayout(4, 1, 0, 25));
		JPanel centerWrapper = new JPanel(new java.awt.GridBagLayout());
		centerWrapper.setOpaque(false);
		centerWrapper.add(buttonPanel);
		buttonPanel.setOpaque(false);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0)); 
		ImageIcon bgIcon = new ImageIcon(getClass().getResource(bgPath));
		backgroundPanel = new BackgroundPanel(bgIcon.getImage());
		backgroundPanel.setLayout(new BorderLayout());
		
		//components
		resumeBtn = new JButton("Fortsetzen");
		beautifyButton(resumeBtn);
		restartBtn = new JButton("Restart");
		beautifyButton(restartBtn);
		saveBtn = new JButton("Speichern");
		beautifyButton(saveBtn);
		exitBtn = new JButton("Beenden");
		beautifyButton(exitBtn);
		
		
		
		//layout
		buttonPanel.add(resumeBtn);
		buttonPanel.add(restartBtn);
		buttonPanel.add(saveBtn);
		buttonPanel.add(exitBtn);
		buttonPanel.setOpaque(false);
		
		
	
		backgroundPanel.add(centerWrapper, BorderLayout.CENTER);
		
		//actions
		
		/*resumeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		*/
		
		/*restartBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		*/
		
		/*saveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});;
		*/
		
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
	
	
	//methods
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


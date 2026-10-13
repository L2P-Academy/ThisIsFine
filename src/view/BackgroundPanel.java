
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * Specialized JPanel for Backgrounds in the Game, uses a Image when constructed
 *
 */
public class BackgroundPanel extends JPanel {
	private Image backgroundImage;

	public BackgroundPanel(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
		setLayout(new BorderLayout());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(backgroundImage.getWidth(this), backgroundImage.getHeight(this));
	}
}

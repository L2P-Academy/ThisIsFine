package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import model.ItemModel;

// author: Whatchamaycall

public class ItemView extends JLabel {

	private ItemModel item;
	private int tileSize;

	// constructor
	public ItemView(ItemModel item, ImageIcon icon, int tileSize) {
		this.item = item;
		this.tileSize = tileSize;

		setIcon(icon);
		refreshView();
	}

	// methods
	public ItemModel getItem() {
		return item;
	}
	
	public void setItemIcon(ImageIcon icon) {
		setIcon(icon);
	}

	public void updateVisibility() {
		setVisible(item.isVisible());

	}

	public void updatePosition() {
		setLocation(item.getPositionX() * tileSize, item.getPositionY() * tileSize);
	}

	public void updateSize() {
		setPreferredSize(new java.awt.Dimension(item.getWidth(), item.getHeight()));

		setSize(item.getWidth(), item.getHeight());
	}

	public void refreshView() {
		updateVisibility();
		updatePosition();
		updateSize();
	}

}

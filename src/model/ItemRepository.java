package model;

// author Whatchamaycall

public class ItemRepository {

	// attributes
	private ItemModel fireExtinguisher, keyboard, keyCard;
	private ItemModel coffee, harrysCape, usbDrive;

	// constructor

	public ItemRepository(ItemModel fireExtinguisher, ItemModel keyboard, ItemModel keyCard, ItemModel coffee,
			ItemModel harrysCape, ItemModel usbDrive) {
		super();
		this.fireExtinguisher = fireExtinguisher;
		this.keyboard = keyboard;
		this.keyCard = keyCard;
		this.coffee = coffee;
		this.harrysCape = harrysCape;
		this.usbDrive = usbDrive;
	}

	// methods
	// getter & setter
	public ItemModel getFireExtinguisher() {
		return fireExtinguisher;
	}

	public void setFireExtinguisher(ItemModel fireExtinguisher) {
		this.fireExtinguisher = fireExtinguisher;
	}

	public ItemModel getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(ItemModel keyboard) {
		this.keyboard = keyboard;
	}

	public ItemModel getKeyCard() {
		return keyCard;
	}

	public void setKeyCard(ItemModel keyCard) {
		this.keyCard = keyCard;
	}

	public ItemModel getCoffee() {
		return coffee;
	}

	public void setCoffee(ItemModel coffee) {
		this.coffee = coffee;
	}

	public ItemModel getHarrysCape() {
		return harrysCape;
	}

	public void setHarrysCape(ItemModel harrysCape) {
		this.harrysCape = harrysCape;
	}

	public ItemModel getUsbDrive() {
		return usbDrive;
	}

	public void setUsbDrive(ItemModel usbDrive) {
		this.usbDrive = usbDrive;
	}

}

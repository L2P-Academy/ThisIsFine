package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

// author Whatchamaycall

public class ItemRepository {

	// attributes
	private ArrayList<ItemModel> items;

	// constructor
	public ItemRepository(ArrayList<ItemModel> items) {
		super();
		this.items = items;
	}

	// methods

	// add item to list
	public void addItem(ItemModel item) {
		items.add(item);

	}

	// return all items
	public ArrayList<ItemModel> getItems() {
		return items;

	}

	// remove an item from the list
	public void removeItem(ItemModel item) {
		items.remove(item);

	}
	
	// find an item by its name
	public ItemModel findItemByName(String name) {
		for (ItemModel item : items) {
			if (item.getName().equals(name)) {
				return item;
			}
		}
		
		return null;
	}

}

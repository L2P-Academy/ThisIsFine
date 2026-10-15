package model;

import java.util.List;
import java.util.ArrayList;

// author: Whatchamaycall

public class ItemRepository {

    // attributes
    private static final List<ItemModel> ITEMS = new ArrayList<>();

    // create items
    static {
        ITEMS.add(new ItemModel(1, 0, 0, "Fire Extinguisher", false, true));
        ITEMS.add(new ItemModel(2, 0, 0, "Keyboard", false, true));
        ITEMS.add(new ItemModel(3, 0, 0, "Keycard", false, true));
        ITEMS.add(new ItemModel(4, 0, 0, "Coffee", false, true));
        ITEMS.add(new ItemModel(5, 0, 0, "Harry's Cape", false, true));
        ITEMS.add(new ItemModel(6, 0, 0, "USB Drive", false, true));
    }

    // return all items
    public static List<ItemModel> getAllItems() {
        return ITEMS;
    }

    // return item via ID 
    public static ItemModel getItemById(int id) {
        for (ItemModel item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }
}

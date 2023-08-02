/**
 * The Slot class represents a slot in a vending machine that holds a specific item
 * and its quantity.*/
public class Slot {
    private Item item;
    private int quantity;

    /**
     * Constructs a new Slot object with the given Item and quantity.
     * @param item The item that will be placed in the slot.
     * @param q The quantity of that Item that will be placed in the slot.
     **/
    public Slot(Item item, int q) {
        this.item = item;
        quantity = q;
    }
    /**
     * Removes an Item from the slot
     * @param amt Amount of that item to be removed
     * */
    public void removeItem(int amt) {
        quantity -= amt;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int q) {
        quantity = q;
    }
}


/**
 * The Item class represents the ingredients or food items to be either bought or a part of a Customizable.
 * This Item class also extends the interface Purchaseable.
 * */

public class Item implements Purchaseable {
    private final String name;
    private double price;
    private double calories;
    private int quantity;

    /**
     * Constructs an item object given the name, price, caloric yield, and quantity
     * @param n Name of item.
     * @param p Price of item.
     * @param c Caloric yield of item.
     * @param q Quantity of the item to be instantiated.*/

    public Item(String n, double p, double c, int q) {
        name = n;
        price = p;
        calories = c;
        quantity = q;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * This equals method overrides the object class methods.
     * @param obj Accepts the object parameter.
     * @return name.equals(otherItem.name) Returns a boolean value comparing the name of the object to the string name that it is compared to.*/
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Item otherItem = (Item) obj;
        return name.equals(otherItem.name);
    }

    /**
     * Inherited methods from the implemented class, Purchaseable.
     * */
    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
import java.util.ArrayList;

/**
 * The Customizables class represents the customizable objects which are predefined that can be customized and sold in the Vending Machine.
 * This class also implements the interface Purchaseable.
 * */
public class Customizables implements Purchaseable {
    private final String name;
    private ArrayList<Item> foodItems;
    /**
     * Constructs a customizables object given the name and its food item ingredients.
     * @param name Given name that will define the customizable.
     * */
    public Customizables(String name) {
        this.name = name;
        this.foodItems = new ArrayList<>();
    }
    /**
     * Calculates the total price by summing up the individual prices of all ingredients.
     * @return totalPrice Returns the total price of the customizable.
     * */
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Item item : foodItems) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
    /**
     * Calculates the total calories by summing up the individual caloric yields of all ingredients.
     * @return totalCalories Returns the total calories of the customizable.
     * */
    public double getTotalCalories() {
        double totalCalories = 0;
        for (Item item : foodItems) {
            totalCalories += item.getCalories();
        }
        return totalCalories;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getItems() {
        return foodItems;
    }
    /**
     * This static method pre-defines Customizable objects that are to be sold in the Vending Machine.
     * @return customizablesList Returns an ArrayList of customizables.
     * */
    public static ArrayList<Customizables> getAvailableCustomizables() {
        ArrayList<Customizables> customizablesList = new ArrayList<>();

        customizablesList.add(createBBQBaconBurger());

        customizablesList.add(createCheeseBurger());

        customizablesList.add(createVegetarianBurger());

        customizablesList.add(createChickenBurger());

        customizablesList.add(createTeriyakiBurger());

        customizablesList.add(createBeefTaco());

        customizablesList.add(createShrimpTaco());

        customizablesList.add(createBurrito());

        return customizablesList;
    }

    /**
     * This method gathers all the items used to comprise the Customizables.
     * @return availableItems Returns an ArrayList of all available items used to make the customizable objects.*/
    public static ArrayList<Item> getAvailableItems() {
        ArrayList<Item> availableItems = new ArrayList<>();

        availableItems.add(new Item("Bun", 1.0, 120, 64));
        availableItems.add(new Item("Patty", 2.5, 200, 32));
        availableItems.add(new Item("Cheese", 0.5, 50, 32));
        availableItems.add(new Item("Lettuce", 0.3, 10, 64));
        availableItems.add(new Item("Tomato", 0.4, 15, 64));
        availableItems.add(new Item("Onion", 0.5, 30, 64));
        availableItems.add(new Item("BBQ Sauce", 0.8, 50, 32));
        availableItems.add(new Item("Bacon", 1.0, 150, 128));
        availableItems.add(new Item("Ketchup", 0.2, 30, 32));
        availableItems.add(new Item("Mayonnaise", 0.2, 40, 32));
        availableItems.add(new Item("Mustard", 0.2, 20, 32));
        availableItems.add(new Item("Vegetarian Patty", 2.0, 180, 32));
        availableItems.add(new Item("Pickles", 0.3, 5, 128));
        availableItems.add(new Item("Spinach", 0.2, 8, 128));
        availableItems.add(new Item("Chicken Breast", 2.0, 165, 32));
        availableItems.add(new Item("Teriyaki Sauce", 1.5, 40, 32));
        availableItems.add(new Item("Seaweed", 0.7, 15, 128));
        availableItems.add(new Item("Bonito Flakes", 1.2, 25, 32));
        availableItems.add(new Item("Shell", 0.6, 50, 64));
        availableItems.add(new Item("Ground Beef", 2.0, 180, 32));
        availableItems.add(new Item("Hot Sauce", 0.4, 20, 128));
        availableItems.add(new Item("Shrimp", 1.5, 100, 32));
        availableItems.add(new Item("Lime", 0.3, 10, 128));
        availableItems.add(new Item("Pita", 0.7, 100, 64));
        availableItems.add(new Item("Avocado", 1.2, 120, 64));

        return availableItems;
    }

    /**
     * These methods define the objects that will be customized and sold in the Vending Machine
     */
    public static Customizables createBBQBaconBurger() {
        Customizables bbqBaconBurger = new Customizables("BBQ Bacon Burger");
        bbqBaconBurger.addItem("Bun", 2);
        bbqBaconBurger.addItem("Patty", 1);
        bbqBaconBurger.addItem("Onion", 1);
        bbqBaconBurger.addItem("BBQ Sauce", 1);
        bbqBaconBurger.addItem("Bacon", 3);
        return bbqBaconBurger;
    }

    public static Customizables createCheeseBurger() {
        Customizables cheeseBurger = new Customizables("Cheese Burger");
        cheeseBurger.addItem("Bun", 2);
        cheeseBurger.addItem("Patty", 1);
        cheeseBurger.addItem("Cheese", 1);
        cheeseBurger.addItem("Lettuce", 1);
        cheeseBurger.addItem("Ketchup", 1);
        cheeseBurger.addItem("Mayonnaise", 1);
        cheeseBurger.addItem("Mustard", 1);
        return cheeseBurger;
    }

    public static Customizables createVegetarianBurger() {
        Customizables vegetarianBurger = new Customizables("Vegetarian Burger");
        vegetarianBurger.addItem("Bun", 2);
        vegetarianBurger.addItem("Vegetarian Patty", 1);
        vegetarianBurger.addItem("Pickles", 2);
        vegetarianBurger.addItem("Spinach", 1);
        return vegetarianBurger;
    }

    public static Customizables createChickenBurger() {
        Customizables chickenBurger = new Customizables("Chicken Burger");
        chickenBurger.addItem("Bun", 2);
        chickenBurger.addItem("Chicken Breast", 1);
        chickenBurger.addItem("Lettuce", 1);
        chickenBurger.addItem("Mayonnaise", 1);
        chickenBurger.addItem("Pickles", 2);
        return chickenBurger;
    }

    public static Customizables createTeriyakiBurger() {
        Customizables teriyakiBurger = new Customizables("Teriyaki Burger");
        teriyakiBurger.addItem("Bun", 2);
        teriyakiBurger.addItem("Patty", 1);
        teriyakiBurger.addItem("Teriyaki Sauce", 1);
        teriyakiBurger.addItem("Seaweed", 1);
        teriyakiBurger.addItem("Bonito Flakes", 2);
        return teriyakiBurger;
    }
    public static Customizables createBeefTaco() {
        Customizables beefTaco = new Customizables("Beef Taco");
        beefTaco.addItem("Shell", 1);
        beefTaco.addItem("Ground Beef", 1);
        beefTaco.addItem("Cheese", 1);
        beefTaco.addItem("Onion", 1);
        beefTaco.addItem("Hot Sauce", 1);
        return beefTaco;
    }

    public static Customizables createShrimpTaco() {
        Customizables shrimpTaco = new Customizables("Shrimp Taco");
        shrimpTaco.addItem("Shell", 1);
        shrimpTaco.addItem("Shrimp", 3);
        shrimpTaco.addItem("Lime", 1);
        shrimpTaco.addItem("Onion", 1);
        shrimpTaco.addItem("Tomato", 1);
        return shrimpTaco;
    }

    public static Customizables createBurrito() {
        Customizables burrito = new Customizables("Burrito");
        burrito.addItem("Pita", 1);
        burrito.addItem("Ground Beef", 2);
        burrito.addItem("Avocado", 2);
        burrito.addItem("Cheese", 1);
        burrito.addItem("Onion", 1);
        burrito.addItem("Tomato", 1);
        return burrito;
    }

    public void addItem(String itemName, int quantity) {
        for (Item availableItem : getAvailableItems()) {
            if (availableItem.getName().equalsIgnoreCase(itemName)) {
                Item item = new Item(availableItem.getName(), availableItem.getPrice(), availableItem.getCalories(), quantity);
                foodItems.add(item);
                break;
            }
        }
    }

    @Override
    public double getPrice() {
        return getTotalPrice();
    }

    @Override
    public int getQuantity() {
        return 1;
    }

    @Override
    public void setQuantity(int quantity) {
    }
}

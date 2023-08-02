import java.util.ArrayList;

/**
 * This method is responsible for displaying the inventory, or menu of the Vending Machine.
 */
public class Inventory {

    private VendingMachine vendingMachine;
    private ArrayList<Slot> inventory;

    /**
     * Constructs an inventory object given the Vending Machine that it is a part of.
     * @param vendingMachine Accepts the vending machine object.
     */
    public Inventory(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        inventory = new ArrayList<>(vendingMachine.getSlots());
    }
    /**
     * Method to print inventory.
     */
    public void displayInventory() {

        System.out.println("----- Inventory -----");
        System.out.println("Customizables:");
        ArrayList<Customizables> customizablesList = Customizables.getAvailableCustomizables();
        for (Customizables customizable : customizablesList) {
            System.out.println(customizable.getName());
            System.out.println("Price: " + customizable.getTotalPrice());
            System.out.println("Caloric yield per item: " + customizable.getTotalCalories());
            System.out.println("-----------------");
        }

        System.out.println();
        System.out.println("<><><><><><><><><><><><><><><><>");
        System.out.println();
        System.out.println("Individual Item Ingredients:");
        ArrayList<Item> availableItems = Customizables.getAvailableItems();
        for (Item item : availableItems) {
            System.out.println(item.getName());
            System.out.println("Price: " + item.getPrice());
            System.out.println("Caloric yield per item: " + item.getCalories());
            System.out.println("Available in slot: " + vendingMachine.getRemainingQuantity(item));
            System.out.println("-----------------");
        }

    }
}

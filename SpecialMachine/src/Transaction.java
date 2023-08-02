import java.util.ArrayList;
import java.util.Scanner;

/**
 * This method represents the Transaction that will occur when buying an ingredient or Customizable item.
 */
public class Transaction {
    private ArrayList<Purchaseable> purchaseablesList;
    private double totalPrice;
    private double amountPaid;

    /**
     * Consturcts a Transaction object given a purchaseable to accomodate both Items and Customizables, and the total price.
     * @param purchaseablesList Accepts an ArrayList of Purchaseables.
     * @param totalPrice Accepts the total price.
     */
    public Transaction(ArrayList<Purchaseable> purchaseablesList, double totalPrice) {
        this.purchaseablesList = purchaseablesList;
        this.totalPrice = totalPrice;
        amountPaid = 0;
    }

    /**
     * This method is responsible for printing the receipt details.
     */
    public void printReceipt() {
        for (Purchaseable item : purchaseablesList) {
            System.out.println("Item: " + item.getName());
            System.out.println("Price: " + item.getPrice());
            System.out.println("Calories: " + item.getQuantity());
            System.out.println("-------------------------------");
        }
        System.out.println("Price: $" + totalPrice);
        System.out.println("--------------------------------------");
    }

    /**
     * This method calculates the change that is to be returned to the user if they have a remaining balance left in the machine.
     * @param insertedAmount Accepts the inserted amount.
     * @return change Returns the difference of the inserted amount and the amount paid.
     */
    public double calculateChange(double insertedAmount) {
        double change = insertedAmount - amountPaid;
        amountPaid = 0;
        return change;
    }

    /**
     * This method checks if the inserted amount can afford what is being attempted to buy.
     * @param insertedAmount Accepts the inserted amount.
     * @return Returns a boolean value comparing if the inserted amount is greater than or equal to the amount paid.
     */
    public boolean checkSufficiency(double insertedAmount) {
        return insertedAmount >= amountPaid;
    }

    /**
     * This method is simply responsible for asking for confirmation when a transaction occurs.
     * @return Returns a boolean value if the input equals the intended string.
     */
    public boolean promptConfirmation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Confirm purchase for $" + totalPrice + "? (Y/N): ");
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("Y");
    }

}

import java.util.Scanner;
import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();

        Scanner scanner = new Scanner(System.in);

        boolean vendingMachineRunning = true;
        double totalBalance = 0;

        ArrayList<Money> moneyList = null;

        while (vendingMachineRunning) {
            System.out.println("------ Vending Machine Menu ------");
            System.out.println("1. Insert money");
            System.out.println("2. Purchase items");
            System.out.println("3. Customize a product");
            System.out.println("4. Check Balance");
            System.out.println("5. Display menu");
            System.out.println("6. Exit");
            System.out.println("7. Maintenance Menu");
            System.out.println("----------------------------------");
            System.out.println("Enter your choice (1-7): ");


            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Insert money into the vending machine.");
            boolean insertMoreMoney = false;
            moneyList = Money.createMoneyList();

            switch (choice) {
                case 1:
                    System.out.println("Insert money into the vending machine.");
                    insertMoreMoney = true;
                    moneyList = Money.createMoneyList();

                    while (insertMoreMoney) {
                        System.out.println("Available denominations and quantities:");
                        for (int i = 0; i < moneyList.size(); i++) {
                            Money money = moneyList.get(i);
                            System.out.println((i + 1) + ". $" + money.getDenomination() + " bills: " + money.getBills());
                        }

                        System.out.println("Enter the number of the denomination you want to insert (1-" + moneyList.size() + "): ");
                        int choice2 = scanner.nextInt();
                        scanner.nextLine();

                        if (choice2 < 1 || choice2 > moneyList.size()) {
                            System.out.println("Invalid choice. Please try again.");
                            continue;
                        }

                        System.out.println("Enter the quantity of bills: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();

                        Money selectedMoney = moneyList.get(choice2 - 1);
                        selectedMoney.setBills(selectedMoney.getBills() + quantity);

                        vendingMachine.insertMoney(selectedMoney);

                        System.out.println("Insert more money? (yes/no): ");
                        String insertMore = scanner.nextLine();
                        insertMoreMoney = insertMore.equalsIgnoreCase("yes");
                    }
                    break;

                case 2:
                    boolean itemFound = false;

                    while (!itemFound) {
                        System.out.println("Enter the item name you want to purchase (or 'cancel' to exit): ");
                        String itemNameInput = scanner.nextLine();

                        if (itemNameInput.equalsIgnoreCase("cancel")) {
                            break;
                        }

                        Item selectedItem = null;
                        ArrayList<Item> availableItems = Customizables.getAvailableItems();
                        for (Item availableItem : availableItems) {
                            if (availableItem.getName().equalsIgnoreCase(itemNameInput)) {
                                selectedItem = availableItem;
                                break;
                            }
                        }

                        if (selectedItem == null) {
                            System.out.println("Item not found. Please enter a valid item name.");
                        } else {
                            itemFound = true;

                            System.out.println("Enter the quantity you want to purchase: ");
                            int quantityToPurchase = scanner.nextInt();
                            scanner.nextLine();

                            Transaction purchaseTransaction = vendingMachine.purchaseIngredient(selectedItem, quantityToPurchase);

                            if (purchaseTransaction != null) {
                                double remainingBalance = vendingMachine.getBalance();
                                while (remainingBalance < 0) {
                                    System.out.println("Insufficient funds. You need $" + (-remainingBalance) + " more to complete the purchase.");
                                    System.out.println("Enter 'buy' to complete the purchase, or 'cancel' to exit: ");
                                    String action = scanner.nextLine();

                                    if (action.equalsIgnoreCase("cancel")) {
                                        remainingBalance = 0;
                                        double refundAmount = purchaseTransaction.calculateChange(remainingBalance);
                                        vendingMachine.insertMoney(new Money(1, (int) refundAmount));
                                        System.out.println("Transaction cancelled. $" + refundAmount + " returned.");
                                    } else if (action.equalsIgnoreCase("buy")) {
                                        remainingBalance = 0;
                                    }
                                }

                                if (remainingBalance >= 0) {
                                    System.out.println("Purchase successful!");
                                    purchaseTransaction.printReceipt();
                                }
                            }
                        }
                    }
                    break;

                case 3:
                    System.out.println("Available Customizable Products:");

                    ArrayList<Customizables> customizablesList = Customizables.getAvailableCustomizables();
                    for (int i = 0; i < customizablesList.size(); i++) {
                        Customizables customizable = customizablesList.get(i);
                        System.out.println((i + 1) + ". " + customizable.getName());
                    }

                    System.out.println("Enter the number of the product you want to customize (1-" + customizablesList.size() + "): ");
                    int productChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (productChoice < 1 || productChoice > customizablesList.size()) {
                        System.out.println("Invalid choice. Please try again.");
                        break;
                    }

                    System.out.println("Do you want to add additional ingredients? (Y/N): ");
                    String addIngredientsChoice = scanner.nextLine();

                    Customizables selectedCustomizable = customizablesList.get(productChoice - 1);

                    if (addIngredientsChoice.equalsIgnoreCase("Y")) {
                        System.out.println("Available Ingredients for Customization:");
                        ArrayList<Item> availableItems = Customizables.getAvailableItems();
                        for (int i = 0; i < availableItems.size(); i++) {
                            Item availableItem = availableItems.get(i);
                            System.out.println((i + 1) + ". " + availableItem.getName() + " (Price: $" + availableItem.getPrice() + ", Calories: " + availableItem.getCalories() + " cal)");
                        }

                        ArrayList<Item> selectedItems = new ArrayList<>();
                        ArrayList<Integer> quantities = new ArrayList<>();

                        while (true) {
                            System.out.println("Enter the number of the ingredient you want to add (1-" + availableItems.size() + "), or 0 to stop adding ingredients: ");
                            int ingredientChoice = scanner.nextInt();
                            scanner.nextLine();

                            if (ingredientChoice == 0) {
                                break;
                            }

                            if (ingredientChoice < 1 || ingredientChoice > availableItems.size()) {
                                System.out.println("Invalid choice. Please try again.");
                                continue;
                            }

                            Item selectedIngredient = availableItems.get(ingredientChoice - 1);
                            System.out.println("Enter the quantity of " + selectedIngredient.getName() + " you want to include: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine();

                            if (quantity > 0) {
                                selectedItems.add(selectedIngredient);
                                quantities.add(quantity);
                            }
                        }

                        for (int i = 0; i < selectedItems.size(); i++) {
                            Item selectedIngredient = selectedItems.get(i);
                            int quantity = quantities.get(i);
                            selectedCustomizable.addItem(selectedIngredient.getName(), quantity);
                        }
                    }

                    ArrayList<Item> availableItems = selectedCustomizable.getItems();
                    System.out.println("Available Items for Customization:");
                    for (int i = 0; i < availableItems.size(); i++) {
                        Item item2 = availableItems.get(i);
                        System.out.println((i + 1) + ". " + item2.getName() + " (Price: $" + item2.getPrice() + ", Calories: " + item2.getCalories() + " cal)");
                    }

                    ArrayList<Item> selectedItems = new ArrayList<>();
                    ArrayList<Integer> quantities = new ArrayList<>();

                    for (Item item3 : availableItems) {
                        System.out.println("Enter the quantity of " + item3.getName() + " you want to include: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();

                        if (quantity > 0) {
                            selectedItems.add(item3);
                            quantities.add(quantity);
                        }
                    }

                    vendingMachine.prepareCustomizableProduct(selectedCustomizable, selectedItems, quantities);

                    System.out.println("Do you want to purchase this customizable product? (Y/N): ");
                    String purchaseChoice = scanner.nextLine();
                    if (purchaseChoice.equalsIgnoreCase("Y")) {
                        ArrayList<Purchaseable> itemsToPurchase = new ArrayList<>(selectedItems);
                        itemsToPurchase.add(selectedCustomizable);
                        Transaction purchaseTransaction = vendingMachine.purchaseItem(itemsToPurchase);
                        if (purchaseTransaction != null) {
                            System.out.println("Purchase successful!");
                            purchaseTransaction.printReceipt();
                        }
                    }
                    break;

                case 4:
                    double balance = vendingMachine.getBalance();
                    System.out.println("Your current balance is: $" + balance);
                    break;

                case 5:
                    System.out.println("Current Inventory:");
                    vendingMachine.displayCurrentInventory();
                    break;

                case 6:
                    vendingMachine.printTransactionReceipts();
                    vendingMachine.exitVendingMachine();
                    return;

                case 7:
                    System.out.println("------ Maintenance Menu ------");
                    System.out.println("1. Add item");
                    System.out.println("2. Restock items");
                    System.out.println("3. Change item price");
                    System.out.println("4. Collect revenue");
                    System.out.println("5. Replenish Money");
                    System.out.println("6. Exit Maintenance");
                    System.out.println("-----------------------------");
                    System.out.println("Enter your choice (1-6): ");

                    int maintenanceChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (maintenanceChoice) {

                        case 1:
                            System.out.println("Enter the name of the item: ");
                            String itemName = scanner.nextLine();

                            System.out.println("Enter the price of the item: ");
                            double itemPrice = scanner.nextDouble();

                            System.out.println("Enter the calories of the item: ");
                            double itemCalories = scanner.nextDouble();

                            System.out.println("Enter the quantity of the item: ");
                            int itemQuantity = scanner.nextInt();
                            scanner.nextLine();

                            Item item = new Item(itemName, itemPrice, itemCalories, itemQuantity);

                            vendingMachine.addItem(item, itemQuantity);

                            System.out.println("Item added successfully.");
                            break;

                        case 2:
                            System.out.println("Enter the name of the item to restock: ");
                            String restockItemName = scanner.nextLine();

                            System.out.println("Enter the quantity to restock: ");
                            int restockQuantity = scanner.nextInt();
                            scanner.nextLine();

                            Item restockItem = null;
                            for (Slot slot : vendingMachine.getSlots()) {
                                if (slot.getItem().getName().equalsIgnoreCase(restockItemName)) {
                                    restockItem = slot.getItem();
                                    break;
                                }
                            }

                            if (restockItem == null) {
                                System.out.println("Item not found. Please enter a valid item name.");
                            } else {
                                int currentInventory = vendingMachine.getRemainingQuantity(restockItem);
                                vendingMachine.restockItem(restockItem, restockQuantity);
                                int newInventory = vendingMachine.getRemainingQuantity(restockItem);
                                System.out.println("Items restocked successfully.");
                                System.out.println("Previous inventory: " + currentInventory);
                                System.out.println("New inventory: " + newInventory);
                            }
                            break;


                        case 3:
                            System.out.println("Enter the name of the item to change the price: ");
                            String itemToChangePrice = scanner.nextLine();

                            Item targetItem = null;
                            for (Slot slot : vendingMachine.getSlots()) {
                                if (slot.getItem().getName().equalsIgnoreCase(itemToChangePrice)) {
                                    targetItem = slot.getItem();
                                    break;
                                }
                            }

                            if (targetItem == null) {
                                System.out.println("Item not found. Please enter a valid item name.");
                            } else {
                                System.out.println("Enter the new price for " + targetItem.getName() + ": ");
                                double newPrice = scanner.nextDouble();
                                scanner.nextLine();

                                vendingMachine.changeItemPrice(targetItem, newPrice);
                                System.out.println(targetItem.getName() + " price updated to $" + targetItem.getPrice());
                            }
                            break;

                        case 4:
                            double revenue = vendingMachine.collectRevenue();
                            totalBalance = vendingMachine.getTotalBalance();

                            if (revenue > 0) {
                                System.out.println("Collecting revenue. Total revenue: $" + revenue);
                                System.out.println("Current balance in the vending machine: $" + totalBalance);

                                double changeAmount = totalBalance - revenue;
                                if (changeAmount > 0) {
                                    System.out.println("Dispensing change: $" + changeAmount);
                                    ArrayList<Money> change = vendingMachine.getChange(changeAmount);
                                    if (change != null) {
                                        for (Money money : change) {
                                            System.out.println("$" + money.getDenomination() + " bills: " + money.getBills());
                                        }
                                    } else {
                                        System.out.println("Unable to dispense change. Please add smaller denominations to the vending machine.");
                                    }
                                } else {
                                    System.out.println("No change to dispense.");
                                }
                            } else {
                                System.out.println("No revenue to collect.");
                            }
                            break;

                        case 5:
                            System.out.println("Add money to the vending machine.");
                            moneyList = Money.createMoneyList();

                            System.out.println("Available denominations and quantities:");
                            for (int i = 0; i < moneyList.size(); i++) {
                                Money money = moneyList.get(i);
                                System.out.println((i + 1) + ". $" + money.getDenomination() + " bills: " + money.getBills());
                            }

                            System.out.println("Enter the number of the denomination you want to add (1-" + moneyList.size() + "): ");
                            int choice5 = scanner.nextInt();
                            scanner.nextLine();

                            if (choice5 < 1 || choice5 > moneyList.size()) {
                                System.out.println("Invalid choice. Please try again.");
                                break;
                            }

                            System.out.println("Enter the quantity of bills: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine();

                            Money selectedMoney = moneyList.get(choice5 - 1);
                            selectedMoney.setBills(quantity);

                            ArrayList<Money> moneyToAdd = new ArrayList<>();
                            moneyToAdd.add(selectedMoney);

                            vendingMachine.addMoneyToVendingMachine(moneyToAdd);

                            System.out.println("Money added successfully.");
                            break;

                        case 6:
                            System.out.println("Exiting maintenance.");
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Vending machine is now closed.");
        scanner.close();
    }
}

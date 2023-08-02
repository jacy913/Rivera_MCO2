import java.util.ArrayList;

/**
 * This method represents the Vending Machine and its features.
 */
public class VendingMachine {
    private ArrayList<Slot> slots;
    private ArrayList<Transaction> transactions;
    private Inventory inventory;
    private ArrayList<Money> money;
    private double balance;
    private double vendorCash;

    /**
     * Constructs a vending machine object.
     */
    public VendingMachine() {
        this.slots = new ArrayList<>();
        this.balance = 0;
        this.transactions = new ArrayList<Transaction>();
        this.inventory = new Inventory(this);
        this.money = new ArrayList<Money>();
    }

    /**
     * Adds an item to a slot in the vending machine. Checks if an item exists, hence adding a slot if it does not.
     * @param item Accepts the item object.
     * @param amt Accepts the amount of that item.
     */
    public void addItem(Item item, int amt) {
        for (Slot slot : slots) {
            if (slot.getItem().equals(item)) {
                slot.setQuantity(slot.getQuantity() + amt);
                return;
            }
        }
        slots.add(new Slot(item, amt));
    }

    /**
     * This method removes an items from a slot.
     * @param item Specifies the item on which to be removed
     * @param quantity Accepts the amount of that item.
     * @return Returns the new quantity of that item after removal.
     */
    public int removeItem(Item item, int quantity) {
        for (Slot slot : slots) {
            if (slot.getItem().equals(item)) {
                slot.removeItem(quantity);
                return slot.getQuantity();
            }
        }
        return -1;
    }

    /**
     * This method inserts money into the Vending Machine.
     * @param money Accepts the money object.
     */
    public void insertMoney(Money money) {
        this.money.add(new Money(money.getDenomination(), money.getBills()));
        double inserted = money.getDenomination() * money.getBills();
        balance += inserted;

        System.out.println("You have inserted " + money.getBills() + " bills worth " + money.getDenomination() + " each.");
        System.out.println("You have inserted $" + inserted);
    }

    /**
     * This method allows a customizable to be purchased. This method is particular to a customizable, not an ingredient or item.
     * @param itemsToPurchase Accepts an ArrayList of Purchaseables.
     * @return Returns a Transaction object if the purchase went through, or null if otherwise.
     */
    public Transaction purchaseItem(ArrayList<Purchaseable> itemsToPurchase) {
        double totalAmountPaid = 0;
        for (Purchaseable item : itemsToPurchase) {
            if (item instanceof Item) {
                totalAmountPaid += ((Item) item).getPrice() * ((Item) item).getQuantity();
            } else if (item instanceof Customizables) {
                totalAmountPaid += ((Customizables) item).getTotalPrice();
            }
        }

        Transaction transaction = new Transaction(itemsToPurchase, totalAmountPaid);
        transactions.add(transaction);

        boolean isConfirmed = transaction.promptConfirmation();

        if (transaction.checkSufficiency(balance) && isConfirmed) {
            balance -= totalAmountPaid;
            vendorCash += totalAmountPaid;

            for (Purchaseable item : itemsToPurchase) {
                if (item instanceof Item) {
                    Item individualItem = (Item) item;
                    int availableQuantity = individualItem.getQuantity();
                    individualItem.setQuantity(availableQuantity - individualItem.getQuantity());
                }
            }

            return transaction;
        } else if (!isConfirmed) {
            balance += totalAmountPaid;
            System.out.println("Transaction cancelled. $" + totalAmountPaid + " refunded.");
        } else {
            System.out.println("Insufficient funds. Please insert more money.");
        }

        return null;
    }

    /**
     * This method handles purchasing individual food items or ingredients of a customizable.
     * @param ingredient Accepts an Item object.
     * @param quantity Accepts the quantity of that object.
     * @return Returns a Transaction object if the purchase went through, or null if otherwise.
     */
    public Transaction purchaseIngredient(Item ingredient, int quantity) {
        int availableQuantity = ingredient.getQuantity();

        if (quantity > availableQuantity) {
            System.out.println("Insufficient stock. Available quantity: " + availableQuantity);
            return null;
        }

        double totalAmountPaid = ingredient.getPrice() * quantity;

        ArrayList<Purchaseable> purchaseablesList = new ArrayList<>();
        purchaseablesList.add(ingredient);

        Transaction transaction = new Transaction(purchaseablesList, totalAmountPaid);
        transactions.add(transaction);

        boolean isConfirmed = transaction.promptConfirmation();

        if (transaction.checkSufficiency(balance) && isConfirmed) {
            balance -= totalAmountPaid;
            vendorCash += totalAmountPaid;
            ingredient.setQuantity(availableQuantity - quantity);
            return transaction;
        } else if (!isConfirmed) {
            balance += totalAmountPaid;
            System.out.println("Transaction cancelled. $" + totalAmountPaid + " refunded.");
            transaction = null;
        } else {
            System.out.println("Insufficient funds. Please insert more money.");
            transaction = null;
        }

        return null;
    }

    /**
     * This method calls the Transaction class for printing the necessary transactions that occured.
     */
    public void printTransactionReceipts() {
        System.out.println("------ Receipt: All Transactions ------");
        for (Transaction transaction : transactions) {
            if (transaction != null) {
                transaction.printReceipt();
            } else {
                System.out.println("Purchase did not go through.");
            }
        }
    }

    /**
     * This method gets the change required to dispense.
     * @param amount Accepts the amount of change needed to be dispensed.
     * @return changeList Returns an ArrayList of Money if the Vending Machine has enough change to return, or null if otherwise.
     */
    public ArrayList<Money> getChange(double amount) {
        ArrayList<Money> changeList = new ArrayList<>();

        int remainingAmount = (int) (amount * 100);

        for (Money money : this.money) {
            int denomination = money.getDenomination();
            int billsAvailable = money.getBills();
            int numBillsToUse = Math.min(remainingAmount / denomination, billsAvailable);

            if (numBillsToUse > 0) {
                changeList.add(new Money(denomination, numBillsToUse));
                remainingAmount -= numBillsToUse * denomination;
            }

            if (remainingAmount == 0) {
                break;
            }
        }

        if (remainingAmount > 0) {
            System.out.println("Sorry, the vending machine does not have enough change to return.");
            return null;
        }

        for (Money change : changeList) {
            Money originalMoney = this.money.get(this.money.indexOf(change));
            originalMoney.setBills(originalMoney.getBills() - change.getBills());
        }

        return changeList;
    }

    /**
     * This method is mainly responsible for converting the remaining balance into denominations that need to be given.
     */
    public void exitVendingMachine() {
        double remainingBalance = getBalance();

        if (remainingBalance > 0) {
            System.out.println("Thank you for using the Vending Machine");
            System.out.println("Returning your inserted money: ");
            ArrayList<Money> moneyList = Money.createMoneyList();

            for (int i = moneyList.size() - 1; i >= 0; i--) {
                Money money = moneyList.get(i);
                int denomination = money.getDenomination();
                int numBills = (int) (remainingBalance / denomination);

                if (numBills > 0) {
                    System.out.println("Denomination: $" + denomination + " Bills: " + numBills);
                    remainingBalance -= numBills * denomination;
                }
            }
        }
    }

    /**
     * This method is responsible for preparing a Customizable product, and will ready it for customization and purchase.
     * @param selectedCustomizable Specifies the certain Customizable that the user chose.
     * @param selectedItems Specifies the items that will be added to the Customizable.
     * @param quantities Amount of that item for customization.
     * @return itemsToPurchase Returns an ArrayList of Purchaseables that are to be bought.
     */
    public ArrayList<Purchaseable> prepareCustomizableProduct(Customizables selectedCustomizable, ArrayList<Item> selectedItems, ArrayList<Integer> quantities) {
        ArrayList<Purchaseable> itemsToPurchase = new ArrayList<>();

        double totalPrice = 0;
        double totalCalories = 0;

        for (int i = 0; i < selectedItems.size(); i++) {
            Item item = selectedItems.get(i);
            int quantity = quantities.get(i);
            item.setQuantity(quantity);

            totalPrice += item.getPrice() * quantity;
            totalCalories += item.getCalories() * quantity;
        }

        System.out.println("Total Price: $" + totalPrice);
        System.out.println("Total Calories: " + totalCalories + " cal");

        System.out.println("Customized " + selectedCustomizable.getName() + ":");
        for (int i = 0; i < selectedItems.size(); i++) {
            Item item = selectedItems.get(i);
            int quantity = quantities.get(i);
            System.out.println("- " + item.getName() + ": " + quantity);
            itemsToPurchase.add(item);
        }

        itemsToPurchase.add(selectedCustomizable);

        return itemsToPurchase;
    }

    /**
     * This method adds the item to a slot in the Vending Machine.
     */
    public void addIndividualItem() {
        ArrayList<Item> item = Customizables.getAvailableItems();

        for(Item item1 : item) {
            slots.add(new Slot(item1, item1.getQuantity()));
        }
    }

    /**
     * This method displays the current menu or inventory by calling Inventory.
     */
    public void displayCurrentInventory() {
        inventory.displayInventory();
    }

    /**
     * This method resets the cash that the Vending Machine has for change in case of collecting revenue.
     */
    public void resetVendorCash() {
        vendorCash = 0;
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * This method gets the remaining quantity of an item to update its current stock.
     * @param item Specifies the item.
     * @return Returns the quantity if the item is found and an instance of Purchaseable, or 0 if otherwise.
     */
    public int getRemainingQuantity(Item item) {

        addIndividualItem();

        if (item instanceof Purchaseable) {
            for (Slot slot : slots) {
                if (slot.getItem().equals(item)) {
                    return slot.getQuantity();
                }
            }
        }
        return 0;
    }

    /**
     * This method calculates the balance that the user has after inserting Money into the machine.
     * @return total Returns the total balance that the user has to spend.
     */
    public double getTotalBalance() {
        double total = 0;
        for (Money money : this.money) {
            total += money.getDenomination() * money.getBills();
        }
        return total;
    }

    /**
     * This method restocks an item by calling addItem.
     * @param item Specifies the item.
     * @param quantity Accepts the amount of that item to be restocked.
     */
    public void restockItem(Item item, int quantity) {
        addItem(item, quantity);
    }

    /**
     * This method updates the item price.
     * @param item Specifies the item.
     * @param newPrice Accepts the new amount of which the item will be priced to.
     */
    public void changeItemPrice(Item item, double newPrice) {
        item.setPrice(newPrice);
    }

    /**
     * This collects the profits made in the vending machine, and resets it to zero after collection.
     * @return revenue Returns the revenue made.
     */
    public double collectRevenue() {
        double revenue = vendorCash;
        resetVendorCash();
        return revenue;
    }

    /**
     * This allows money to be added into the vending machine during maintenance.
     * @param moneyList Accepts a list of money to be inserted.
     */
    public void addMoneyToVendingMachine(ArrayList<Money> moneyList) {
        for (Money money : moneyList) {
            for (Money machineMoney : this.money) {
                if (machineMoney.getDenomination() == money.getDenomination()) {
                    machineMoney.setBills(machineMoney.getBills() + money.getBills());
                    break;
                }
            }
        }
    }
}



import java.util.ArrayList;

/**
 * This class represents the Money objects that are to be inserted into the Vending Machine.
 */
public class Money {
    private int denomination;
    private int bills;

    /**
     * Constructs a Money object given the denomination and bills.
     * @param denom Accepts the denomination of the bill.
     * @param bills Accepts the amount of bills.
     */
    public Money(int denom, int bills) {
        denomination = denom;
        this.bills = bills;
    }

    public int getDenomination() {
        return denomination;
    }

    public int getBills() {
        return bills;
    }

    public void setBills(int bills) {
        this.bills = bills;
    }

    /**
     * This method creates the default Money objects that the Vending Machine accepts.
     * @return moneyList Returns an ArrayList of Money objects.
     */
    public static ArrayList<Money> createMoneyList() {
        ArrayList<Money> moneyList = new ArrayList<>();
        moneyList.add(new Money(1, 0));
        moneyList.add(new Money(5, 0));
        moneyList.add(new Money(10, 0));
        moneyList.add(new Money(20, 0));
        return moneyList;
    }
}

/**
 * This interface is responsible for serving as the format for Customizables and Items primarily for the Transaction.
 */
public interface Purchaseable {
    String getName();
    double getPrice();
    int getQuantity();
    void setQuantity(int quantity);
}

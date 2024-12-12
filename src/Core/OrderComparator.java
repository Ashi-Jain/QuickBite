package Core;
import Core.Order;
import java.util.Comparator;

public class OrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        // VIP orders should have higher priority (assume VIP is indicated by a boolean flag in Order)
        if (o1.isVIP() && !o2.isVIP()) {
            return -1; // o1 has higher priority
        } else if (!o1.isVIP() && o2.isVIP()) {
            return 1; // o2 has higher priority
        } else {
            // If both are of the same type, compare by order time
            return o1.getOrderTime().compareTo(o2.getOrderTime());
        }
    }
}

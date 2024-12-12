package User;

import Core.Cart;
import Core.FoodItem;
import Core.Order;
import java.util.ArrayList;
import java.util.List;

public class VIPCustomer extends User {
    public Cart cart;
    private List<Order> orderHistory;
    private Admin admin;

    public VIPCustomer(String name, String userId,Admin admin) {
        super(name, userId);
        this.cart = new Cart();
        this.admin = admin;
        this.orderHistory = new ArrayList<>();
    }

    public void addToCart(FoodItem item, int quantity) {
        cart.addItem(item, quantity);
    }

    public void removeFromCart(FoodItem item) {
        cart.removeItem(item);
    }

    public void placeOrder() {
        double total = cart.calculateTotal();
        // Create the order with only 3 arguments as expected by the constructor
        Order order = new Order(this, true, "");  // Adjust vipPriority and specialRequest as needed
        // Add items to the order
        for (FoodItem item : cart.getItems().keySet()) {
            order.addItem(item, cart.getItems().get(item));
        }
        orderHistory.add(order);
        admin.addOrder(order);
        cart.clearCart(); // Clear cart after placing an order
        System.out.println("Order placed successfully. Total: " + total);
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void viewOrderStatus() {
        if (orderHistory.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        for (Order order : orderHistory) {
            System.out.println("Order ID: " + order.getOrderId() + ", Status: " + order.getStatus());
        }
    }

    public void cancelOrder(int orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId() == orderId && order.canBeCancelled()) {
                order.setStatus("Cancelled");
                System.out.println("Order ID " + orderId + " cancelled successfully.");
                return;
            }
        }
        System.out.println("Order cannot be cancelled.");
    }
}



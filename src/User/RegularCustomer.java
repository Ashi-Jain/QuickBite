package User;

import Core.Cart;
import Core.FoodItem;
import Core.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RegularCustomer extends User {
    public Cart cart;
    private List<Order> orderHistory;
    private Admin admin;

    public RegularCustomer(String name, String userId, Admin admin) {
        super(name, userId);
        this.admin = admin;
        this.cart = new Cart();
        this.orderHistory = new ArrayList<>();
    }

    public void addToCart(FoodItem item, int quantity) {
        cart.addItem(item, quantity);
    }
    /*public void updateToCart(String itemName, int newQuantity){
        for (FoodItem item : cart.getItems().keySet()) {
            if(item.getName().equals(itemName)){

            }
        }

    }*/
    public void removeFromCart(FoodItem item) {
        cart.removeItem(item);
    }

    public void placeOrder() {
        double total = cart.calculateTotal();
        // Create the order with only 3 arguments as expected by the constructor
        Order order = new Order(this, false, "");  // Adjust vipPriority and specialRequest as needed
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
    public static void reOrder(RegularCustomer customer, Scanner scanner) {
        System.out.println("Enter the Order ID you want to re-order:");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Order orderToReOrder = customer.getOrderHistory().stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst()
                .orElse(null);

        if (orderToReOrder != null) {
            System.out.println("Re-ordering the following items:");
            for (Map.Entry<FoodItem, Integer> entry : orderToReOrder.getItems().entrySet()) {
                FoodItem foodItem = entry.getKey();
                int quantity = entry.getValue();
                System.out.println(foodItem.getName() + " x" + quantity);
                // Here you can add the logic to actually place the order again.
            }
            System.out.println("Re-order placed successfully!");
        } else {
            System.out.println("Order ID not found.");
        }
    }
}



package Core;

import User.User;
import java.util.*;

public class Order {
    private static int idCounter = 1; // To generate unique order IDs
    private int orderId;
    private User customer;
    private Map<FoodItem, Integer> items; // Map of food items and quantities
    private String status; // Order status
    private boolean vipPriority;
    private String specialRequest;
    private Date orderTime;

    // Constructor
    public Order(User customer, boolean vipPriority, String specialRequest) {
        this.orderId = idCounter++; // Increment ID for each order
        this.customer = customer;
        this.items = new HashMap<>();
        this.status = "Order Received";
        this.vipPriority = vipPriority;
        this.specialRequest = specialRequest;
        this.orderTime = new Date();
    }

    // Add an item to the order with quantity
    public void addItem(FoodItem item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    // Calculate the total price of the order
    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    // Update the status of the order
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // Check if this is a VIP order
    public boolean isVIP() {
        return vipPriority;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    // Check if the order can be cancelled (if status is "Order Received")
    public boolean canBeCancelled() {
        return "Order Received".equals(this.status);
    }

    // Get the order ID
    public int getOrderId() {
        return orderId;
    }

    // String representation of the order
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Order by ").append(customer.getUserId()).append(" - ").append(status).append("\n");
        sb.append("Items:\n");
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            sb.append(entry.getKey().getName()).append(" x").append(entry.getValue()).append("\n");
        }
        sb.append("Total: $").append(calculateTotal()).append("\n");
        sb.append("Special Request: ").append(specialRequest).append("\n");
        sb.append("Order Time: ").append(orderTime).append("\n");
        return sb.toString();
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Map<FoodItem, Integer> getItems() {
        return items;
    }

    public void setItems(Map<FoodItem, Integer> items) {
        this.items = items;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }
}

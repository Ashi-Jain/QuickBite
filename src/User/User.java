package User;

import Core.Cart;
import Core.Order;

import java.util.ArrayList;
import java.util.List;

public class User {
    protected String name;
    protected String userId;
    protected List<Order> orderHistory;
    public Cart cart;
    // Constructor to initialize name and userId
    public User(String name, String userId) {
        this.name = name;
        this.userId = userId;
        this.orderHistory = new ArrayList<>();
    }
    // Common methods
    public boolean login(String name, String userId) {
        // Simple login validation logic
        if (this.name.equals(name) && this.userId.equals(userId)) {
            System.out.println("Login successful for user: " + name);
            return true;
        } else {
            System.out.println("Invalid credentials for user: " + name);
            return false;
        }
    }

    // Method to add an order to the user's order history
    public void addOrderToHistory(Order order) {
        orderHistory.add(order);
    }

    // Method to retrieve the user's order history
    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    // toString method to display user details
    @Override
    public String toString() {
        return "User ID: " + userId + ", Name: " + name;
    }

    // Getters for name and userId (optional based on requirements)
    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }


}

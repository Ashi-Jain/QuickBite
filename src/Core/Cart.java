package Core;

import java.util.*;

public class Cart {
    private Map<FoodItem, Integer> items;

    public Cart() {
        items = new HashMap<>();
    }

    public void addItem(FoodItem item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public void removeItem(FoodItem item) {
        items.remove(item);
    }

    public void updateQuantity(FoodItem item, int quantity) {
        if (items.containsKey(item)) {
            items.put(item, quantity);
        }
    }
    // Method to view the contents of the cart
    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            double totalPrice = 0.0;
            System.out.println("Your cart contains the following items:");

            for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
                FoodItem item = entry.getKey();
                int quantity = entry.getValue();
                double itemTotal = item.getPrice() * quantity;
                totalPrice += itemTotal;
                System.out.printf("%s (x%d) - $%.2f\n", item.getName(), quantity, itemTotal);
            }

            // Show the total price
            System.out.printf("Total price of your cart: $%.2f\n", totalPrice);
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public Map<FoodItem, Integer> getItems() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }
}


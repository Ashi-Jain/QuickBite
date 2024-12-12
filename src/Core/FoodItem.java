package Core;

import java.util.*;

public class FoodItem {
    private String name;
    private double price;
    public String category;

    private boolean available;
    private List<Review> reviews;

    public FoodItem(String name, double price, String category, boolean available){
        this.name = name;
        this.price=price;
        this.category=category;
        this.available=available;
        this.reviews=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return name + " (" + category + ") - $" + price + " - " + (available ? "Available" : "Not Available");
    }

    // Update price and availability
    public void updateDetails(double newPrice, boolean isAvailable) {
        this.price = newPrice;
        this.available = isAvailable;
    }

    // Add a new review
    public void addReview(Review review) {
        this.reviews.add(review);
    }

}

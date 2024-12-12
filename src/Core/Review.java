package Core;

import java.util.ArrayList;
import java.util.List;

public class Review {
    private String reviewText;
    private int rating;
    private List<Order> completedOrders;

    // Constructor
    public Review(String reviewText, int rating) {
        this.reviewText = reviewText;
        this.rating = rating;
        this.completedOrders = new ArrayList<>();

    }

    // String representation of the review
    @Override
    public String toString() {
        return "Rating: " + rating + "/5 - " + reviewText;
    }

    // Getters
    public String getReviewText() {
        return reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public List<Order> getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(List<Order> completedOrders) {
        this.completedOrders = completedOrders;
    }

}


import Core.FoodItem;
import Core.Order;
import Core.Cart;
import Core.Review;
import User.Admin;
import User.RegularCustomer;
import User.VIPCustomer;
import User.User;
import java.util.*;

public class Main {
    private static HashMap<String, User> customerDatabase = new HashMap<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Initialize admin and sample data
        Admin admin = new Admin("admin", "admin@123");
        admin.addMenuItem(new FoodItem("Pizza", 100, "Snacks", true));
        admin.addMenuItem(new FoodItem("Burger", 50, "Snacks", true));
        admin.addMenuItem(new FoodItem("Soda", 20, "Beverages", true));

        // Example customers (register users as needed)
        VIPCustomer vipCustomer = new VIPCustomer("Alice", "VIP000",admin);
        RegularCustomer regularCustomer = new RegularCustomer("Bob", "REG010",admin);
        Cart cart = new Cart();
        // Main loop
        while (true) {
            System.out.println("Welcome to QuickBite! Please select an option:");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login/Register");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    adminMenu(admin, scanner);
                    break;
                case 2:
                    customerMenu(admin, scanner);
                    break;
                case 3:
                    System.out.println("Thank you for using Byte Me! Goodbye.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Admin Menu
    public static void adminMenu(Admin admin, Scanner scanner) {
        System.out.print("Enter admin name: ");
        String name = scanner.nextLine();
        System.out.print("Enter admin userId: ");
        String userId = scanner.nextLine();

        if (admin.login(name, userId)) {
            while (true) {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. View Menu");
                System.out.println("2. Add Menu Item");
                System.out.println("3. Update Menu Item");
                System.out.println("4. Remove Menu Item");
                System.out.println("5. View Pending Orders");
                System.out.println("6. Process Order");
                System.out.println("7. Process Refund");
                System.out.println("8. Generate Daily Sales Report");
                System.out.println("9. Back to Main Menu");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        admin.viewMenu();
                        break;
                    case 2:
                        System.out.print("Enter item name: ");
                        String iname = scanner.nextLine();
                        System.out.print("Enter price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Enter category: ");
                        String category = scanner.nextLine();
                        admin.addMenuItem(new FoodItem(iname, price, category, true));
                        break;
                    case 3:
                        System.out.print("Enter item name to update: ");
                        name = scanner.nextLine();
                        System.out.print("Enter new price: ");
                        price = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Is the item available (true/false)? ");
                        boolean available = scanner.nextBoolean();
                        admin.updateMenuItem(name, price, available);
                        break;
                    case 4:
                        System.out.print("Enter item name to remove: ");
                        name = scanner.nextLine();
                        admin.removeMenuItem(name);
                        break;
                    case 5:
                        admin.viewPendingOrders();
                        break;
                    case 6:
                        System.out.print("Enter Order ID to update status: ");
                        int orderId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        Order order = findOrderById(admin, orderId);
                        if (order != null) {
                            System.out.print("Enter new status (e.g., 'Delivered', 'Preparing', 'Denied'): ");
                            String newStatus = scanner.nextLine();
                            admin.updateOrderStatus(order, newStatus);
                        } else {
                            System.out.println("Order with ID " + orderId + " not found.");
                        }
                        break;
                    case 7:
                        System.out.println("Process Refund");
                        System.out.print("Enter Order ID to refund: ");
                        int orderIdNo = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        Order orders = findOrderById(admin, orderIdNo);
                        if (orders != null) {
                            admin.processRefund(orders);
                        } else {
                            System.out.println("Order with ID " + orderIdNo + " not found.");
                        }
                        break;

                    case 8:
                        System.out.println(admin.generateDailySalesReport());
                        break;
                    case 9:
                        admin.logout();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }else {
            System.out.println("Invalid login credentials.");
        }
    }

    // Counters for unique customer IDs
    private static int regularCustomerCount = 0;
    private static int vipCustomerCount = 0;

    public static void customerMenu(Admin admin, Scanner scanner) {
        User customer = null;

        while (true) {
            System.out.println("\nCustomer Account:");
            System.out.println("1. Register as a New Customer");
            System.out.println("2. Login to Existing Account");
            System.out.println("3. Back to Main Menu");
            int accountChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (accountChoice == 1) { // Register
                customer = registerCustomer(admin, scanner);
                if (customer != null) {
                    System.out.println("Registration successful!");
                }
            } else if (accountChoice == 2) { // Login
                System.out.print("Enter your Customer ID: ");
                String userId = scanner.nextLine();

                // Find the customer by ID
                customer = findCustomerById(userId);
                if (customer != null) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Invalid Customer ID. Please try again.");
                    continue;
                }
            } else if (accountChoice == 3) {
                return; // Exit to main menu
            } else {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            // Once logged in or registered, navigate to the customer actions menu
            customeractions(admin, scanner, customer);
        }
    }

    // Updated registration method
    private static User registerCustomer(Admin admin, Scanner scanner) {
        int customerType=0;
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("REG/VIP: ");
        String userId = scanner.nextLine();
        new User(name,userId);
        if(userId.contains("REG")){
            customerType=1;
        }
        else{
            customerType=2;
        }
        if (customerType == 1) {
            regularCustomerCount++;
            userId = String.format("REG%03d", regularCustomerCount);
            RegularCustomer customer = new RegularCustomer(name, userId, admin);
            customerDatabase.put(userId, customer);  // Store in HashMap
            System.out.println("Registration successful! Your customer ID is: " + userId);
            return customer;
        } else if (customerType == 2) {
            System.out.print("VIP registration requires a $50 fee. Do you wish to proceed? (yes/no): ");
            String paymentConfirmation = scanner.nextLine();

            if (paymentConfirmation.equalsIgnoreCase("yes")) {
                vipCustomerCount++;
                userId = String.format("VIP%03d", vipCustomerCount);
                VIPCustomer customer = new VIPCustomer(name, userId, admin);
                customerDatabase.put(userId, customer);  // Store in HashMap
                System.out.println("Registration successful! Your VIP customer ID is: " + userId);
                return customer;
            } else {
                System.out.println("VIP registration canceled.");
                return null;
            }
        } else {
            System.out.println("Invalid customer type.");
            return null;
        }
    }

    // Customer Menu
    public static void customeractions(Admin admin, Scanner scanner, User customer) {
        if (customer == null) {
            System.out.println("Invalid customer. Exiting actions.");
            return;
        }

        while (true) {
            System.out.println("\nCustomer Menu:");
            // List actions
            System.out.println("1. View Menu");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. Place Order");
            System.out.println("5. View Order Status");
            System.out.println("6. View Order History");
            System.out.println("7. Handle Special Requests");
            System.out.println("8. Cancel Order");
            System.out.println("9. Search functionality");
            System.out.println("10. Filter by category");
            System.out.println("11. Sort by price");
            System.out.println("12. Provide reviews");
            System.out.println("13. View reviews");
            System.out.println("14. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    admin.viewMenu();
                    break;
                case 2:
                    System.out.print("Enter item name: ");
                    String itemName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    FoodItem item = admin.findMenuItem(itemName);
                    if (item != null) {
                        if (customer instanceof VIPCustomer) {
                            ((VIPCustomer) customer).addToCart(item, quantity);
                        } else {
                            ((RegularCustomer) customer).addToCart(item, quantity);
                        }
                        System.out.println("Item added to cart.");
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter item name to remove from cart: ");
                    itemName = scanner.nextLine();
                    item = admin.findMenuItem(itemName);
                    if (item != null) {
                        if (customer instanceof VIPCustomer) {
                            ((VIPCustomer) customer).removeFromCart(item);
                        } else {
                            ((RegularCustomer) customer).removeFromCart(item);
                        }
                        System.out.println("Item removed from cart.");
                    } else {
                        System.out.println("Item not found in cart.");
                    }
                    break;
                case 4:
                    handlePlaceOrderFlow(customer, scanner, admin);
                    break;
                case 5:
                    if (customer instanceof VIPCustomer) {
                        ((VIPCustomer) customer).viewOrderStatus();
                    } else {
                        ((RegularCustomer) customer).viewOrderStatus();
                    }
                    break;
                case 6:
                    System.out.println("Order History:");
                    List<Order> orderHistory = (customer instanceof VIPCustomer)
                            ? ((VIPCustomer) customer).getOrderHistory()
                            : ((RegularCustomer) customer).getOrderHistory();
                    for (Order order : orderHistory) {
                        System.out.println(order);
                    }
                    System.out.println("Do you want to reorder any previous orders? Yes/No");
                    String reorderOption = scanner.nextLine();
                    if(reorderOption.equalsIgnoreCase("Yes")){
                        System.out.println("Enter the Order ID you want to reorder:");
                        int orderId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        Order orderToReOrder = orderHistory.stream()
                                .filter(order -> order.getOrderId() == orderId)
                                .findFirst()
                                .orElse(null);

                        if (orderToReOrder != null) {
                            // Re-add items from the previous order to the cart
                            for (Map.Entry<FoodItem, Integer> entry : orderToReOrder.getItems().entrySet()) {
                                FoodItem foodItem = entry.getKey();
                                int quantityItem = entry.getValue();
                                if (customer instanceof VIPCustomer) {
                                    ((VIPCustomer) customer).addToCart(foodItem, quantityItem);
                                } else {
                                    ((RegularCustomer) customer).addToCart(foodItem, quantityItem);
                                }
                            }

                            System.out.println("Items from previous order have been added to the cart. Proceeding to place the order.");
                            handlePlaceOrderFlow(customer, scanner, admin);
                        } else {
                            System.out.println("Order ID not found.");
                        }
                    }
                    break;
                case 7:
                    System.out.println("Handle special requests");
                    System.out.print("Enter Order ID to handle special request: ");
                    int orderIdNo = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Order order = findOrderById(admin, orderIdNo);
                    System.out.println("What special request you want? ");
                    String specialRequest = scanner.nextLine();
                    if (order != null) {
                        admin.handleSpecialRequest(order,specialRequest);
                    }
                    else{
                        System.out.println("Order with ID " + orderIdNo + " not found.");
                    }
                    break;
                case 8:
                    System.out.print("Enter Order ID to cancel: ");
                    int orderId = scanner.nextInt();
                    if (customer instanceof VIPCustomer) {
                        ((VIPCustomer) customer).cancelOrder(orderId);
                    } else {
                        ((RegularCustomer) customer).cancelOrder(orderId);
                    }
                    System.out.println("Order canceled if eligible.");
                    break;
                case 9:
                    System.out.println("Search item with name");
                    System.out.print("Enter food item name to search: ");
                    String iname = scanner.nextLine();
                    admin.findMenuItem(iname);
                    break;
                case 10:
                    System.out.println("Filter by Category");
                    System.out.print("Enter category with which you want to search the food item: ");
                    String category = scanner.nextLine();
                    admin.filterMenuItemByCategory(category);
                    break;
                case 11:
                    System.out.println("Do you want to sort the food menu items by ascending order or descending order? ");
                    String sortOption = scanner.nextLine();
                    admin.SortByPrice(sortOption);
                    break;
                case 12:
                    System.out.println("You have ordered following items previously: ");
                    for(Order orders : admin.getCompletedOrders()){
                        System.out.println(orders);
                    }
                    System.out.println("Enter order id for which you want to review: ");
                    int OrderId = scanner.nextInt();
                    scanner.nextLine();
                    Order orderReview = admin.getCompletedOrders().stream()
                            .filter(orders -> orders.getOrderId()==OrderId)
                            .findFirst()
                            .orElse(null);

                    if(orderReview!=null){
                        // Ask for the food item to review
                        System.out.println("Enter the name of the food item for which you want to provide your review:");
                        String foodName = scanner.nextLine();
                        List<FoodItem> reviewFoodItems = new ArrayList<>(orderReview.getItems().keySet());
                        // Check if the food item exists in the order
                        boolean fooditemExists = reviewFoodItems.stream().anyMatch(foodItem -> foodItem.getName().equalsIgnoreCase(foodName));
                        if(fooditemExists){
                            System.out.println("Enter your review: ");
                            String reviewText = scanner.nextLine();

                            System.out.println("Enter your rating from 1 to 5: ");
                            int rating = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character

                            // Create the review
                            Review review = new Review(reviewText, rating);

                            // Find the corresponding food item to add the review to
                            FoodItem selectedFoodItem = reviewFoodItems.stream()
                                    .filter(foodItem -> foodItem.getName().equalsIgnoreCase(foodName))
                                    .findFirst()
                                    .orElse(null);

                            if (selectedFoodItem != null) {
                                selectedFoodItem.addReview(review);
                                System.out.println("Review added successfully");
                            }
                        }
                        else{
                            System.out.println("Food Item does not exist in order");
                        }
                    }
                    else{
                        System.out.println("Order doesn't exist in the completed Orders");
                    }
                    break;
                case 13:
                    System.out.println("Enter the name of the food item you want to find reviews for:");
                    String foodName = scanner.nextLine();

                    // Collect all reviews for the specified food item
                    List<Review> reviewsList = new ArrayList<>();

                    for (Order orders : admin.getCompletedOrders()) {
                        List<FoodItem> foodItemsInMenu = new ArrayList<>(orders.getItems().keySet());
                        for (FoodItem foodItem : foodItemsInMenu) {
                            if (foodItem.getName().equalsIgnoreCase(foodName)) {
                                reviewsList.addAll(foodItem.getReviews());
                            }
                        }
                    }

                    // Display the reviews
                    if (reviewsList.isEmpty()) {
                        System.out.println("No reviews found for " + foodName);
                    } else {
                        System.out.println("Reviews for " + foodName + ":");
                        for (Review review : reviewsList) {
                            System.out.println(review);
                        }
                    }
                    break;
                case 14:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    // Helper method to find an order by ID
    public static Order findOrderById(Admin admin, int orderId) {
        // Search both pending and completed orders for the given ID
        for (Order order : admin.getPendingOrders()) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        for (Order order : admin.getCompletedOrders()) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public static void CashiT(Scanner scanner){
        System.out.println("Enter the delivery details: ");
        System.out.println("Enter address1 details: ");
        String address1 = scanner.nextLine();
        System.out.println("Enter address2 details: ");
        String address2 = scanner.nextLine();
    }

    public static void CardiT(Scanner scanner){
        System.out.println("Enter the card details: ");
        System.out.println("Enter the card NUMBER: ");
        long cardNo = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter the expiry date and CVV: ");
        String expiryDate = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Payment Successful! ");
        CashiT(scanner);
    }
    // Method to find customer by ID
    private static User findCustomerById(String userId) {
        return customerDatabase.get(userId);
    }

    private static void handlePayment(User customer, Scanner scanner) {
        System.out.println("How would you like to pay? Cash/Card");
        String paymentOption = scanner.nextLine();
        if (paymentOption.equalsIgnoreCase("Cash")) {
            CashiT(scanner);
        } else {
            CardiT(scanner);
        }
        if (customer instanceof VIPCustomer) {
            ((VIPCustomer) customer).placeOrder();
        } else {
            ((RegularCustomer) customer).placeOrder();
        }
        System.out.println("Order placed successfully!");
    }

    private static void modifyCart(User customer, Admin admin, Scanner scanner) {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter quantity you want to modify for that particular item: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        FoodItem item = admin.findMenuItem(itemName);

        if (item != null) {
            if (customer instanceof VIPCustomer) {
                ((VIPCustomer) customer).cart.updateQuantity(item, quantity);
            } else {
                ((RegularCustomer) customer).cart.updateQuantity(item, quantity);
            }
            System.out.println("Quantity Updated for item.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void handlePlaceOrderFlow(User customer, Scanner scanner, Admin admin) {
        System.out.print("Do you want to modify the cart? Yes/No: ");
        String option = scanner.nextLine();
        if (option.equalsIgnoreCase("Yes")) {
            modifyCart(customer, admin, scanner);
        }
        //cart price view
        if (customer instanceof VIPCustomer) {
            ((VIPCustomer) customer).cart.viewCart();
        } else {
            ((RegularCustomer) customer).cart.viewCart();
        }

        System.out.println("Would you like to place your order? Yes/No");
        String orderPlace = scanner.nextLine();
        if (orderPlace.equalsIgnoreCase("Yes")) {
            handlePayment(customer, scanner);
        } else {
            System.out.println("Order not placed.");
        }
    }

}

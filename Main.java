package com.onlineshopping;
import java.util.*;
import java.io.*;
public class Main {
	 public static void main(String[] args) {
		        Scanner scanner = new Scanner(System.in);
		        List<Product> productList = new ArrayList<>();
		        Cart cart = new Cart();
		        List<Order> orderHistory = new ArrayList<>();

		        // Load sample data from a file
		        loadSampleData(productList);

	        System.out.println("Welcome to the Online Shopping Console App!");
	       // User customer = createUser(scanner);
	        System.out.print("Enter your name: ");
	        String customerName = scanner.nextLine();
	        System.out.print("Enter your email: ");
	        String customerEmail = scanner.nextLine();

	        User customer1 = new User(customerName, customerEmail);

	        while (true) {
	            System.out.println("\nAvailable Products:");
	            for (Product product : productList) {
	                System.out.println(product);
	            }

	            System.out.print("\nEnter product ID to add to cart (or 0 to checkout): ");
	            int productId = scanner.nextInt();
	            if (productId == 0) {
	                break;
	            }
	            if (productId > 0 && productId <= productList.size()) {
	                Product selectedProduct = productList.get(productId - 1);
	                cart.addItem(selectedProduct);
	                System.out.println(selectedProduct.getName() + " added to cart.");
	            } else {
	                System.out.println("Invalid product ID.");
	            }
	        }

	        System.out.println("\nCart items:");
	        for (Product item : cart.getItems()) {
	            System.out.println(item);
	        }

	        System.out.println("Cart total: $" + cart.getTotalPrice());

	        // Select payment method
	        System.out.println("\nSelect payment method:");
	        System.out.println("1. Credit Card");
	        System.out.println("2. PayPal");
	        int paymentChoice = scanner.nextInt();
	        PaymentMethod paymentMethod;

	        if (paymentChoice == 1) {
	            System.out.print("Enter credit card number: ");
	            String cardNumber = scanner.next();
	            System.out.print("Enter expiration date (MM/YYYY): ");
	            String expirationDate = scanner.next();
	            paymentMethod = new CreditCardPayment(cardNumber, expirationDate);
	        } else if (paymentChoice == 2) {
	            System.out.print("Enter PayPal email: ");
	            String email = scanner.next();
	            paymentMethod = new PayPalPayment(email);
	        } else {
	            System.out.println("Invalid payment choice.");
	            return;
	        }

	        paymentMethod.processPayment(cart.getTotalPrice());

	        Order order = new Order(customer1, cart, paymentMethod);
	        orderHistory.add(order);

	        System.out.println("\nThank you for shopping with us, " + customer1.getName() + "!");
	        System.out.println("Order Summary:\n" + orderSummary(order));

	        // Save order history to a file
	        saveOrderHistory(orderHistory);
	    }

	    public static String orderSummary(Order order) {
	        StringBuilder summary = new StringBuilder();
	        summary.append("Order Summary:\n");
	        summary.append("User:\n").append(order.getUser()).append("\n");
	        summary.append("Cart items:\n");
	        for (Product item : order.getCart().getItems()) {
	            summary.append(item.getName()).append(" - $").append(item.getPrice()).append("\n");
	        }
	        summary.append("Total Price: $").append(order.getCart().getTotalPrice()).append("\n");
	        summary.append("Payment Method: ").append(order.getPaymentMethod().getClass().getSimpleName()).append("\n");
	        return summary.toString();
	    }
	    public static void loadSampleData(List<Product> productList) {
	        try (Scanner fileScanner = new Scanner(new File("products.txt"))) {
	            while (fileScanner.hasNextLine()) {
	                String line = fileScanner.nextLine();
	                String[] parts = line.split(",");
	                if (parts.length == 4) {
	                    int id = Integer.parseInt(parts[0]);
	                    String name = parts[1];
	                    String description = parts[2];
	                    double price = Double.parseDouble(parts[3]);
	                    productList.add(new Product(id, name, description, price));
	                }
	            }
	        } catch (FileNotFoundException e) {
	            System.out.println("Error loading sample data: " + e.getMessage());
	        }
	    }
//	    public static User createUser(Scanner scanner) {
////	        System.out.print("Enter your name: ");
////	        String name = scanner.nextLine();
////	        System.out.print("Enter your email: ");
////	        String email = scanner.nextLine();
////	        return new User(name, email);
//	    }
	    public static void saveOrderHistory(List<Order> orderHistory) {
	        try (PrintWriter writer = new PrintWriter(new FileWriter("order_history.txt"))) {
	            for (Order order : orderHistory) {
	                writer.println("User: " + order.getUser().getName());
	                writer.println("Items:");
	                for (Product item : order.getCart().getItems()) {
	                    writer.println(item.getName() + " - $" + item.getPrice());
	                }
	                writer.println("Total Price: $" + order.getCart().getTotalPrice());
	                writer.println("Payment Method: " + order.getPaymentMethod().getClass().getSimpleName());
	                writer.println();
	            }
	        } catch (IOException e) {
	            System.out.println("Error saving order history: " + e.getMessage());
	        }
	    }
	 
}

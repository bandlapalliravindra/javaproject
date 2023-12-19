package javapraject;


	
	import java.sql.*;
	import java.util.*;

	public class hotelbilling {
	    private static Connection getConnection() throws SQLException {
	        String url = "jdbc:mysql://localhost:3306/menulist";
	        String username = "root";
	        String password = "root";
	        return DriverManager.getConnection(url, username, password);
	    }

	    private static void showMenu() {
	        try (Connection connection = getConnection();
	             Statement statement = connection.createStatement();
	        		//String create Table ="menulist.menu' doesn't exist(";
	        		
	             ResultSet resultSet = statement.executeQuery("SELECT * FROM menulist")) {
	            System.out.println("Menu:");
	            while (resultSet.next()) {
	                String itemName = resultSet.getString("itemname");
	                double itemPrice = resultSet.getDouble("price");
	                System.out.println(itemName + " - $" + itemPrice);
	            }
	        } catch (SQLException e) {
	            handleSQLException(e);
	        }
	    }

	    private static void calculateBilling() {
	        double totalBill = 0;
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement("SELECT price FROM menulist WHERE itemname = ?")) {
	            Scanner scanner = new Scanner(System.in);
	            System.out.println("Enter nomber of item ");
	            int item = scanner.nextInt();
	            scanner.nextLine();

	            for (int i = 1; i <= item; i++) {
	                System.out.println("Enter item name " + i + ": ");
	                String itemName = scanner.nextLine();

	                preparedStatement.setString(1, itemName);
	                ResultSet resultSet = preparedStatement.executeQuery();

	                if (resultSet.next()) {
	                    double itemPrice = resultSet.getDouble("price");
	                    totalBill += itemPrice;
	                } else {
	                    System.out.println("Invalid item name: " + itemName);
	                }
	            }
	           
	            System.out.println("Total bill: $" + totalBill);
	        } catch (SQLException e) {
	            handleSQLException(e);
	        }
	    }

	    private static void handleSQLException(SQLException e) {
	        // Handle SQL exceptions appropriately (e.g., logging, custom error messages, etc.)
	        e.printStackTrace();
	    }

	    public static void main(String[] args) {
	        showMenu();
	        calculateBilling();
	        
	    }
	

	}



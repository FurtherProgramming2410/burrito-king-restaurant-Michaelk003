package controller;

import dao.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.Order;

import javax.swing.*;
import java.sql.*;
import java.time.LocalTime;

public class ViewOrderController {
    private Model model;
    private Stage stage;
    private Stage parentStage;
    private HomeController parentController;

    @FXML
    private Button pending;
    @FXML
    private Button finalised;
    @FXML
    private Button all;
    @FXML
    private ListView<String> list;
    @FXML
    private TextField output;
    @FXML
    private Button cancel;
    @FXML
    private Button pickup;

    private ObservableList<String> orders = FXCollections.observableArrayList();
    private String selectedOrder;

    public ViewOrderController(Stage parentStage, Model model, HomeController parentController) {
        this.stage = new Stage();
        this.model = model;
        this.parentStage = parentStage;
        this.parentController = parentController;
    }

    public void showStage(VBox pane) {
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("View Order");
        stage.show();
    }

    @FXML
    public void initialize() {
        list.setItems(orders);

        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedOrder = newValue;
            if (selectedOrder != null) {
                displayOrderDetails(selectedOrder);
            }
        });

        pending.setOnAction(event -> loadOrders("Placed"));
        finalised.setOnAction(event -> loadOrders("Finalised"));
        all.setOnAction(event -> loadOrders(null));

        cancel.setOnAction(event -> updateOrderStatus("Cancelled", null));
        pickup.setOnAction(event -> handlePickupOrder());

        loadOrders(null);
    }

    private void loadOrders(String status) {
        orders.clear();
        String username = model.getCurrentUser().getUsername();
        String sql = "SELECT orderNumber, orderCost FROM orders_" + username;

        if ("Placed".equals(status)) {
            sql += " WHERE orderStatus = 'Placed'";
        } else if ("Finalised".equals(status)) {
            sql += " WHERE orderStatus = 'Cancelled' OR orderStatus = 'Collected'";
        }

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int orderNumber = rs.getInt("orderNumber");
                double orderCost = rs.getDouble("orderCost");
                orders.add("Order #" + orderNumber + " - $" + orderCost);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayOrderDetails(String order) {
        String username = model.getCurrentUser().getUsername();
        int orderNumber = Integer.parseInt(order.split(" ")[1].substring(1));
        String sql = "SELECT * FROM orders_" + username + " WHERE orderNumber = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String details = "Order Number: " + rs.getInt("orderNumber") + "\n" +
                        " Order Status: " + rs.getString("orderStatus") + "\n" +
                        " Order Cost: $" + rs.getDouble("orderCost") + "\n" +
                        " Order Time: " + rs.getString("orderTime") + "\n" +
                        " Preparation Time: " + rs.getInt("prepTime") + " minutes" + "\n" +
                        " Collection Time: " + rs.getString("collectionTime");

                output.setText(details);

                String status = rs.getString("orderStatus");
                boolean isPlaced = "Placed".equals(status);
                cancel.setDisable(!isPlaced);
                pickup.setDisable(!isPlaced);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateOrderStatus(String status, String collectionTime) {
        if (selectedOrder == null) {
            JOptionPane.showMessageDialog(null, "No order selected");
            return;
        }

        String username = model.getCurrentUser().getUsername();
        int orderNumber = Integer.parseInt(selectedOrder.split(" ")[1].substring(1));
        String sql = "UPDATE orders_" + username + " SET orderStatus = ?, collectionTime = ? WHERE orderNumber = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, collectionTime);
            stmt.setInt(3, orderNumber);
            stmt.executeUpdate();
            loadOrders("Placed".equals(status) ? "Placed" : "Finalised");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handlePickupOrder() {
        if (selectedOrder == null) {
            JOptionPane.showMessageDialog(null, "No order selected");
            return;
        }

        String pickupTime = JOptionPane.showInputDialog("Enter pickup time (HH:mm):");
        if (pickupTime == null || !pickupTime.matches("\\d{2}:\\d{2}")) {
            JOptionPane.showMessageDialog(null, "Invalid time format. Please use HH:mm.");
            return;
        }

        String username = model.getCurrentUser().getUsername();
        int orderNumber = Integer.parseInt(selectedOrder.split(" ")[1].substring(1));
        String sql = "SELECT orderTime, prepTime FROM orders_" + username + " WHERE orderNumber = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String orderTime = rs.getString("orderTime");
                int prepTime = rs.getInt("prepTime");

                LocalTime orderLocalTime = LocalTime.parse(orderTime);
                LocalTime prepEndTime = orderLocalTime.plusMinutes(prepTime);
                LocalTime pickupLocalTime = LocalTime.parse(pickupTime);

                if (!pickupLocalTime.isBefore(prepEndTime)) {
                    updateOrderStatus("Collected", pickupTime);
                } else {
                    JOptionPane.showMessageDialog(null, "Pickup time must be after preparation time.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goback() {
        parentController.refreshData();
        stage.close();
        parentStage.show();
    }
}

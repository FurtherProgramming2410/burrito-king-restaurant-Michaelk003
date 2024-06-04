package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Model;
import dao.Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExportOrderController {
    private Model model;
    private Stage stage;
    private Stage parentStage;

    @FXML
    private ListView<HBox> orderListView;
    @FXML
    private TextField filePathField;
    @FXML
    private Button browseButton;
    @FXML
    private Button exportButton;

    private ObservableList<HBox> orders = FXCollections.observableArrayList();
    private List<OrderData> orderDataList = new ArrayList<>();

    public ExportOrderController(Stage parentStage, Model model) {
        this.stage = new Stage();
        this.model = model;
        this.parentStage = parentStage;
    }

    public void showStage(VBox pane) {
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Export Order");
        stage.show();
    }

    @FXML
    public void initialize() {
        // Load orders from the database
        loadOrders();
        orderListView.setItems(orders);

        //button to browse file destination
        browseButton.setOnAction(event -> browseFileDestination());
        //button to export orders
        exportButton.setOnAction(event -> exportOrders());
    }

    // Load orders from the database
    private void loadOrders() {
        String username = model.getCurrentUser().getUsername();
        String sql = "SELECT orderNumber, orderStatus, orderCost, orderTime, prepTime, collectionTime FROM orders_" + username;

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int orderNumber = rs.getInt("orderNumber");
                String orderStatus = rs.getString("orderStatus");
                double orderCost = rs.getDouble("orderCost");
                String orderTime = rs.getString("orderTime");
                int prepTime = rs.getInt("prepTime");
                String collectionTime = rs.getString("collectionTime");

                CheckBox checkBox = new CheckBox("Order #" + orderNumber + " - $" + orderCost);
                HBox hbox = new HBox(checkBox);
                orders.add(hbox);
                orderDataList.add(new OrderData(orderNumber, orderStatus, orderCost, orderTime, prepTime, collectionTime, checkBox));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Browse file destination
    private void browseFileDestination() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            filePathField.setText(file.getAbsolutePath());
        }
    }

    // Export selected orders to a CSV file
    private void exportOrders() {
        List<OrderData> selectedOrders = new ArrayList<>();
        for (OrderData orderData : orderDataList) {
            if (orderData.getCheckBox().isSelected()) {
                selectedOrders.add(orderData);
            }
        }
        // Check if any orders are selected
        if (selectedOrders.isEmpty()) {
            showAlert("No Orders Selected", "Please select orders to export.");
            return;
        }
        // Check if a file path is provided
        String filePath = filePathField.getText();
        if (filePath.isEmpty()) {
            showAlert("No File Path", "Please select a file destination.");
            return;
        }
        // Export orders to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Order Number,Order Status,Order Cost,Order Time,Preparation Time,Collection Time");
            writer.newLine();

            for (OrderData order : selectedOrders) {
                writer.write(order.toCSV());
                writer.newLine();
            }

            showAlert("Export Successful", "Orders have been exported successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Export Failed", "An error occurred while exporting orders.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goback() {
        stage.close();
        parentStage.show();
    }

    // Order data class

    public static class OrderData {
        private final int orderNumber;
        private final String orderStatus;
        private final double orderCost;
        private final String orderTime;
        private final int prepTime;
        private final String collectionTime;
        private final CheckBox checkBox;

        public OrderData(int orderNumber, String orderStatus, double orderCost, String orderTime, int prepTime, String collectionTime, CheckBox checkBox) {
            this.orderNumber = orderNumber;
            this.orderStatus = orderStatus;
            this.orderCost = orderCost;
            this.orderTime = orderTime;
            this.prepTime = prepTime;
            this.collectionTime = collectionTime;
            this.checkBox = checkBox;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        // Convert order data to CSV format

        public String toCSV() {
            return orderNumber + "," + orderStatus + "," + orderCost + "," + orderTime + "," + prepTime + "," + collectionTime;
        }
    }
}

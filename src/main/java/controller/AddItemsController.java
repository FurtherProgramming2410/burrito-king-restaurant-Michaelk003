package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.Order;

import javax.swing.*;
import java.io.IOException;

public class AddItemsController {
    private Model model;
    private Stage stage;
    private Stage parentStage;

    private HomeController parentController; // Reference to the parent controller

    @FXML
    private Button checkout;
    @FXML
    private Label burrito;
    @FXML
    private Label fries;
    @FXML
    private Label soda;
    @FXML
    private Label combo;

    @FXML
    private Button decrementburrito;
    @FXML
    private Button incrementburrito;
    @FXML
    private Button decrementfries;
    @FXML
    private Button incrementfries;
    @FXML
    private Button decrementsoda;
    @FXML
    private Button incrementsoda;
    @FXML
    private Button decrementcombo;
    @FXML
    private Button incrementcombo;

    @FXML
    private TextField countburrito;
    @FXML
    private TextField countfries;
    @FXML
    private TextField countsoda;
    @FXML
    private TextField countcombo;



    public AddItemsController(Stage parentstage, Model model,HomeController parentController) {
        this.stage = new Stage();
        this.model = model;
        this.parentStage = parentstage;
        this.parentController = parentController; // Assign the parent controller
    }

    public void showStage(VBox pane) {
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Add Items");
        stage.show();
    }

    @FXML
    public void initialize() {

        // Initialize the order object
        Order order = new Order(0, 0, 0, 0);
        // Load the prices of the items
        loadPrices();

        // buttons to increment and decrement the quantity of items
        decrementburrito.setOnAction(event -> updateCount(countburrito, -1));
        incrementburrito.setOnAction(event -> updateCount(countburrito, 1));
        decrementfries.setOnAction(event -> updateCount(countfries, -1));
        incrementfries.setOnAction(event -> updateCount(countfries, 1));
        decrementsoda.setOnAction(event -> updateCount(countsoda, -1));
        incrementsoda.setOnAction(event -> updateCount(countsoda, 1));
        decrementcombo.setOnAction(event -> updateCount(countcombo, -1));
        incrementcombo.setOnAction(event -> updateCount(countcombo, 1));

        // Validate the text fields
        setupTextFieldValidation(countburrito);
        setupTextFieldValidation(countfries);
        setupTextFieldValidation(countsoda);
        setupTextFieldValidation(countcombo);

        // Checkout button
        checkout.setOnAction(event -> {
            // Check if the order is empty
            if (countburrito.getText().equals("0") && countfries.getText().equals("0") && countsoda.getText().equals("0") && countcombo.getText().equals("0")) {

                JOptionPane.showMessageDialog(null, "Order is empty");

            }
            // If the order is not empty, set the quantities of items in the order class and proceed to the checkout
            else {
                order.setBurritoQuantity(Integer.parseInt(countburrito.getText()));
                order.setFriesQuantity(Integer.parseInt(countfries.getText()));
                order.setSodaQuantity(Integer.parseInt(countsoda.getText()));
                order.setComboQuantity(Integer.parseInt(countcombo.getText()));


                // go to checkout
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CheckoutView.fxml"));
                    CheckoutController checkoutController = new CheckoutController(stage, model);
                    loader.setController(checkoutController);
                    VBox pane = loader.load();
                    stage.hide();
                    checkoutController.showStage(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    // Load the prices of the items
    private void loadPrices() {
        burrito.setText("$" + Order.getBurritoPrice());
        fries.setText("$" + Order.getFriesPrice());
        soda.setText("$" + Order.getSodaPrice());

        if (model.getCurrentUser().getVip() == false) {
            combo.setText("$N/A");
            incrementcombo.setDisable(true);
            decrementcombo.setDisable(true);
            countcombo.setDisable(true);

        } else {
            combo.setText("$" + Order.getComboPrice());
            incrementcombo.setDisable(false);
            decrementcombo.setDisable(false);
            countcombo.setDisable(false);
        }

    }


    // Update the quantity of items
    private void updateCount(TextField textField, int delta) {
        try {
            int currentValue = Integer.parseInt(textField.getText());
            int newValue = Math.max(currentValue + delta, 0); // Ensure value is not negative
            textField.setText(String.valueOf(newValue));
        } catch (NumberFormatException e) {
            textField.setText("0");
        }
    }

    // Validate the text fields
    private void setupTextFieldValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    public void goback() {

        // Refresh data in the parent controller
        parentController.refreshData();

        stage.close();
        parentStage.show();
    }

}

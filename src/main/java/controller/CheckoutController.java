package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.Order;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CheckoutController {
    private Model model;
    private Stage stage;
    private Stage parentStage;

    @FXML
    private Label total;
    @FXML
    private Label burritoprice;
    @FXML
    private Label friesprice;
    @FXML
    private Label sodaprice;
    @FXML
    private Label comboprice;
    @FXML
    private Label burritocount;
    @FXML
    private Label friescount;
    @FXML
    private Label sodacount;
    @FXML
    private Label combocount;
    @FXML
    private Label burritototal;
    @FXML
    private Label friestotal;
    @FXML
    private Label sodatotal;
    @FXML
    private Label combototal;
    @FXML
    private RowConstraints burritoline;
    @FXML
    private RowConstraints friesline;
    @FXML
    private RowConstraints sodaline;
    @FXML
    private RowConstraints comboline;
    @FXML
    private GridPane gridpane;

    @FXML
    private TextField cardno;
    @FXML
    private TextField cvc;
    @FXML
    private TextField expire;
    @FXML
    private TextField time;
    @FXML
    private Label waittime;



    @FXML
    public void initialize() {
        setValues();
        PreparationSummary(Order.getFriesQuantity(),Order.getBurritoQuantity(),Order.getComboQuantity());

    }

    public void setValues() {
        burritoprice.setText("$"+ Order.getBurritoPrice());
        friesprice.setText("$"+ Order.getFriesPrice());
        sodaprice.setText("$"+ Order.getSodaPrice());
        comboprice.setText("$"+ Order.getComboPrice());

        burritocount.setText(String.valueOf(Order.getBurritoQuantity()));
        friescount.setText(String.valueOf(Order.getFriesQuantity()));
        sodacount.setText(String.valueOf(Order.getSodaQuantity()));
        combocount.setText(String.valueOf(Order.getComboQuantity()));

        burritototal.setText("$"+ String.valueOf(Order.getBurritoTotal()));
        friestotal.setText("$"+ String.valueOf(Order.getFriesTotal()));
        sodatotal.setText("$"+ String.valueOf(Order.getSodaTotal()));
        combototal.setText("$"+ String.valueOf(Order.getComboTotal()));

        total.setText("$"+ String.valueOf(Order.getTotal()));

        if (Order.getBurritoQuantity() == 0) {
            hideRow(0);
        }
        if (Order.getFriesQuantity() == 0) {
            hideRow(1);
        }
        if (Order.getSodaQuantity() == 0) {
            hideRow(2);
        }
        if (Order.getComboQuantity() == 0) {
            hideRow(3);
        }
    }

    private void hideRow(int rowIndex) {
        // Set the height of the row to 0
        gridpane.getRowConstraints().get(rowIndex).setMinHeight(0);
        gridpane.getRowConstraints().get(rowIndex).setPrefHeight(0);
        gridpane.getRowConstraints().get(rowIndex).setMaxHeight(0);

        // Make all nodes in the specified row invisible
        gridpane.getChildren().forEach(node -> {
            Integer row = GridPane.getRowIndex(node);
            if (row != null && row == rowIndex) {
                node.setVisible(false);
            }
        });
    }

    public CheckoutController(Stage parentstage, Model model) {
        this.stage = new Stage();
        this.model = model;
        this.parentStage = parentstage;
    }

    public void showStage(VBox pane) {
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Checkout");
        stage.show();
    }

    @FXML
    public void goback() {
        stage.close();
        parentStage.show();
    }

    public void PreparationSummary(int orderedFries, int orderedBurritos, int orderedCombo) {
        // Assuming the batches of fries are made up of 5 fries each
        int friesPerBatch = 5;
        int friesBatches = 0;
        int friesTime = 0;
        int burritoBatches = 0;
        int burritoTime = 0;

        //each ordered combo is 1 fries and 1 burrito, therfore we need to calculate the time for both so they are added to the total time for fries and burrito

        // Calculate time for fries
        if (orderedFries + orderedCombo > 0) {
            friesBatches = (int) Math.ceil((double) ((orderedFries + orderedCombo) / friesPerBatch));
            friesTime = friesBatches * 8; // 8 minutes per batch
        }

        // Calculate time for burritos
        if (orderedBurritos + orderedCombo > 0) {
            burritoBatches = (int) Math.ceil((orderedBurritos + orderedCombo) / 2.0); // 2 burritos per batch
            burritoTime = burritoBatches * 9; // 9 minutes per batch
        }

        // Print the expected wait time
        if (burritoTime > 0 || friesTime > 0) { // print wait time if any burritos are ordered or if there is a wait time for fries
            int waitTime = Math.max(burritoTime, friesTime);
           waittime.setText( waitTime + " minutes");
        }
    }


    @FXML
    public void validateAndPlaceOrder() {
        String cardNumber = cardno.getText();
        String cvcCode = cvc.getText();
        String expiryDate = expire.getText();
        String orderTime = time.getText();

        if (!isValidCardNumber(cardNumber)) {
            showAlert("Invalid Card Number", "The card number must be 16 digits.");
            return;
        }

        if (!isValidCVC(cvcCode)) {
            showAlert("Invalid CVC", "The CVC code must be 3 digits.");
            return;
        }

        if (!isValidExpiryDate(expiryDate)) {
            showAlert("Invalid Expiry Date", "The expiry date must be in MM/YY format and not be in the past.");
            return;
        }

        if (!isValidOrderTime(orderTime)) {
            showAlert("Invalid Order Time", "The order time must be in HH:mm format.");
            return;
        }

        showAlert("Order Placed", "Your order has been placed successfully!");
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}");
    }

    private boolean isValidCVC(String cvc) {
        return cvc.matches("\\d{3}");
    }

    private boolean isValidExpiryDate(String expiryDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            formatter.parse(expiryDate);
            LocalDate expiry = LocalDate.parse("01/" + expiryDate, DateTimeFormatter.ofPattern("dd/MM/yy"));
            return !expiry.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidOrderTime(String orderTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime.parse(orderTime, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

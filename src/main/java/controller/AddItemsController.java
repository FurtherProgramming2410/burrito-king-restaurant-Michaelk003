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

import java.io.IOException;

public class AddItemsController {
    private Model model;
    private Stage stage;
    private Stage parentStage;

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

    public AddItemsController(Stage parentstage, Model model) {
        this.stage = new Stage();
        this.model = model;
        this.parentStage = parentstage;
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

        Order order = new Order(0, 0, 0, 0);
        loadPrices();

        decrementburrito.setOnAction(event -> updateCount(countburrito, -1));
        incrementburrito.setOnAction(event -> updateCount(countburrito, 1));
        decrementfries.setOnAction(event -> updateCount(countfries, -1));
        incrementfries.setOnAction(event -> updateCount(countfries, 1));
        decrementsoda.setOnAction(event -> updateCount(countsoda, -1));
        incrementsoda.setOnAction(event -> updateCount(countsoda, 1));
        decrementcombo.setOnAction(event -> updateCount(countcombo, -1));
        incrementcombo.setOnAction(event -> updateCount(countcombo, 1));

        setupTextFieldValidation(countburrito);
        setupTextFieldValidation(countfries);
        setupTextFieldValidation(countsoda);
        setupTextFieldValidation(countcombo);

        checkout.setOnAction(event -> {
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
        });
    }

    private void loadPrices() {
        burrito.setText("$" + Order.getBurritoPrice());
        fries.setText("$" + Order.getFriesPrice());
        soda.setText("$" + Order.getSodaPrice());
        combo.setText("$" + Order.getComboPrice());
    }

    private void updateCount(TextField textField, int delta) {
        try {
            int currentValue = Integer.parseInt(textField.getText());
            int newValue = Math.max(currentValue + delta, 0); // Ensure value is not negative
            textField.setText(String.valueOf(newValue));
        } catch (NumberFormatException e) {
            textField.setText("0");
        }
    }

    private void setupTextFieldValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    public void goback() {

        stage.close();
        parentStage.show();
    }

}

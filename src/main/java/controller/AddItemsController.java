package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button checkout; // Corresponds to the Menu item button "View all orders" in HomeView.fxml

    @FXML
    private Label burrito;
    @FXML
    private Label fries;
    @FXML
    private Label soda;
    @FXML
    private Label combo;






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

        model.Order order = new Order(0,0,0,0);
        loadprices();

        checkout.setOnAction(event -> {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CheckoutView.fxml"));
                    CheckoutController CheckoutController = new CheckoutController(stage, model);
                    loader.setController(CheckoutController);

                    VBox pane = loader.load();

                    stage.hide();

                    // Use the profileController instance to call showStage
                    CheckoutController.showStage(pane);

                } catch (IOException e) {
                    // Handle the IOException properly
                    e.printStackTrace();
                }
        });

    }

    public void loadprices() {
        burrito.setText("$" + Order.getBurritoPrice());
        fries.setText("$" + Order.getFriesPrice());
        soda.setText("$" + Order.getSodaPrice());
        combo.setText("$" + Order.getComboPrice());

    }

    @FXML
    public void goback() {

        stage.close();
        parentStage.show();
        }

    }


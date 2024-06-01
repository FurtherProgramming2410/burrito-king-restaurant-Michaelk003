package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;

import java.io.IOException;

public class AddItemsController {
    private Model model;
    private Stage stage;
    private Stage parentStage;

    @FXML
    private Button checkout; // Corresponds to the Menu item button "View all orders" in HomeView.fxml




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

    @FXML
    public void goback() {

        stage.close();
        parentStage.show();
        }

    }


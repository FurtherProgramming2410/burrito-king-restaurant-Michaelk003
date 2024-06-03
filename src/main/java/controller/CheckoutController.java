package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.Order;

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
    public void initialize() {
        setValues();
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
}

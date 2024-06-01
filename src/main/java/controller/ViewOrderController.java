package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;

public class ViewOrderController {
    private Model model;
    private Stage stage;
    private Stage parentStage;





    public ViewOrderController(Stage parentstage, Model model) {
        this.stage = new Stage();
        this.model = model;
        this.parentStage = parentstage;
    }

    public void showStage(VBox pane) {
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("View Order");
        stage.show();
    }

    @FXML
    public void goback() {

        stage.close();
        parentStage.show();
        }

    }


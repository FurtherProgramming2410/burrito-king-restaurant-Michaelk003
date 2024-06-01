package controller;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class ProfileController {
    private Model model;
    private Stage stage;
    private Stage parentStage;





    public ProfileController(Stage parentstage, Model model) {
        this.stage = new Stage();
        this.model = model;
        this.parentStage = parentstage;
    }

    public void showStage(VBox pane) {
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("View Profile");
        stage.show();
    }

    @FXML
    public void goback() {

        stage.close();
        parentStage.show();
        }

    }


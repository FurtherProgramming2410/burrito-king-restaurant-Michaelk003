package controller;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class ProfileController {
    private Model model;
    private Stage stage;
    private Stage parentStage;


    @FXML
    private Label username;
    @FXML
    private Label password;
    @FXML
    private Label firstname;
    @FXML
    private Label lastname;
    @FXML
    private Label vip;


    @FXML
    public void initialize() {
        setData();
    }

    public void setData() {
        // Add your code here to set the data in the profile view
        username.setText( model.getCurrentUser().getUsername());
        password.setText( model.getCurrentUser().getPassword());
        firstname.setText( model.getCurrentUser().getFirstname());
        lastname.setText( model.getCurrentUser().getLastname());

        if (model.getCurrentUser().getVip() == false) {
            vip.setText("No");
        } else {
            vip.setText("Yes");
        }

    }



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


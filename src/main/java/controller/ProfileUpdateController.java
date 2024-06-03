package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import javafx.scene.control.TextInputDialog;
import model.User;
import java.util.Optional;


public class ProfileUpdateController {
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
    private TextField passwordtxt;
    @FXML
    private TextField firstnametxt;
    @FXML
    private TextField lastnametxt;
    @FXML
    private Button passwordbtn;
    @FXML
    private Button firstnamebtn;
    @FXML
    private Button lastnamebtn;
    @FXML
    private Button vipbtn;



    @FXML
    public void initialize() {
        placeData();

        passwordbtn.setOnAction(event -> {
            try{
                model.updatePassword(passwordtxt.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
               placeData();

        });

        firstnamebtn.setOnAction(event -> {
            try{
                model.updateFirstname(firstnametxt.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            placeData();
        });

        lastnamebtn.setOnAction(event -> {
            try{
                model.updateLastname(lastnametxt.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            placeData();
        });

        vipbtn.setOnAction(event -> {
           //message box appears asking for username input
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Email Address");
            dialog.setHeaderText("Please enter your Email Address:");
            dialog.setContentText("Input:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                // Handle the input

                try{
                    model.updateVip(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                placeData();

                String userInput = result.get();
                System.out.println("User input: " + userInput);
            } else {
                System.out.println("No input provided");
            }


        });


    }

    public void placeData() {
        // Add your code here to set the data in the profile view
        username.setText( model.getCurrentUser().getUsername());
        password.setText( model.getCurrentUser().getPassword());
        firstname.setText( model.getCurrentUser().getFirstname());
        lastname.setText( model.getCurrentUser().getLastname());

        if (model.getCurrentUser().getVip() == false) {
            vip.setText("No");
            vipbtn.setDisable(false);
        } else {
            vip.setText("Yes");
            vipbtn.setDisable(true);
        }

    }



    public ProfileUpdateController(Stage parentstage, Model model) {
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




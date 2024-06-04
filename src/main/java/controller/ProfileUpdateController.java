package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.User;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ProfileUpdateController {
    private Model model;
    private Stage stage;
    private Stage parentStage;

    private HomeController parentController; // Reference to the parent controller

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

        //buttons to update user information


        // Update the password
        passwordbtn.setOnAction(event -> {
            try{
                model.updatePassword(passwordtxt.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            placeData();

        });


        // Update the first name
        firstnamebtn.setOnAction(event -> {
            try{
                model.updateFirstname(firstnametxt.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            placeData();
        });

        // Update the last name
        lastnamebtn.setOnAction(event -> {
            try{
                model.updateLastname(lastnametxt.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            placeData();
        });

        // Update the VIP status
        vipbtn.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Email Address");
            dialog.setHeaderText("Please enter your Email Address:");
            dialog.setContentText("Input:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String userInput = result.get();
                if (isValidEmail(userInput)) {
                    try {
                        model.updateVip(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    placeData();

                    System.out.println("User input: " + userInput);
                } else {
                    showAlert("Invalid Email", "Please enter a valid email address.");
                }
            } else {
                System.out.println("No input provided");
            }
        });
    }

    // Place data in the labels from the database
    public void placeData() {
        username.setText(model.getCurrentUser().getUsername());
        password.setText(model.getCurrentUser().getPassword());
        firstname.setText(model.getCurrentUser().getFirstname());
        lastname.setText(model.getCurrentUser().getLastname());


        // If the user is not a VIP, enable the VIP button
        if (model.getCurrentUser().getVip() == false) {
            vip.setText("No");
            vipbtn.setDisable(false);
        } else {
            vip.setText("Yes");
            vipbtn.setDisable(true);
        }
    }

    public ProfileUpdateController(Stage parentstage, Model model, HomeController parentController) {
        this.stage = new Stage();
        this.model = model;
        this.parentStage = parentstage;
        this.parentController = parentController; // Assign the parent controller
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
        // Refresh data in the parent controller
        parentController.refreshData();
        stage.close();
        parentStage.show();
    }

    // Validate the email address
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

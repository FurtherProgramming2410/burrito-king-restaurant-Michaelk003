package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import java.io.IOException;
import model.Model;
import model.User;



public class HomeController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private MenuItem viewProfile; // Corresponds to the Menu item "viewProfile" in HomeView.fxml
	@FXML
	private MenuItem updateProfile; // // Corresponds to the Menu item "updateProfile" in HomeView.fxml
@FXML
	private Button vieworder; // Corresponds to the button "View all orders" in HomeView.fxml

	@FXML
	private Button exportorder; // Corresponds to the button "Export Orders" in HomeView.fxml
	@FXML
	private Button additems; // Corresponds to the Menu item button "View all orders" in HomeView.fxml
	@FXML
	private Button logout; // Corresponds to the Menu item button "View all orders" in HomeView.fxml

	@FXML
	private Label Greeting;

	
	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	// Add your code to complete the functionality of the program

	@FXML
	public void initialize() {

		setGreeting();

		viewProfile.setOnAction(event -> {
			try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));
			ProfileController profileController = new ProfileController(stage, model);
			loader.setController(profileController);

			VBox pane = loader.load();

			stage.hide();

			// Use the profileController instance to call showStage
			profileController.showStage(pane);

			} catch (IOException e) {
				// Handle the IOException properly
				e.printStackTrace();
			}

		});

		updateProfile.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileUpdate.fxml"));
				ProfileController updateController = new ProfileController(stage, model);
				loader.setController(updateController);

				VBox pane = loader.load();

				// Hide the current stage
				stage.hide();

				// Use the updateController instance to call showStage
				updateController.showStage(pane);

			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		vieworder.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderHistoryView.fxml"));
				ViewOrderController viewOrdersController = new ViewOrderController(stage, model);
				loader.setController(viewOrdersController);

				VBox pane = loader.load();

				// Hide the current stage
				stage.hide();

				// Use the viewOrdersController instance to call showStage
				viewOrdersController.showStage(pane);

			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		exportorder.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExportOrders.fxml"));
				ExportOrderController exportOrdersController = new ExportOrderController(stage, model);
				loader.setController(exportOrdersController);

				VBox pane = loader.load();

				// Hide the current stage
				stage.hide();

				// Use the exportOrdersController instance to call showStage
				exportOrdersController.showStage(pane);

			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		additems.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderView.fxml"));
				AddItemsController addItemsController = new AddItemsController(stage, model);
				loader.setController(addItemsController);

				VBox pane = loader.load();

				// Hide the current stage
				stage.hide();

				// Use the addItemsController instance to call showStage
				addItemsController.showStage(pane);

			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		logout.setOnAction(event -> {
			stage.hide();
			parentStage.show();
		});




	}


	private void setGreeting() {
		Greeting.setText("Welcome! " + model.getCurrentUser().getFirstname());
	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();
	}
}

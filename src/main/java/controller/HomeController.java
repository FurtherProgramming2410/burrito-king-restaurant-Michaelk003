package controller;

import dao.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.User;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



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

	@FXML
	private ListView<String> pendingorders;

	
	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	// Add your code to complete the functionality of the program

	@FXML
	public void initialize() {

		setGreeting();
		setPendingOrders();

		viewProfile.setOnAction(event -> {
			try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));
			ProfileController profileController = new ProfileController(stage, model);
			loader.setController(profileController);

			VBox pane = loader.load();

			stage.hide();

			// Use the profileController instance to call showStage

				profileController.showStage(pane);
				profileController.refreshAndPlaceData();

			} catch (IOException e) {
				// Handle the IOException properly
				e.printStackTrace();
			}

		});

		updateProfile.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileUpdate.fxml"));
				ProfileUpdateController updateController = new ProfileUpdateController(stage, model, this);
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
				ViewOrderController viewOrdersController = new ViewOrderController(stage, model, this);
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
				AddItemsController addItemsController = new AddItemsController(stage, model,this);
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
			//close connection to sql database
			Database.closeConnection();



			parentStage.show();
		});




	}


	private void setGreeting() {
		Greeting.setText("Welcome! " + model.getCurrentUser().getFirstname() + " " + model.getCurrentUser().getLastname());
	}

	private void setPendingOrders() {
		String username = model.getCurrentUser().getUsername();
		String sql = "SELECT orderNumber, orderCost FROM orders_" + username + " WHERE orderStatus = 'Placed'";

		List<String> orders = new ArrayList<>();

		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int orderNumber = rs.getInt("orderNumber");
				double orderCost = rs.getDouble("orderCost");
				orders.add("Order #" + orderNumber + " - $" + orderCost);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		ObservableList<String> observableOrders = FXCollections.observableArrayList(orders);
		pendingorders.setItems(observableOrders);
	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();
	}

	public void refreshData() {
		// Add code here to refresh the data displayed in the parent stage
		Greeting.setText("Welcome back, " + model.getCurrentUser().getFirstname() + " " + model.getCurrentUser().getLastname());
		setPendingOrders();
		// Refresh other components if necessary
	}
}

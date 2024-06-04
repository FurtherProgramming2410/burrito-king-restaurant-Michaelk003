package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";

	public UserDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
			 Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL,"
					+ "password VARCHAR(8) NOT NULL," + "PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
		} 
	}

	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setFirstname(rs.getString("firstname"));
					user.setLastname(rs.getString("lastname"));
					user.setVip(rs.getBoolean("vip"));
					user.setCredits(rs.getInt("credits"));
					return user;
				}
				return null;
			} 
		}
	}

	@Override
	public User createUser(String username, String password) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?)";

		String orderTableSql = "CREATE TABLE IF NOT EXISTS orders_" + username + " ("
				+ "orderNumber INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "orderStatus TEXT DEFAULT 'Placed', "
				+ "orderCost REAL, "
				+ "orderTime TEXT, "
				+ "prepTime INTEGER, "
				+ "collectionTime TEXT DEFAULT NULL"
				+ ")";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);
			 Statement orderTableStmt = connection.createStatement())
		{
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, "Firstname");
			stmt.setString(4, "Lastname");
			stmt.setBoolean(5, false);
			stmt.setInt(6, 0);


			stmt.executeUpdate();

			// Create orders table for the user
			orderTableStmt.execute(orderTableSql);

			return new User(username, password, "Firstname", "Lastname", false, 0);
		}
	}

	@Override
	public void createOrder(String username, double orderCost, String orderTime, int prepTime) throws SQLException {
		String sql = "INSERT INTO orders_" + username + " (orderCost, orderTime, prepTime) VALUES (?, ?, ?)";

		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setDouble(1, orderCost);
			stmt.setString(2, orderTime);
			stmt.setInt(3, prepTime);

			stmt.executeUpdate();
		}
	}

	@Override
	public User updatePassword(String username, String newPassword) throws SQLException {
		String sql = "UPDATE users SET password = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, newPassword);
			stmt.setString(2, username);
			stmt.executeUpdate();
		}
		return null;
	}

	@Override
	public User updateFirstname(String username, String newFirstname) throws SQLException {
		String sql = "UPDATE users SET firstname = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, newFirstname);
			stmt.setString(2, username);
			stmt.executeUpdate();
		}
		return null;
	}

	@Override
	public User updateLastname(String username, String newLastname) throws SQLException {
		String sql = "UPDATE users SET lastname = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, newLastname);
			stmt.setString(2, username);
			stmt.executeUpdate();
		}
		return null;
	}

	@Override
	public User updateVip(String username, Boolean newVip) throws SQLException {
		String sql = "UPDATE users SET vip = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setBoolean(1, newVip);
			stmt.setString(2, username);
			stmt.executeUpdate();
		}
		return null;
	}

}



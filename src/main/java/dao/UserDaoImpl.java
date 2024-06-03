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
					return user;
				}
				return null;
			} 
		}
	}

	@Override
	public User createUser(String username, String password) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ? )";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, "Firstname");
			stmt.setString(4, "Lastname");
			stmt.setBoolean(5, false);


			stmt.executeUpdate();
			return new User(username, password, "Firstname", "Lastname", false);
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

}



package model;

import java.sql.SQLException;

import dao.UserDao;
import dao.UserDaoImpl;

public class Model {
	private UserDao userDao;
	private User currentUser; 
	
	public Model() {
		userDao = new UserDaoImpl();
	}
	
	public void setup() throws SQLException {
		userDao.setup();
	}
	public UserDao getUserDao() {
		return userDao;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}

	public void updatePassword(String newPassword) throws SQLException {
		if (currentUser != null) {
			userDao.updatePassword(currentUser.getUsername(), newPassword);
			currentUser.setPassword(newPassword); // Update the password in the model
		}

	}

	public void updateFirstname(String newFirstname) throws SQLException {
		if (currentUser != null) {
			userDao.updateFirstname(currentUser.getUsername(), newFirstname);
			currentUser.setFirstname(newFirstname); // Update the password in the model
		}

	}

	public void updateLastname(String newLastname) throws SQLException {
		if (currentUser != null) {
			userDao.updateLastname(currentUser.getUsername(), newLastname);
			currentUser.setLastname(newLastname); // Update the password in the model
		}

	}

}


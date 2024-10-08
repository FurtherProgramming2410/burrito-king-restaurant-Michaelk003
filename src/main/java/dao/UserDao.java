package dao;

import java.sql.SQLException;

import model.User;

/**
 * A data access object (DAO) is a pattern that provides an abstract interface 
 * to a database or other persistence mechanism. 
 * the DAO maps application calls to the persistence layer and provides some specific data operations 
 * without exposing details of the database. 
 */
public interface UserDao {
	void setup() throws SQLException;
	User getUser(String username, String password) throws SQLException;
	User createUser(String username, String password) throws SQLException;
	public void createOrder(String username, double orderCost, String orderTime, int prepTime) throws SQLException;

	User updatePassword(String username, String newPassword) throws SQLException;

	User updateFirstname(String username, String newFirstname) throws SQLException;
	User updateLastname(String username, String newLastname) throws SQLException;

	User updateVip(String username, Boolean newVip) throws SQLException;

   User updateUserCredits(String username, int newCreditBalance) throws SQLException;
}

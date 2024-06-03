package model;

public class User {
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private Boolean vip;
	private Integer credits;

	public User() {
	}
	
	public User(String username, String password, String firstname, String lastname, Boolean vip, Integer credits) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.vip = vip;
		this.credits = credits;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public Boolean getVip() {
		return vip;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;


	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

}

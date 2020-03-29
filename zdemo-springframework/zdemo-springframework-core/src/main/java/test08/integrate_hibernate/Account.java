package test08.integrate_hibernate;

public class Account {
	private int id;
	private String username;
	private int balance;
	
	public Account() {
		super();
	}
	public Account(String username, int balance) {
		this.username = username;
		this.balance = balance;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", username=" + username + ", balance="
				+ balance + "]";
	}
}

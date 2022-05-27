package application;

public class Fan {
	private int fanId;
	private String firstName;
	private String lastName;
	private String username;
	private int paycard;
	private String email;
	
	public Fan() {
		super();
	}

	public Fan(int fanId, String firstName, String lastName, String username, int paycard, String email) {
		this.fanId = fanId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.paycard = paycard;
		this.email = email;
	}

	public int getFanId() {
		return fanId;
	}

	public void setFanId(int fanId) {
		this.fanId = fanId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPaycard() {
		return paycard;
	}

	public void setPaycard(int paycard) {
		this.paycard = paycard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

package application;

public class Side extends Seat {
	private String sideName;
	private int price;
	private int maxSeats;
	private int minSeats;

	public Side() {
		super();
	}

	public Side(String sideName, int price, int maxSeats, int minSeats) {
		this.sideName = sideName;
		this.price = price;
		this.maxSeats = maxSeats;
		this.minSeats = minSeats;

	}

	public Side(int sideId, String sideName, int price, int maxSeats, int minSeats) {
		super(sideId);
		this.sideName = sideName;
		this.price = price;
		this.maxSeats = maxSeats;
		this.minSeats = minSeats;
	}

	public String getSideName() {
		return sideName;
	}

	public void setSideName(String sideName) {
		this.sideName = sideName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMaxSeats() {
		return maxSeats;
	}

	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
	}

	public int getMinSeats() {
		return minSeats;
	}

	public void setMinSeats(int minSeats) {
		this.minSeats = minSeats;
	}

}

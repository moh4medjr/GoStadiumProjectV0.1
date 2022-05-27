package application;

import java.util.Date;

public class Booking extends Fan {
	private int seatNumber;
	private String side;
	private int price;
	private Date bookingDate;
	private int seatId;
	private int gameId;

	public Booking() {
		super();
	}

	public Booking(int seatNumber, String side, int price, Date bookingDate, int seatId, int gameId) {
		this.seatNumber = seatNumber;
		this.side = side;
		this.price = price;
		this.bookingDate = bookingDate;
		this.seatId = seatId;
		this.gameId = gameId;
	}

	public Booking(int fanId, String firstName, String lastName, String username, int paycard, String email,
			int seatNumber, String side, int price, Date bookingDate, int seatId, int gameId) {
		super(fanId, firstName, lastName, username, paycard, email);
		this.seatNumber = seatNumber;
		this.side = side;
		this.price = price;
		this.bookingDate = bookingDate;
		this.seatId = seatId;
		this.gameId = gameId;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

}

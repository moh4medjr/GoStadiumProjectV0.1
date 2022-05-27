package application;

import java.util.Date;

public class Seat extends Booking {
	private int sideId;

	public Seat() {
		super();

	}
	
	public Seat(int sideId) {
		this.sideId=sideId;
	}

	public Seat(int seatNumber, String side, int price, Date bookingDate, int seatId, int gameId, int sideId) {
		super(seatNumber, side, price, bookingDate, seatId, gameId);
		this.sideId = sideId;

	}

	public int getSideId() {
		return sideId;
	}

	public void setSideId(int sideId) {
		this.sideId = sideId;
	}

}

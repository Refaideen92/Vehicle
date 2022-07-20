package busbooking.bus.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class MyBookingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer BookingId;

	@Column
	@Type(type = "uuid-char")
	private UUID userId;

	@Column
	private String bookingStatus;

	@Column
	@Type(type = "uuid-char")
	private UUID vehicleId;

	@Column
	@Type(type = "uuid-char")
	private UUID paymentId;

	@Column
	@ElementCollection
	List<BookedUser> BookedData;

	public Integer getBookingId() {
		return BookingId;
	}

	public void setBookingId(Integer bookingId) {
		BookingId = bookingId;
	}

	public UUID getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(UUID paymentId) {
		this.paymentId = paymentId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public UUID getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(UUID vehicleId) {
		this.vehicleId = vehicleId;
	}

	public List<BookedUser> getBookedData() {
		return BookedData;
	}

	public void setBookedData(List<BookedUser> bookedData) {
		BookedData = bookedData;
	}

}

package busbooking.bus.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="Vehicle")
public class VehicleEntity {
	@Id
	@Type(type="uuid-char")
	@GenericGenerator(name="uuid2",strategy="uuid2")
	@GeneratedValue(generator="uuid2")
	@Column(name="vehicle_id")
	private UUID vehicleId;
	
	@Column
	private String vehicleName;
	
	@Column
	private String vehicleNumber;
	
	@Column
	private String ownerId;
	
	@Column
	private String vechicleSource;
	
	@Column
	private String vehicleDestination;
	
	@Column
	private String activeStatus;
	
	@Column
	private String startLocation;
	
	@Column(name="SEAT_DETAIL")
	@ElementCollection(fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private Map<Integer,BookedUser> seatDetail = new HashMap<Integer, BookedUser>();
	
	@Transient
	private int vehicleSeatCount;

	
	public UUID getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(UUID vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
	public String getVechicleSource() {
		return vechicleSource;
	}

	public void setVechicleSource(String vechicleSource) {
		this.vechicleSource = vechicleSource;
	}

	public String getVehicleDestination() {
		return vehicleDestination;
	}

	public void setVehicleDestination(String vehicleDestination) {
		this.vehicleDestination = vehicleDestination;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Map<Integer, BookedUser> getSeatDetail() {
		return seatDetail;
	}

	public void setSeatDetail(Map<Integer, BookedUser> seatDetail) {
		this.seatDetail = seatDetail;
	}

	public int getVehicleSeatCount() {
		return vehicleSeatCount;
	}

	public void setVehicleSeatCount(int vehicleSeatCount) {
		this.vehicleSeatCount = vehicleSeatCount;
	}
	
	
	
}

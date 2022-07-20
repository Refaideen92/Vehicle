package busbooking.bus.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import busbooking.bus.model.BookedUser;
import busbooking.bus.model.MyBookingEntity;
import busbooking.bus.repository.MyBookingRepository;

@Service
public class MyBookingService {

	@Autowired
	MyBookingRepository myBookingRepo;

	public void getSavedBooking(UUID userId, UUID vehicleId, List<BookedUser> bookedData) {
		MyBookingEntity bookedEntity = new MyBookingEntity();
		BookedUser bookedUser = bookedData.get(0);
		bookedEntity.setUserId(userId);
		bookedEntity.setVehicleId(vehicleId);
		bookedEntity.setBookingStatus("In-Progress");
		bookedEntity.setBookedData(bookedData);
		bookedEntity.setPaymentId(bookedUser.getPaymentId());
		myBookingRepo.save(bookedEntity);
	}

	public Map<String, List<MyBookingEntity>> getAllBooking(UUID userId) {
		List<MyBookingEntity> allMyBooking = myBookingRepo.findByUserId(userId);
		Map<String, List<MyBookingEntity>> myBookingMap = allMyBooking.stream().map(booking -> {
			return booking;
		}).collect(Collectors.groupingBy(MyBookingEntity::getBookingStatus));
		return myBookingMap;
	}

	public Optional<MyBookingEntity> findMyBooking(Integer BookingId) {
		return myBookingRepo.findById(BookingId);
	}

	public ResponseEntity<?> getUpdateBooking(MyBookingEntity bookedEntity) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(myBookingRepo.save(bookedEntity));
	}
}

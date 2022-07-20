package busbooking.bus.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import busbooking.bus.DTO.BookingDetails;
import busbooking.bus.DTO.SearchInfo;
import busbooking.bus.model.BookedUser;
import busbooking.bus.model.MyBookingEntity;
import busbooking.bus.model.VehicleEntity;
import busbooking.bus.service.BookingService;
import busbooking.bus.service.MyBookingService;

@RestController
@RequestMapping("/api/v1/book")
public class BookingController {

	@Autowired
	BookingService bookingService;

	@Autowired
	MyBookingService myBookingService;

	@GetMapping("/search")
	public List<VehicleEntity> getListVehicle(@RequestBody SearchInfo searchInfo) {
		return bookingService.getSearchVehicle(searchInfo);
	}

	@PostMapping("/booking")
	public ResponseEntity<?> getBooked(@RequestParam UUID vehicleId, @RequestBody List<BookingDetails> bookingDetail) {
		UUID userId = UUID.randomUUID();
		ResponseEntity<?> bookingVehicleResponse = bookingService.getBookingVehicle(userId, vehicleId, bookingDetail);

		if (bookingVehicleResponse.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			VehicleEntity vehicleEntity = (VehicleEntity) bookingVehicleResponse.getBody();
			Map<Integer, BookedUser> seatDetail = vehicleEntity.getSeatDetail();
			List<BookedUser> bookedUserList = new ArrayList<>();
			for (BookingDetails detail : bookingDetail) {
				BookedUser bookedUser = seatDetail.get(detail.getSeatNumber());
				bookedUserList.add(bookedUser);
			}
			myBookingService.getSavedBooking(userId, vehicleId, bookedUserList);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully Booked!!!");
		} else {
			return ResponseEntity.status(bookingVehicleResponse.getStatusCode()).body(bookingVehicleResponse.getBody());
		}

	}

	@DeleteMapping("/cancelbooking")
	public ResponseEntity<?> getCancelBooking(@RequestParam UUID vehicleId, @RequestParam Integer BookingId,
			@RequestParam UUID userId) {
		ResponseEntity<?> cancelVehicleResponse = bookingService.getCancelVehicle(vehicleId, BookingId, userId);
		if (cancelVehicleResponse.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			Optional<MyBookingEntity> updateMyBooking = myBookingService.findMyBooking(BookingId);
			if (updateMyBooking.isPresent()) {
				MyBookingEntity myBookingEntity = updateMyBooking.get();
				myBookingEntity.setBookingStatus("Cancelled");
				myBookingService.getUpdateBooking(myBookingEntity);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully Cancelled!!!");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking not found Cancelled!!!");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error Occured in repayment!!");
		}
	}
}

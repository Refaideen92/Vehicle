package busbooking.bus.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import busbooking.bus.model.BookedUser;
import busbooking.bus.model.MyBookingEntity;
import busbooking.bus.service.MyBookingService;

@RestController
@RequestMapping("/api/v1/mybooking")
public class MyBookingController {
	
	@Autowired
	MyBookingService bookingService;
	
	
	@PostMapping("/add")
	public void getSavedBooking(UUID userId, UUID vehicleId, List<BookedUser>bookedData) {
		bookingService.getSavedBooking(userId, vehicleId, bookedData);
	}
	
	@PostMapping("/fetchAll")
	public Map<String, List<MyBookingEntity>> getSavedBooking(@RequestParam UUID userId) {
		return bookingService.getAllBooking(userId);
	}
	

}

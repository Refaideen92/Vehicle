package busbooking.bus.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import busbooking.bus.DTO.BookingDetails;
import busbooking.bus.DTO.PaymentDetailDto;
import busbooking.bus.DTO.SearchInfo;
import busbooking.bus.model.BookedUser;
import busbooking.bus.model.MyBookingEntity;
import busbooking.bus.model.VehicleEntity;
import busbooking.bus.repository.MyBookingRepository;
import busbooking.bus.repository.VehicleRepository;

@Service
public class BookingService {

	@Autowired
	VehicleRepository vehicleRepo;

	@Autowired
	MyBookingRepository myBookingeRepo;

	@Autowired
	RestTemplate restTemplate;

	public List<VehicleEntity> getSearchVehicle(SearchInfo searchInfo) {
		return vehicleRepo.findByAllVechicle(searchInfo.getFromLocation(), searchInfo.getToLocation());
	}

	public ResponseEntity<?> getBookingVehicle(UUID userId, UUID vehicleId, List<BookingDetails> bookingDetail) {
		Optional<VehicleEntity> vehicleExists = checkVehicleExists(vehicleId);
		if (!vehicleExists.isEmpty()) {
			VehicleEntity vehicleEntity = vehicleExists.get();

			PaymentDetailDto paymentDetailDto = new PaymentDetailDto();
			paymentDetailDto.setUserId(userId);
			paymentDetailDto.setBookingId(1);
			paymentDetailDto.setPaymentMode("Netbanking");
			paymentDetailDto.setTotalAmount("100");

			ResponseEntity<PaymentDetailDto> paymentResponse = restTemplate.postForEntity(
					"http://localhost:8082/api/v1/payment/pay", paymentDetailDto, PaymentDetailDto.class);

			if (paymentResponse.getStatusCode().equals(HttpStatus.OK)) {
				UUID paymentId = paymentResponse.getBody().getPaymentId();
				Map<Integer, BookedUser> seatDetail = vehicleEntity.getSeatDetail();

				for (BookingDetails detail : bookingDetail) {
					BookedUser bookedUser = seatDetail.get(detail.getSeatNumber());
					bookedUser.setUserName(detail.getUserName());
					bookedUser.setGender(detail.getGender());
					bookedUser.setMailId(detail.getMailId());
					bookedUser.setContact(detail.getContact());
					bookedUser.setSeatStatus("Booked");
					bookedUser.setUserId(userId);
					bookedUser.setPaymentId(paymentId);
				}

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(vehicleRepo.save(vehicleEntity));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Payment Failure !!!");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vehicle does not Exist");
		}
	}

	public ResponseEntity<?> getCancelVehicle(UUID vehicleId, Integer BookingId, UUID userId) {
		Optional<VehicleEntity> vehicleExists = checkVehicleExists(vehicleId);
		Optional<MyBookingEntity> checkBookingExists = checkBookingExists(BookingId);
		if (!vehicleExists.isEmpty() && checkBookingExists.isPresent()) {
			VehicleEntity vehicleEntity = vehicleExists.get();
			MyBookingEntity myBookingEntity = checkBookingExists.get();

			ResponseEntity<PaymentDetailDto> repaymentResponse = restTemplate.postForEntity(
					"http://localhost:8082/api/v1/payment/refund", myBookingEntity.getPaymentId(),
					PaymentDetailDto.class);

			if (repaymentResponse.getStatusCode().equals(HttpStatus.ACCEPTED)) {
				Map<Integer, BookedUser> seatDetail = vehicleEntity.getSeatDetail();
				for (BookedUser detail : myBookingEntity.getBookedData()) {
					BookedUser bookedUser = seatDetail.get(detail.getSeatNumber());
					bookedUser.setUserName(null);
					bookedUser.setGender(null);
					bookedUser.setMailId(null);
					bookedUser.setContact(null);
					bookedUser.setUserId(null);
					bookedUser.setSeatStatus("Free");
					bookedUser.setPaymentId(null);
				}
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(vehicleRepo.save(vehicleEntity));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("RePayment Failure !!!");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vehicle does not Exist");
		}
	}

	public Optional<VehicleEntity> checkVehicleExists(UUID vehicleId) {
		Optional<VehicleEntity> vehicleData = vehicleRepo.findById(vehicleId);
		return vehicleData;
	}

	public Optional<MyBookingEntity> checkBookingExists(Integer bookingId) {
		Optional<MyBookingEntity> bookingData = myBookingeRepo.findById(bookingId);
		return bookingData;
	}

}

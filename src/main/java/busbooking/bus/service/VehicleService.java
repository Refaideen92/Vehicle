package busbooking.bus.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import busbooking.bus.model.BookedUser;
import busbooking.bus.model.VehicleEntity;
import busbooking.bus.repository.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	VehicleRepository vehicleRepo;

	public ResponseEntity<?> saveVehicle(VehicleEntity vehicle) {
		Optional<VehicleEntity> checkVehicle = checkVehicle(vehicle);
		if (checkVehicle.isEmpty()) {
			Map<Integer, BookedUser> seatDetail = new HashMap<>();

			for (int index = 0; index <= vehicle.getVehicleSeatCount(); index++) {
				BookedUser user = new BookedUser();
				user.setSeatNumber(index);
				user.setSeatStatus("Free");
				seatDetail.put(index, user);
			}
			vehicle.setSeatDetail(seatDetail);
			vehicle = vehicleRepo.save(vehicle);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(vehicle);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Vehicle Already Exists");
		}
	}

	public Optional<VehicleEntity> checkVehicle(VehicleEntity vehicle) {
		VehicleEntity vehicleDetail = vehicleRepo.findByVehicleNumber(vehicle.getVehicleNumber());
		return Optional.ofNullable(vehicleDetail);
	}

	public ResponseEntity<?> deleteVehicle(VehicleEntity vehicle) {
		Optional<VehicleEntity> checkVehicle = checkVehicle(vehicle);
		if (!checkVehicle.isEmpty()) {
			vehicleRepo.deleteById(checkVehicle.get().getVehicleId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully Deleted");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Vehicle does not Exists");
		}
	}

	public ResponseEntity<?> UpdateVehicle(VehicleEntity vehicle) {
		Optional<VehicleEntity> checkVehicle = checkVehicle(vehicle);
		if (!checkVehicle.isEmpty()) {
			VehicleEntity saveData = vehicleRepo.save(vehicle);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveData);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Vehicle does not Exists");
		}
	}
}

package busbooking.bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import busbooking.bus.model.VehicleEntity;
import busbooking.bus.service.VehicleService;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

	@Autowired
	VehicleService vehicleService;

	@PostMapping("/getRegisterVehicle")
	public ResponseEntity<?> registerVehicle(@RequestBody VehicleEntity vehicle) {
		return vehicleService.saveVehicle(vehicle);
	}

	@PutMapping("/getUpdateVehicle")
	public ResponseEntity<?> updateVehicle(@RequestBody VehicleEntity vehicle) {
		return vehicleService.UpdateVehicle(vehicle);
	}

	@DeleteMapping("/getDeleteVehicle")
	public ResponseEntity<?> deleteVehicle(@RequestBody VehicleEntity vehicle) {
		return vehicleService.deleteVehicle(vehicle);
	}

}

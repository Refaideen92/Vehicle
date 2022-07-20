package busbooking.bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import busbooking.bus.DTO.LoginDto;
import busbooking.bus.model.UserEntity;
import busbooking.bus.service.LoginService;

@RestController
@RequestMapping("/api/v1/user")
public class LoginController {

	@Autowired
	LoginService loginService;

	@GetMapping("/login")
	private ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto) {
		return ResponseEntity.ok().body("Emtpy");
	}

	@PostMapping("/getRegisterUser")
	private ResponseEntity<?> saveUser(@RequestBody UserEntity userData) {
		return loginService.registerUser(userData);
	}

	@PutMapping("/getModifyUser")
	private ResponseEntity<String> updateUser(@RequestBody UserEntity userData) {
		return loginService.updateUser(userData);
	}

	@DeleteMapping("/getDeleteUser")
	private ResponseEntity<String> deleteUser(@RequestBody UserEntity userData) {
		return loginService.deleteUser(userData);
	}
}

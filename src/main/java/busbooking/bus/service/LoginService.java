package busbooking.bus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import busbooking.bus.model.UserEntity;
import busbooking.bus.repository.LoginRepositoy;

@Service
public class LoginService {

	@Autowired
	private LoginRepositoy loginRepo;

	public ResponseEntity<?> registerUser(UserEntity userData) {
		if (checkUserExists(userData.getUsername())) {
			UserEntity save = loginRepo.save(userData);
			return ResponseEntity.status(HttpStatus.OK).body(save);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("User already exists");
		}
	}

	public Boolean checkUserExists(String userName) {
		UserEntity user = loginRepo.findByUsername(userName);
		return Optional.ofNullable(user).isEmpty();
	}

	public ResponseEntity<String> updateUser(UserEntity userData) {
		if (!checkUserExists(userData.getUsername())) {
			loginRepo.save(userData);
			return ResponseEntity.accepted().body("User has updated");
		} else {
			return ResponseEntity.badRequest().body("User does not exists");
		}
	}

	public ResponseEntity<String> deleteUser(UserEntity userData) {
		if (!checkUserExists(userData.getUsername())) {
			loginRepo.delete(userData);
			return ResponseEntity.accepted().body("User has deleted");
		} else {
			return ResponseEntity.badRequest().body("User does not exists");
		}
	}
}

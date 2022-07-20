package busbooking.bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import busbooking.bus.model.UserEntity;

public interface LoginRepositoy extends JpaRepository<UserEntity, String>{
	
	UserEntity findByUsername(String userName);

}

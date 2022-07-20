package busbooking.bus.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import busbooking.bus.model.MyBookingEntity;

public interface MyBookingRepository extends JpaRepository<MyBookingEntity, Integer>{

	List<MyBookingEntity> findByUserId(UUID userId);
}

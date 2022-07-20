package busbooking.bus.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import busbooking.bus.model.VehicleEntity;

public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID>{

	VehicleEntity findByVehicleNumber(String vehicleNumber);
	
	@Query(value="SELECT * FROM bus_service.vehicle where vehicle.vechicle_source = :source and vehicle.vehicle_destination = :destination",nativeQuery=true)
	List<VehicleEntity> findByAllVechicle(@Param("source")String Source , @Param("destination")String Destination);
}

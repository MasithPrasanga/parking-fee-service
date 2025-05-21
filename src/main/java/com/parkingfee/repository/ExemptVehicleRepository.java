package com.parkingfee.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.parkingfee.model.ExemptVehicle;

public interface ExemptVehicleRepository extends MongoRepository<ExemptVehicle, String> {
	boolean existsByVehicleType(String vehicleType);
}

package com.parkingfee.repository;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.parkingfee.model.Holiday;

public interface HolidayRepository extends MongoRepository<Holiday, String> {
	boolean existsByDate(LocalDate date);
}

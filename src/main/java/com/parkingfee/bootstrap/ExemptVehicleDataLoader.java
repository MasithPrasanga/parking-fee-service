package com.parkingfee.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.parkingfee.model.ExemptVehicle;
import com.parkingfee.model.VehicleType;
import com.parkingfee.repository.ExemptVehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExemptVehicleDataLoader implements CommandLineRunner {

	@Autowired
	private ExemptVehicleRepository exemptVehicleRepository;

	@Override
	public void run(String... args) throws Exception {
		long count = exemptVehicleRepository.count();
		if (count == 0) {
			List<ExemptVehicle> defaults = new ArrayList<ExemptVehicle>();
			defaults.add(new ExemptVehicle(null, VehicleType.EMERGENCY, 0));
			defaults.add(new ExemptVehicle(null, VehicleType.FOREIGN, 1));
			defaults.add(new ExemptVehicle(null, VehicleType.GOVERNMENT, 2));
			exemptVehicleRepository.saveAll(defaults);
			log.info("Loaded default exempt vehicles: {}", defaults);
		} else {
			log.info("{} exempt-vehicle records already exist, skipping seeding", count);
		}
	}

}

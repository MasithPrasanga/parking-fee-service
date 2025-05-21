package com.parkingfee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.parkingfee.service.ParkingFeeCalculatorService;

@SpringBootTest
class ParkingFeeServiceApplicationTests {

	@Autowired
	private ParkingFeeCalculatorService service;

}

package com.parkingfee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkingfee.controller.request.ParkingRequest;
import com.parkingfee.controller.response.ParkingResponse;
import com.parkingfee.service.ParkingFeeCalculatorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/parking")
@Slf4j
public class ParkingController {

	@Autowired
	private ParkingFeeCalculatorService parkingFeeCalculatorService;

	@PostMapping("/calculate")
	public ParkingResponse calculateFee(@RequestBody ParkingRequest parkingRequest) {

		// Entry log with MDC-driven requestId
		log.info("[Controller] POST /calculate – vehicleType={}, startTime={}, endTime={}",
				parkingRequest.getVehicleType(), parkingRequest.getStartTime(), parkingRequest.getEndTime());

		ParkingResponse response = parkingFeeCalculatorService.calculateParkingFee(parkingRequest);

		// Exit log
		log.info("[Controller] Response – duration={}min, totalFee={}, message={}",
				response.getParkingDurationInMinutes(), response.getTotalFee(), response.getMessage());

		return parkingFeeCalculatorService.calculateParkingFee(parkingRequest);
	}

}

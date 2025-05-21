package com.parkingfee.service;

import com.parkingfee.controller.request.ParkingRequest;
import com.parkingfee.controller.response.ParkingResponse;

public interface Transformer {
	
	public static ParkingResponse createParkingResponse(ParkingRequest parkingRequest, double totalFee, long parkingDurationInMinutes, String message) {
		return ParkingResponse.builder()
				.vehicalType(parkingRequest.getVehicleType())
				.startTime(parkingRequest.getStartTime())
				.endTime(parkingRequest.getEndTime())
				.parkingDurationInMinutes(parkingDurationInMinutes)
				.totalFee(totalFee)
				.message(message)
				.build();
	}

}

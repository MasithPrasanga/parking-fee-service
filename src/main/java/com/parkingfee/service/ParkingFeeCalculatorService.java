package com.parkingfee.service;

import java.time.LocalTime;

import com.parkingfee.controller.request.ParkingRequest;
import com.parkingfee.controller.response.ParkingResponse;

public interface ParkingFeeCalculatorService {

	public ParkingResponse calculateParkingFee(ParkingRequest parkingRequest);

	public double getHourlyFee(LocalTime time);
}

package com.parkingfee.model;

public class Motorbike implements Vehicle {
	@Override
	public String getVehicleType() {
		return VehicleType.MOTORBIKE.getVehicleType();
	}
}

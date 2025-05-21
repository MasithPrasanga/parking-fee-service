package com.parkingfee.model;

public class Van implements Vehicle {
	@Override
	public String getVehicleType() {
		return VehicleType.VAN.getVehicleType();
	}
}

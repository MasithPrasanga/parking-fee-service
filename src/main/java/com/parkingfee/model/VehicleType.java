package com.parkingfee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VehicleType {

	MOTORBIKE("Motorbike"), 
	VAN("Van"), 
	CAR("Car"), 
	LORRY("Lorry"), 
	TRUCK("Truck"), 
	EMERGENCY("Emergency"), 
	FOREIGN("Foreign"), 
	GOVERNMENT("Government");
	
	private String vehicleType;

}

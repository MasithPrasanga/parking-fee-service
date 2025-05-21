package com.parkingfee.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "exempt_vehicle")
public class ExemptVehicle {

	@Id
	private String id;

	@Indexed(unique = true)
	private VehicleType vehicleType;

	private Integer priorityCode;
}

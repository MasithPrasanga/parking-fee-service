package com.parkingfee.controller.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
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
public class ParkingRequest {

	@NotNull(message = "Vehicle type must not be null")
	private String vehicleType;

	@NotNull(message = "Start time must not be null")
	private LocalDateTime startTime;

	@NotNull(message = "End time must not be null")
	private LocalDateTime endTime;
}

package com.parkingfee.controller.response;

import java.time.LocalDateTime;

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
public class ParkingResponse {

	private String vehicalType;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private long parkingDurationInMinutes;
	private double totalFee;
	private String message;

}

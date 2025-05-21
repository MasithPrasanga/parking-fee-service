package com.parkingfee.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkingfee.controller.request.ParkingRequest;
import com.parkingfee.controller.response.ParkingResponse;
import com.parkingfee.exception.ExceptionName;
import com.parkingfee.exception.InvalidTimeRangeException;
import com.parkingfee.repository.ExemptVehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingFeeCalculatorServiceImpl implements ParkingFeeCalculatorService {

	@Autowired
	private HolidayClient holidayClient;

	@Autowired
	private ExemptVehicleRepository exemptVehicleRepository;

	public ParkingResponse calculateParkingFee(ParkingRequest parkingRequest) {

		// Entry log
		log.info("[Service] calculateParkingFee called – vehicleType={}, startTime={}, endTime={}",
				parkingRequest.getVehicleType(), parkingRequest.getStartTime(), parkingRequest.getEndTime());

		// Validate time range
		if (parkingRequest.getEndTime().isBefore(parkingRequest.getStartTime())) {
			log.error("[Service] calculateParkingFee validation failed – endTime < startTime");
			String description = "end time must be after start time";
			throw new InvalidTimeRangeException(ExceptionName.INVALID_TIME_RANGE_EXCEPTION.getExceptionName(),
					description);
		}

		// calculate the parking duration
		long parkingDurationInMinutes = Duration.between(parkingRequest.getStartTime(), parkingRequest.getEndTime())
				.toMinutes();
		log.debug("[Service] duration calculated = {} minutes", parkingDurationInMinutes);

		// Exempt vehicles pay nothing
		if (isFeeExemptVehicle(parkingRequest.getVehicleType())) {
			log.info("[Service] vehicleType={} is exempt → returning zero fee", parkingRequest.getVehicleType());
			return Transformer.createParkingResponse(parkingRequest, 0.0, parkingDurationInMinutes,
					"Vehicle is exempt from parking fees");
		}

		//
		LocalDate date = parkingRequest.getStartTime().toLocalDate();
		if (holidayClient.isPublicHoliday(date) || isWeekend(date)) {
			String msg = holidayClient.isPublicHoliday(date) ? "No charge on public holidays" : "No charge on weekends";
			log.info("[Service] date={} triggers no-fee policy → {}", date, msg);
			return Transformer.createParkingResponse(parkingRequest, 0.0, parkingDurationInMinutes, msg);
		}

		log.debug("[Service] calculating hourly fees across time bands");
		double totalFee = 0.0;
		LocalDateTime cursor = parkingRequest.getStartTime();
		LocalDateTime end = parkingRequest.getEndTime();

		while (cursor.isBefore(end)) {
			LocalDateTime nextBoundary = cursor.truncatedTo(ChronoUnit.HOURS).plusHours(1);
			if (nextBoundary.isAfter(end)) {
				nextBoundary = end;
			}

			long minutes = Duration.between(cursor, nextBoundary).toMinutes();
			double rate = getHourlyFee(cursor.toLocalTime());

			log.trace("[Service] segment {} → {} = {} mins @ Rs.{}", cursor.toLocalTime(), nextBoundary.toLocalTime(),
					minutes, rate);
			totalFee += rate * (minutes / 60.0);
			cursor = nextBoundary;
		}

		log.info("[Service] calculateParkingFee completed – duration={}min, totalFee={} ", parkingDurationInMinutes,
				totalFee);

		return Transformer.createParkingResponse(parkingRequest, totalFee, parkingDurationInMinutes,
				"Parking fee calculated successfully");
	}

	public double getHourlyFee(LocalTime time) {

		Objects.requireNonNull(time, "time must not be null");

		// 06:00–09:59 → Rs 100
		if (!time.isBefore(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(10, 0))) {
			return 100.0;
		}
		// 10:00–11:59 → Rs 80
		else if (!time.isBefore(LocalTime.of(10, 0)) && time.isBefore(LocalTime.of(12, 0))) {
			return 80.0;
		}
		// 12:00–13:59 → Rs 100
		else if (!time.isBefore(LocalTime.of(12, 0)) && time.isBefore(LocalTime.of(14, 0))) {
			return 100.0;
		}
		// 14:00–16:59 → Rs 50
		else if (!time.isBefore(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(17, 0))) {
			return 50.0;
		}
		// 17:00–21:59 → Rs 120
		else if (!time.isBefore(LocalTime.of(17, 0)) && time.isBefore(LocalTime.of(22, 0))) {
			return 120.0;
		}
		// 22:00–05:49 → Rs 0
		else {
			return 0.0;
		}
	}

	private boolean isWeekend(LocalDate date) {
		// 6 = Saturday and 7 = Sunday
		return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7;
	}

	private boolean isFeeExemptVehicle(String vehicleType) {
		return exemptVehicleRepository.existsByVehicleType(vehicleType);
	}
}
package com.parkingfee.service;

import java.time.LocalDate;
import java.util.List;

import com.parkingfee.model.Holiday;

public interface HolidayClient {

	List<Holiday> fetchHolidays(int year);

	boolean isPublicHoliday(LocalDate date);
}
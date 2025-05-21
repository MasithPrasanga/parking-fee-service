package com.parkingfee.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.parkingfee.model.Holiday;
import com.parkingfee.repository.HolidayRepository;
import com.parkingfee.service.HolidayClient;

@Service
@EnableScheduling
public class HolidayRefreshScheduler {

	@Autowired
	private HolidayClient holidayClient;

	@Autowired
	private HolidayRepository holidayRepository;

	/**
	 * Runs once when the Spring Boot application is fully started.
	 *
	 * ApplicationReadyEvent is published after:
	 * - the application context is loaded
	 * - all @Bean definitions are initialised
	 * - CommandLineRunner and ApplicationRunner beans have completed
	 *
	 * Refreshes the current-year holiday cache only after every other
	 * component (including the database) is ready.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void loadHolidaysAtStartup() {
		refreshCurrentYear();
	}

	/**
	 * Daily job — executes at **00:00 (midnight)** every day in the Asia/Colombo time-zone.
	 *
	 * Cron      →  sec   min  hour  dom  mon  dow
	 * pattern     0      0     0     *    *    *
	 *
	 * Meaning   →  at second 0, minute 0 of hour 0 (00:00), on every day-of-month,
	 *              every month, regardless of day-of-week.  The explicit
	 *              {@code zone = "Asia/Colombo"} keeps it at local midnight even if
	 *              the server’s default time zone differs.
	 */
	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Colombo")
	public void refreshHolidaysDaily() {
	    refreshCurrentYear();
	}


	private void refreshCurrentYear() {
		int year = LocalDate.now().getYear();
		List<Holiday> holidays = holidayClient.fetchHolidays(year);
		holidayRepository.deleteAll();
		holidayRepository.saveAll(holidays);
	}

}

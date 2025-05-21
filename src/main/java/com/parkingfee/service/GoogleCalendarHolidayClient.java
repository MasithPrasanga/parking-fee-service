package com.parkingfee.service;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkingfee.model.Holiday;
import com.parkingfee.repository.HolidayRepository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class GoogleCalendarHolidayClient implements HolidayClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HolidayRepository holidayRepository;

	@Value("${google.calendar.apiKey}")
	private String apiKey;

	@Value("${google.calendar.calendarId}")
	private String calendarId;
	
	@Override
	public List<Holiday> fetchHolidays(int year) {
		 URI uri = UriComponentsBuilder
		        .fromUriString("https://www.googleapis.com/calendar/v3/calendars/{calendarId}/events")
		        .queryParam("key", apiKey)
		        .queryParam("timeMin", year + "-01-01T00:00:00Z")
		        .queryParam("timeMax", year + "-12-31T23:59:59Z")
		        .queryParam("singleEvents", true)
		        .queryParam("orderBy", "startTime")
		        .buildAndExpand(calendarId)
		        .toUri();
		 
	    GoogleCalendarResponse response = restTemplate.getForObject(uri, GoogleCalendarResponse.class);

	    if (response == null || response.getItems() == null) {
	        return List.of();
	    }

	    return response.getItems().stream()
	        .map(evt -> Holiday.builder()
	            .date(LocalDate.parse(evt.getStart().getDate()))
	            .summary(evt.getSummary())
	            .build())
	        .collect(Collectors.toList());
	}


	@Override
	public boolean isPublicHoliday(LocalDate date) {
		return holidayRepository.existsByDate(date);
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class GoogleCalendarResponse {
		private List<GoogleEvent> items;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class GoogleEvent {
		private DateWrapper start;
		private String summary;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class DateWrapper {
		private String date;
	}

}

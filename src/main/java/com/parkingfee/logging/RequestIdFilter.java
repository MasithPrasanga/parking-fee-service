package com.parkingfee.logging;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestIdFilter extends OncePerRequestFilter {

	public static final String REQUEST_ID = "requestId";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		String requestId = UUID.randomUUID().toString();
		MDC.put(REQUEST_ID, requestId);
		try {
			filterChain.doFilter(request, response);
		} finally {
			MDC.remove(REQUEST_ID);
		}
	}
}

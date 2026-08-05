package com.workshop.petcareops.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class DashboardIncidentExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DashboardIncidentExceptionHandler.class);

    @ExceptionHandler(CannotCreateTransactionException.class)
    ResponseEntity<IncidentErrorResponse> handleConnectionAcquisitionFailure(
            CannotCreateTransactionException exception
    ) {
        String requestId = MDC.get(RequestCorrelationFilter.REQUEST_ID_MDC_KEY);
        log.warn(
                "event=dashboard_connection_acquisition_failed requestId={} exceptionType={}",
                requestId,
                exception.getClass().getSimpleName()
        );

        IncidentErrorResponse response = new IncidentErrorResponse(
                Instant.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Service Unavailable",
                "Dashboard data is temporarily unavailable.",
                requestId
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    record IncidentErrorResponse(
            Instant timestamp,
            int status,
            String error,
            String message,
            String requestId
    ) {
    }
}

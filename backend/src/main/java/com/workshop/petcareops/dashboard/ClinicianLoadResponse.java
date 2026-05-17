package com.workshop.petcareops.dashboard;

public record ClinicianLoadResponse(
        String clinicianName,
        String specialty,
        int appointmentsToday,
        int openFollowUps
) {
}
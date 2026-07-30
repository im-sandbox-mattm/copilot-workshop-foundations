package com.workshop.petcareops.dashboard;

public record ClinicianEscalationLoad(
        String clinicianName,
        int escalationCount
) {
}

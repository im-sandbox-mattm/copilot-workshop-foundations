package com.workshop.petcareops.dashboard;

import java.util.List;

public record DashboardOverviewResponse(
        String clinicName,
        String locationLabel,
        String shiftSummary,
        List<AppointmentSummaryResponse> appointments,
        List<ClinicianLoadResponse> clinicianLoad
) {
}
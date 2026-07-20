package com.workshop.petcareops.dashboard;

import java.util.List;

/**
 * Aggregated view of a clinic's current shift, returned by the dashboard
 * overview endpoint.
 *
 * @param clinicName                     name of the clinic
 * @param locationLabel                  human-readable label for the clinic's location
 * @param shiftSummary                   short description of the current shift
 * @param appointments                   appointments scheduled for the shift
 * @param clinicianLoad                  per-clinician workload summaries for the shift
 * @param appointmentsRequiringFollowUp  count of appointments that need follow-up action
 */
public record DashboardOverviewResponse(
        String clinicName,
        String locationLabel,
        String shiftSummary,
        List<AppointmentSummaryResponse> appointments,
        List<ClinicianLoadResponse> clinicianLoad,
        long appointmentsRequiringFollowUp
) {
}
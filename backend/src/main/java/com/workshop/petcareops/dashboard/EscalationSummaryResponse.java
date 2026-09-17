package com.workshop.petcareops.dashboard;

import java.util.List;

public record EscalationSummaryResponse(
        int totalEscalations,
        List<String> escalatedAppointmentIds,
        List<ClinicianEscalationLoad> clinicianEscalationLoad,
        String busiestClinician
) {
}

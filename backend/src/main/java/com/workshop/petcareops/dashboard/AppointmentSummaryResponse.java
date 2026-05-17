package com.workshop.petcareops.dashboard;

public record AppointmentSummaryResponse(
        long appointmentId,
        String startsAt,
        String status,
        String reason,
        CustomerSummaryResponse customer,
        PetSummaryResponse pet,
        ClinicianSummaryResponse clinician,
        String treatmentSummary,
        boolean followUpRequired
) {
}
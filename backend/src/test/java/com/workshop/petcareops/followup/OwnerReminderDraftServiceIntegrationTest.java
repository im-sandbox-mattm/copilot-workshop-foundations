package com.workshop.petcareops.followup;

import com.workshop.petcareops.dashboard.AppointmentSummaryResponse;
import com.workshop.petcareops.dashboard.ClinicianSummaryResponse;
import com.workshop.petcareops.dashboard.CustomerSummaryResponse;
import com.workshop.petcareops.dashboard.PetSummaryResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OwnerReminderDraftServiceIntegrationTest {

    private final OwnerReminderDraftService ownerReminderDraftService = new OwnerReminderDraftService();

    @Test
    void draftsTheFullClinicQueueCorrectly_whenAppointmentsSpanUrgentRoutineAndMonitorCases() {
        List<AppointmentSummaryResponse> clinicQueue = List.of(
                new AppointmentSummaryResponse(
                        4001L,
                        "2026-07-23T08:00:00",
                        "READY_FOR_DISCHARGE",
                        "Recheck",
                        new CustomerSummaryResponse("Priya Nair", "Email"),
                        new PetSummaryResponse("Nova", "Cat", "Siamese", null),
                        new ClinicianSummaryResponse("Dr. Ana Reyes", "General Practice"),
                        "Vitals stable.",
                        true
                ),
                new AppointmentSummaryResponse(
                        4002L,
                        "2026-07-23T08:15:00",
                        "SCHEDULED",
                        "Recheck",
                        new CustomerSummaryResponse("Dana Ford", "SMS"),
                        new PetSummaryResponse("Peanut", "Dog", "Corgi", "Medication reminder"),
                        new ClinicianSummaryResponse("Dr. Priya Shah", "General Practice"),
                        null,
                        true
                ),
                new AppointmentSummaryResponse(
                        4003L,
                        "2026-07-23T08:30:00",
                        "COMPLETED",
                        "Wellness exam",
                        new CustomerSummaryResponse("Sara Kim", "Email"),
                        new PetSummaryResponse("Sage", "Cat", "Sphynx", null),
                        new ClinicianSummaryResponse("Dr. Priya Shah", "General Practice"),
                        null,
                        false
                )
        );

        List<OwnerReminderDraft> actualDrafts = clinicQueue.stream()
                .map(ownerReminderDraftService::buildDraft)
                .toList();

        List<OwnerReminderDraft> expectedDrafts = List.of(
                new OwnerReminderDraft(
                        4001L,
                        "Urgent",
                        "Email",
                        "Action needed today for Nova update",
                        "Important update for Nova: please contact the clinic today. "
                                + "The care team should confirm the next-step guidance first. "
                                + "Latest note: Vitals stable.",
                        false
                ),
                new OwnerReminderDraft(
                        4002L,
                        "Routine follow-up",
                        "SMS",
                        "Visit update for Peanut",
                        "Text the owner a short follow-up reminder for Peanut. "
                                + "Remind the owner to confirm the medication schedule. "
                                + "Check the dashboard before sending the final message.",
                        false
                ),
                new OwnerReminderDraft(
                        4003L,
                        "Monitor",
                        "Email",
                        "Visit update for Sage",
                        "No immediate follow-up is required for Sage, but keep the visit visible in the queue. "
                                + "Check the dashboard before sending the final message.",
                        false
                )
        );

        assertEquals(expectedDrafts, actualDrafts);
    }
}

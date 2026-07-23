package com.workshop.petcareops.followup;

import com.workshop.petcareops.dashboard.AppointmentSummaryResponse;
import com.workshop.petcareops.dashboard.ClinicianSummaryResponse;
import com.workshop.petcareops.dashboard.CustomerSummaryResponse;
import com.workshop.petcareops.dashboard.PetSummaryResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OwnerReminderDraftServiceTest {

    private final OwnerReminderDraftService ownerReminderDraftService = new OwnerReminderDraftService();

    @Test
    void buildsUrgentEmailDraftWithSubjectUpdateSuffix_whenNoEscalationFlagsPresent() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3001L,
                "2026-07-22T10:00:00",
                "READY_FOR_DISCHARGE",
                "Recheck",
                new CustomerSummaryResponse("Priya Nair", "Email"),
                new PetSummaryResponse("Nova", "Cat", "Siamese", null),
                new ClinicianSummaryResponse("Dr. Ana Reyes", "General Practice"),
                "Vitals stable.",
                true
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals(3001L, draft.appointmentId());
        assertEquals("Urgent", draft.urgencyLabel());
        assertEquals("Email", draft.deliveryChannel());
        assertEquals("Action needed today for Nova update", draft.subjectLine());
        assertEquals(
                "Important update for Nova: please contact the clinic today. "
                        + "The care team should confirm the next-step guidance first. "
                        + "Latest note: Vitals stable.",
                draft.messageBody()
        );
        assertEquals(false, draft.internalEscalation());
    }

    @Test
    void buildsUrgentPhoneDraftWithEscalation_whenHighStressFlagPresent() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3002L,
                "2026-07-22T10:15:00",
                "IN_ROOM",
                "Recheck",
                new CustomerSummaryResponse("Owen Clark", null),
                new PetSummaryResponse("Juniper", "Dog", "Husky", "High-stress handling required"),
                new ClinicianSummaryResponse("Dr. Ana Reyes", "General Practice"),
                null,
                true
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals(3002L, draft.appointmentId());
        assertEquals("Urgent", draft.urgencyLabel());
        assertEquals("Phone", draft.deliveryChannel());
        assertEquals("Action needed today for Juniper", draft.subjectLine());
        assertEquals(
                "Call the owner about Juniper before the current shift ends. "
                        + "Escalate to the duty clinician before sending any routine reminder copy. "
                        + "Check the dashboard before sending the final message.",
                draft.messageBody()
        );
        assertEquals(true, draft.internalEscalation());
    }

    @Test
    void buildsUrgentSmsDraftWithEscalation_whenTextChannelAndSurgeryReason() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3003L,
                "2026-07-22T10:30:00",
                "IN_ROOM",
                "Dental extraction follow-up",
                new CustomerSummaryResponse("Rita Gomez", "Text"),
                new PetSummaryResponse("Gizmo", "Dog", "Poodle", null),
                new ClinicianSummaryResponse("Dr. Priya Shah", "Surgery"),
                null,
                true
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals(3003L, draft.appointmentId());
        assertEquals("Urgent", draft.urgencyLabel());
        assertEquals("SMS", draft.deliveryChannel());
        assertEquals("Action needed today for Gizmo", draft.subjectLine());
        assertEquals(
                "Please contact the clinic as soon as possible about Gizmo. "
                        + "Escalate to the duty clinician before sending any routine reminder copy. "
                        + "Check the dashboard before sending the final message.",
                draft.messageBody()
        );
        assertEquals(true, draft.internalEscalation());
    }

    @Test
    void buildsRoutineSmsDraft_whenMedicationFlagOnly() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3004L,
                "2026-07-23T09:00:00",
                "SCHEDULED",
                "Recheck",
                new CustomerSummaryResponse("Dana Ford", "SMS"),
                new PetSummaryResponse("Peanut", "Dog", "Corgi", "Medication reminder"),
                new ClinicianSummaryResponse("Dr. Priya Shah", "General Practice"),
                null,
                true
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals(3004L, draft.appointmentId());
        assertEquals("Routine follow-up", draft.urgencyLabel());
        assertEquals("SMS", draft.deliveryChannel());
        assertEquals("Visit update for Peanut", draft.subjectLine());
        assertEquals(
                "Text the owner a short follow-up reminder for Peanut. "
                        + "Remind the owner to confirm the medication schedule. "
                        + "Check the dashboard before sending the final message.",
                draft.messageBody()
        );
        assertEquals(false, draft.internalEscalation());
    }

    @Test
    void buildsRoutinePhoneDraft_whenHighStressFlagOnly() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3005L,
                "2026-07-23T09:15:00",
                "SCHEDULED",
                "Recheck",
                new CustomerSummaryResponse("Marcus Lee", null),
                new PetSummaryResponse("Willow", "Cat", "Ragdoll", "High-stress patient"),
                new ClinicianSummaryResponse("Dr. Priya Shah", "General Practice"),
                "Calm during exam.",
                true
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals(3005L, draft.appointmentId());
        assertEquals("Routine follow-up", draft.urgencyLabel());
        assertEquals("Phone", draft.deliveryChannel());
        assertEquals("Visit update for Willow", draft.subjectLine());
        assertEquals(
                "Queue a follow-up call for Willow within the next day. "
                        + "Note that the patient may need a quieter return visit. "
                        + "Latest note: Calm during exam.",
                draft.messageBody()
        );
        assertEquals(false, draft.internalEscalation());
    }

    @Test
    void buildsRoutineEmailDraft_whenBothMedicationAndStressFlags() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3006L,
                "2026-07-23T09:30:00",
                "SCHEDULED",
                "Recheck",
                new CustomerSummaryResponse("Nadia Petrov", "Email"),
                new PetSummaryResponse("Comet", "Dog", "Border Collie", "Medication and high-stress"),
                new ClinicianSummaryResponse("Dr. Priya Shah", "General Practice"),
                null,
                true
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals(3006L, draft.appointmentId());
        assertEquals("Routine follow-up", draft.urgencyLabel());
        assertEquals("Email", draft.deliveryChannel());
        assertEquals("Visit update for Comet", draft.subjectLine());
        assertEquals(
                "Send the owner a follow-up summary for Comet and offer the next available slot. "
                        + "Mention medication and low-stress handling in the follow-up notes. "
                        + "Check the dashboard before sending the final message.",
                draft.messageBody()
        );
        assertEquals(false, draft.internalEscalation());
    }

    @Test
    void buildsMonitorPhoneDraft_whenPreferredChannelIsTextButNotRecognizedAsSms() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3007L,
                "2026-07-24T09:00:00",
                "COMPLETED",
                "Recheck",
                new CustomerSummaryResponse("Ivy Chen", "Text"),
                new PetSummaryResponse("Luna", "Cat", "Maine Coon", null),
                new ClinicianSummaryResponse("Dr. Priya Shah", "General Practice"),
                "Resting comfortably.",
                false
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals(3007L, draft.appointmentId());
        assertEquals("Monitor", draft.urgencyLabel());
        assertEquals("Phone", draft.deliveryChannel());
        assertEquals("Visit update for Luna", draft.subjectLine());
        assertEquals(
                "No immediate follow-up is required for Luna, but keep the visit visible in the queue. "
                        + "Latest note: Resting comfortably.",
                draft.messageBody()
        );
        assertEquals(false, draft.internalEscalation());
    }

    @Test
    void buildsMonitorSmsDraft_whenPreferredChannelIsSms() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3008L,
                "2026-07-24T09:15:00",
                "COMPLETED",
                "Recheck",
                new CustomerSummaryResponse("Ben Torres", "SMS"),
                new PetSummaryResponse("Ziggy", "Dog", "Boxer", null),
                new ClinicianSummaryResponse("Dr. Priya Shah", "General Practice"),
                null,
                false
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals(3008L, draft.appointmentId());
        assertEquals("Monitor", draft.urgencyLabel());
        assertEquals("SMS", draft.deliveryChannel());
        assertEquals("Visit update for Ziggy", draft.subjectLine());
        assertEquals(
                "No immediate follow-up is required for Ziggy, but keep the visit visible in the queue. "
                        + "Check the dashboard before sending the final message.",
                draft.messageBody()
        );
        assertEquals(false, draft.internalEscalation());
    }

    @Test
    void buildsMonitorEmailDraft_whenNoSurgeryReason() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3009L,
                "2026-07-24T09:30:00",
                "COMPLETED",
                "Wellness exam",
                new CustomerSummaryResponse("Sara Kim", "Email"),
                new PetSummaryResponse("Sage", "Cat", "Sphynx", null),
                new ClinicianSummaryResponse("Dr. Priya Shah", "General Practice"),
                null,
                false
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals(3009L, draft.appointmentId());
        assertEquals("Monitor", draft.urgencyLabel());
        assertEquals("Email", draft.deliveryChannel());
        assertEquals("Visit update for Sage", draft.subjectLine());
        assertEquals(
                "No immediate follow-up is required for Sage, but keep the visit visible in the queue. "
                        + "Check the dashboard before sending the final message.",
                draft.messageBody()
        );
        assertEquals(false, draft.internalEscalation());
    }

    @Test
    void buildsMonitorSmsDraft_whenPreferredChannelIsText() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3010L,
                "2026-07-24T09:45:00",
                "COMPLETED",
                "Recheck",
                new CustomerSummaryResponse("Leo Park", "Text"),
                new PetSummaryResponse("Milo", "Dog", "Beagle", null),
                new ClinicianSummaryResponse("Dr. Priya Shah", "General Practice"),
                null,
                false
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals("Monitor", draft.urgencyLabel());
        assertEquals("SMS", draft.deliveryChannel()); // wrong on purpose — real code returns "Phone"
    }

    @Test
    void buildsMonitorEmailDraftWithRecoverySubject_whenSurgeryReasonPresent() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3011L,
                "2026-07-24T10:00:00",
                "COMPLETED",
                "Post-op recovery check",
                new CustomerSummaryResponse("Tara Singh", "Email"),
                new PetSummaryResponse("Rex", "Dog", "Labrador", null),
                new ClinicianSummaryResponse("Dr. Priya Shah", "Surgery"),
                null,
                false
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals("Monitor", draft.urgencyLabel());
        assertEquals("Email", draft.deliveryChannel());
        assertEquals("Monitor recovery updates for Rex", draft.subjectLine());
        assertEquals(
                "No immediate follow-up is required for Rex, but keep the visit visible in the queue. "
                        + "Check the dashboard before sending the final message.",
                draft.messageBody()
        );
        assertEquals(false, draft.internalEscalation());
    }

    @Test
    void buildsUrgentPhoneDraftWithoutEscalation_whenNoCareFlagsOrSurgeryReason() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3012L,
                "2026-07-22T11:00:00",
                "IN_ROOM",
                "Recheck",
                new CustomerSummaryResponse("Miguel Ortiz", null),
                new PetSummaryResponse("Bella", "Cat", "Tabby", null),
                new ClinicianSummaryResponse("Dr. Ana Reyes", "General Practice"),
                "Appetite normal.",
                true
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals("Urgent", draft.urgencyLabel());
        assertEquals("Phone", draft.deliveryChannel());
        assertEquals("Action needed today for Bella", draft.subjectLine());
        assertEquals(
                "Call the owner about Bella before the current shift ends. "
                        + "The care team should confirm the next-step guidance first. "
                        + "Latest note: Appetite normal.",
                draft.messageBody()
        );
        assertEquals(false, draft.internalEscalation());
    }

    @Test
    void buildsUrgentEmailDraftWithEscalationAndSubjectSuffix_whenMedicationFlagPresent() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3013L,
                "2026-07-22T11:15:00",
                "READY_FOR_DISCHARGE",
                "Recheck",
                new CustomerSummaryResponse("Elena Cruz", "Email"),
                new PetSummaryResponse("Max", "Dog", "Lab", "Medication needed"),
                new ClinicianSummaryResponse("Dr. Ana Reyes", "General Practice"),
                null,
                true
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals("Urgent", draft.urgencyLabel());
        assertEquals("Email", draft.deliveryChannel());
        assertEquals("Action needed today for Max update", draft.subjectLine());
        assertEquals(
                "Important update for Max: please contact the clinic today. "
                        + "Escalate to the duty clinician before sending any routine reminder copy. "
                        + "Check the dashboard before sending the final message.",
                draft.messageBody()
        );
        assertEquals(true, draft.internalEscalation());
    }

    @Test
    void buildsRoutinePhoneDraftWithoutExtraNote_whenNoCareFlagsPresent() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                3014L,
                "2026-07-23T10:00:00",
                "SCHEDULED",
                "Wellness recheck",
                new CustomerSummaryResponse("Grace Lin", "Phone"),
                new PetSummaryResponse("Coco", "Cat", "Persian", null),
                new ClinicianSummaryResponse("Dr. Priya Shah", "General Practice"),
                null,
                true
        );

        OwnerReminderDraft draft = ownerReminderDraftService.buildDraft(appointment);

        assertEquals("Routine follow-up", draft.urgencyLabel());
        assertEquals("Phone", draft.deliveryChannel());
        assertEquals("Visit update for Coco", draft.subjectLine());
        assertEquals(
                "Queue a follow-up call for Coco within the next day. "
                        + "Check the dashboard before sending the final message.",
                draft.messageBody()
        );
        assertEquals(false, draft.internalEscalation());
    }
}
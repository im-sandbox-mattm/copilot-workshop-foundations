package com.workshop.petcareops.dashboard;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EscalationBoardServiceTest {

    private final EscalationBoardService escalationBoardService = new EscalationBoardService();

    @Test
    void process_aggregatesSameDayEscalationsByClinician() {
        DashboardOverviewResponse overview = overview(
                appointment(1001L, "IN_ROOM", true, "Dr. Shah"),
                appointment(1002L, "READY_FOR_DISCHARGE", true, "Dr. Shah"),
                appointment(1003L, "IN_ROOM", true, "Dr. Kim"),
                appointment(1004L, "SCHEDULED", true, "Dr. Kim"),
                appointment(1005L, "IN_ROOM", false, "Dr. Kim")
        );

        EscalationSummaryResponse response = escalationBoardService.process(overview);

        assertEquals(3, response.totalEscalations());
        assertEquals(List.of("1001", "1002", "1003"), response.escalatedAppointmentIds());
        assertEquals(List.of(
                new ClinicianEscalationLoad("Dr. Shah", 2),
                new ClinicianEscalationLoad("Dr. Kim", 1)
        ), response.clinicianEscalationLoad());
        assertEquals("Dr. Shah", response.busiestClinician());
    }

    @Test
    void process_doesNotAccumulateCountsAcrossInvocations() {
        DashboardOverviewResponse overview = overview(
                appointment(1001L, "IN_ROOM", true, "Dr. Shah")
        );

        EscalationSummaryResponse firstResponse = escalationBoardService.process(overview);
        EscalationSummaryResponse secondResponse = escalationBoardService.process(overview);

        assertEquals(1, firstResponse.totalEscalations());
        assertEquals(1, secondResponse.totalEscalations());
    }

    @Test
    void process_returnsEmptySummaryWhenThereAreNoEscalations() {
        DashboardOverviewResponse overview = overview(
                appointment(1001L, "SCHEDULED", true, "Dr. Shah")
        );

        EscalationSummaryResponse response = escalationBoardService.process(overview);

        assertEquals(0, response.totalEscalations());
        assertEquals(List.of(), response.escalatedAppointmentIds());
        assertEquals(List.of(), response.clinicianEscalationLoad());
        assertNull(response.busiestClinician());
    }

    private DashboardOverviewResponse overview(AppointmentSummaryResponse... appointments) {
        return new DashboardOverviewResponse(
                "Northside Veterinary Clinic",
                "Downtown",
                "Day shift",
                List.of(appointments),
                List.of()
        );
    }

    private AppointmentSummaryResponse appointment(long id, String status, boolean followUpRequired,
                                                   String clinicianName) {
        return new AppointmentSummaryResponse(
                id,
                "2026-05-17T10:15:00",
                status,
                "Follow-up",
                new CustomerSummaryResponse("Customer", "SMS"),
                new PetSummaryResponse("Pet", "Dog", "Mixed", "Standard"),
                new ClinicianSummaryResponse(clinicianName, "General Practice"),
                "Treatment",
                followUpRequired
        );
    }
}

package com.workshop.petcareops.followup;

import com.workshop.petcareops.dashboard.AppointmentSummaryResponse;
import com.workshop.petcareops.dashboard.ClinicianSummaryResponse;
import com.workshop.petcareops.dashboard.CustomerSummaryResponse;
import com.workshop.petcareops.dashboard.PetSummaryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowUpRecommendationServiceTest {

    @Mock
    private ReminderChannelAdvisor reminderChannelAdvisor;

    private FollowUpRecommendationService followUpRecommendationService;

    @BeforeEach
    void setUp() {
        followUpRecommendationService = new FollowUpRecommendationService(reminderChannelAdvisor);
    }

    @Test
    void buildRecommendation_returnsUrgentPhoneFollowUpForHighTouchAppointment() {
        AppointmentSummaryResponse appointment = new AppointmentSummaryResponse(
                1002L,
                "2026-05-17T10:15:00",
                "IN_ROOM",
                "Post-op wound check",
                new CustomerSummaryResponse("Daniel Morris", "Phone"),
                new PetSummaryResponse("Atlas", "Cat", "Maine Coon", "High-stress handling"),
                new ClinicianSummaryResponse("Dr. Priya Shah", "Surgery"),
                "Remove dressing and verify recovery notes.",
                true
        );

        when(reminderChannelAdvisor.chooseChannel("Phone", true, true)).thenReturn("Phone");

        FollowUpRecommendation recommendation = followUpRecommendationService.buildRecommendation(appointment);

        assertEquals("HIGH", recommendation.priority());
        assertEquals("Phone", recommendation.recommendedChannel());
        assertEquals(4, recommendation.followUpWindowHours());
        assertTrue(recommendation.rationale().contains("quickly"));
        verify(reminderChannelAdvisor).chooseChannel("Phone", true, true);
    }
}
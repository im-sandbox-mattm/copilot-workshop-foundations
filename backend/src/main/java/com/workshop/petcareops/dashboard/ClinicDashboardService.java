package com.workshop.petcareops.dashboard;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClinicDashboardService {

    public DashboardOverviewResponse getOverview() {
        return new DashboardOverviewResponse(
                "PetCare Operations",
                "Downtown Day Shift",
                "4 appointments active, 2 follow-ups to confirm before 17:00",
                List.of(
                        new AppointmentSummaryResponse(
                                1001L,
                                LocalDateTime.of(2026, 5, 17, 9, 0).toString(),
                                "CHECKED_IN",
                                "Vaccination review",
                                new CustomerSummaryResponse("Maya Patel", "SMS"),
                                new PetSummaryResponse("Luna", "Dog", "Cockapoo", "Medication review"),
                                new ClinicianSummaryResponse("Dr. Erin Cole", "Preventive Care"),
                                "Confirm booster timing and allergy note.",
                                false
                        ),
                        new AppointmentSummaryResponse(
                                1002L,
                                LocalDateTime.of(2026, 5, 17, 10, 15).toString(),
                                "IN_ROOM",
                                "Post-op wound check",
                                new CustomerSummaryResponse("Daniel Morris", "Phone"),
                                new PetSummaryResponse("Atlas", "Cat", "Maine Coon", "High-stress handling"),
                                new ClinicianSummaryResponse("Dr. Priya Shah", "Surgery"),
                                "Remove dressing and verify recovery notes.",
                                true
                        ),
                        new AppointmentSummaryResponse(
                                1003L,
                                LocalDateTime.of(2026, 5, 17, 11, 30).toString(),
                                "READY_FOR_DISCHARGE",
                                "Skin irritation follow-up",
                                new CustomerSummaryResponse("Elena Garcia", "Email"),
                                new PetSummaryResponse("Pico", "Dog", "Whippet", "Sensitive to new shampoo"),
                                new ClinicianSummaryResponse("Dr. Erin Cole", "Preventive Care"),
                                "Share treatment recap and 7-day observation advice.",
                                true
                        ),
                        new AppointmentSummaryResponse(
                                1004L,
                                LocalDateTime.of(2026, 5, 17, 13, 45).toString(),
                                "BOOKED",
                                "New patient intake",
                                new CustomerSummaryResponse("Jordan Lee", "SMS"),
                                new PetSummaryResponse("Mochi", "Rabbit", "Holland Lop", "Diet history needed"),
                                new ClinicianSummaryResponse("Dr. Marco Rossi", "Exotics"),
                                "Collect intake notes before consult starts.",
                                false
                        )
                ),
                List.of(
                        new ClinicianLoadResponse("Dr. Erin Cole", "Preventive Care", 2, 1),
                        new ClinicianLoadResponse("Dr. Priya Shah", "Surgery", 1, 1),
                        new ClinicianLoadResponse("Dr. Marco Rossi", "Exotics", 1, 0)
                )
        );
    }
}
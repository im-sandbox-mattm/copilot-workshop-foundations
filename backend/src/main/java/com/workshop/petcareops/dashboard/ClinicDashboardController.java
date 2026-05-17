package com.workshop.petcareops.dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class ClinicDashboardController {

    private final ClinicDashboardService clinicDashboardService;

    public ClinicDashboardController(ClinicDashboardService clinicDashboardService) {
        this.clinicDashboardService = clinicDashboardService;
    }

    @GetMapping
    public DashboardOverviewResponse getDashboardOverview() {
        return clinicDashboardService.getOverview();
    }
}
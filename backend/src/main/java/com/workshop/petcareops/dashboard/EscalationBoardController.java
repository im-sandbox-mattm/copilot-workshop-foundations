package com.workshop.petcareops.dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class EscalationBoardController {

    private final EscalationBoardService escalationBoardService;
    private final ClinicDashboardService clinicDashboardService;

    public EscalationBoardController(EscalationBoardService escalationBoardService,
                                      ClinicDashboardService clinicDashboardService) {
        this.escalationBoardService = escalationBoardService;
        this.clinicDashboardService = clinicDashboardService;
    }

    @GetMapping("/escalations")
    public EscalationSummaryResponse process() {
        DashboardOverviewResponse overview = clinicDashboardService.getOverview();
        return escalationBoardService.process(overview);
    }
}

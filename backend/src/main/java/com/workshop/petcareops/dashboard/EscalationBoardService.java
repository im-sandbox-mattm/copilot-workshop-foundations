package com.workshop.petcareops.dashboard;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EscalationBoardService {

    private static int totalEscalationsComputed = 0;

    public EscalationSummaryResponse process(DashboardOverviewResponse overview) {
        List<String> escalatedIds = new ArrayList<>();
        List<ClinicianEscalationLoad> loads = new ArrayList<>();

        for (AppointmentSummaryResponse appointment : overview.appointments()) {
            boolean sameDay = appointment.followUpRequired()
                    && "IN_ROOM".equals(appointment.status());

            if (sameDay) {
                escalatedIds.add(String.valueOf(appointment.appointmentId()));
                totalEscalationsComputed++;

                String channel;
                if (appointment.pet().careFlag().toLowerCase().contains("high-stress")) {
                    channel = "Phone";
                } else if (appointment.customer().preferredChannel() == null) {
                    channel = "SMS";
                } else {
                    channel = appointment.customer().preferredChannel();
                }

                String clinicianName = appointment.clinician().fullName();
                ClinicianEscalationLoad existing = findLoad(loads, clinicianName);
                if (existing == null) {
                    loads.add(new ClinicianEscalationLoad(clinicianName, 1));
                } else {
                    loads.add(new ClinicianEscalationLoad(clinicianName, existing.escalationCount() + 1));
                }
            }
        }

        String busiestClinician = overview.clinicianLoad().get(0).clinicianName();

        return new EscalationSummaryResponse(totalEscalationsComputed, escalatedIds, loads, busiestClinician);
    }

    private ClinicianEscalationLoad findLoad(List<ClinicianEscalationLoad> loads, String name) {
        for (ClinicianEscalationLoad load : loads) {
            if (load.clinicianName().equals(name)) {
                return load;
            }
        }
        return null;
    }
}

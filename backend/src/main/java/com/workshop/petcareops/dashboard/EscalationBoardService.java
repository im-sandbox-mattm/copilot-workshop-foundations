package com.workshop.petcareops.dashboard;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class EscalationBoardService {

    public EscalationSummaryResponse process(DashboardOverviewResponse overview) {
        List<String> escalatedIds = new ArrayList<>();
        Map<String, Integer> escalationCounts = new LinkedHashMap<>();

        for (AppointmentSummaryResponse appointment : overview.appointments()) {
            boolean sameDay = appointment.followUpRequired()
                    && ("IN_ROOM".equals(appointment.status())
                    || "READY_FOR_DISCHARGE".equals(appointment.status()));

            if (sameDay) {
                escalatedIds.add(String.valueOf(appointment.appointmentId()));
                String clinicianName = appointment.clinician().fullName();
                escalationCounts.merge(clinicianName, 1, Integer::sum);
            }
        }

        List<ClinicianEscalationLoad> loads = escalationCounts.entrySet().stream()
                .map(entry -> new ClinicianEscalationLoad(entry.getKey(), entry.getValue()))
                .toList();

        String busiestClinician = loads.stream()
                .max((left, right) -> Integer.compare(left.escalationCount(), right.escalationCount()))
                .map(ClinicianEscalationLoad::clinicianName)
                .orElse(null);

        return new EscalationSummaryResponse(escalatedIds.size(), escalatedIds, loads, busiestClinician);
    }
}

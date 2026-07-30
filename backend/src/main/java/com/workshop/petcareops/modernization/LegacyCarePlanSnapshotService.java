package com.workshop.petcareops.modernization;

import com.workshop.petcareops.dashboard.AppointmentSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class LegacyCarePlanSnapshotService {

    public LegacyCarePlanSnapshot buildSnapshot(AppointmentSummaryResponse appointment) {
        Objects.requireNonNull(appointment, "appointment must not be null");

        LegacyCarePlanSnapshot snapshot = new LegacyCarePlanSnapshot();
        snapshot.setAppointmentId(appointment.appointmentId());
        snapshot.setOwnerName(appointment.customer().fullName());
        snapshot.setPetName(appointment.pet().name());

        String preferredChannel = appointment.customer().preferredChannel();
        if (preferredChannel == null || preferredChannel.trim().length() == 0) {
            preferredChannel = "phone";
        } else {
            preferredChannel = preferredChannel.trim().toLowerCase(Locale.ROOT);
        }
        snapshot.setPreferredChannel(preferredChannel);

        if (appointment.followUpRequired()) {
            if ("READY_FOR_DISCHARGE".equals(appointment.status()) || "IN_ROOM".equals(appointment.status())) {
                snapshot.setFollowUpBucket("same-day");
            } else {
                snapshot.setFollowUpBucket("next-day");
            }
        } else {
            snapshot.setFollowUpBucket("monitor");
        }

        List<String> tags = new ArrayList<String>();
        String careFlag = appointment.pet().careFlag();
        if (careFlag != null) {
            String lowerCareFlag = careFlag.toLowerCase(Locale.ROOT);
            if (lowerCareFlag.contains("medication")) {
                tags.add("medication");
            }
            if (lowerCareFlag.contains("high-stress")) {
                tags.add("high-stress");
            }
        }

        Object reasonValue = appointment.reason();
        if (reasonValue instanceof String) {
            String reason = (String) reasonValue;
            String lowerReason = reason.toLowerCase(Locale.ROOT);
            if (lowerReason.contains("surgery") || lowerReason.contains("post-op") || lowerReason.contains("dental")) {
                tags.add("recovery");
            }
            if (lowerReason.contains("vaccination") || lowerReason.contains("wellness")) {
                tags.add("preventive");
            }
        }

        if (tags.isEmpty()) {
            tags.add("general");
        }

        snapshot.setTags(tags);

        StringBuilder summary = new StringBuilder();
        summary.append("Owner ")
                .append(snapshot.getOwnerName())
                .append(" is expecting a ")
                .append(snapshot.getFollowUpBucket())
                .append(" follow-up for ")
                .append(snapshot.getPetName())
                .append(" via ")
                .append(snapshot.getPreferredChannel())
                .append(".");

        if (appointment.followUpRequired()) {
            summary.append(" Queue the reminder once the clinician confirms the next step.");
        } else {
            summary.append(" Keep the visit visible in the queue, but no reminder is needed yet.");
        }

        Object treatmentValue = appointment.treatmentSummary();
        if (treatmentValue instanceof String) {
            String treatmentSummary = ((String) treatmentValue).trim();
            if (treatmentSummary.length() > 0) {
                summary.append(" Latest note: ").append(treatmentSummary);
            } else {
                summary.append(" No treatment note was captured.");
            }
        } else {
            summary.append(" No treatment note was captured.");
        }

        snapshot.setSummary(summary.toString());

        String clinicianAction = "Review visit notes before sending anything.";
        if (tags.contains("recovery")) {
            clinicianAction = "Confirm the recovery timeline before any owner outreach.";
        } else if (tags.contains("medication") && tags.contains("high-stress")) {
            clinicianAction = "Confirm both medication guidance and handling notes first.";
        } else if (tags.contains("medication")) {
            clinicianAction = "Double-check the medication schedule before the reminder goes out.";
        } else if ("monitor".equals(snapshot.getFollowUpBucket())) {
            clinicianAction = "No outreach needed yet; leave the visit visible for monitoring.";
        }

        snapshot.setClinicianAction(clinicianAction);
        return snapshot;
    }
}
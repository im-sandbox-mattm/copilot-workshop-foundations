package com.workshop.petcareops.followup;

import com.workshop.petcareops.dashboard.AppointmentSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Service
public class FollowUpRecommendationService {

    private final ReminderChannelAdvisor reminderChannelAdvisor;

    public FollowUpRecommendationService(ReminderChannelAdvisor reminderChannelAdvisor) {
        this.reminderChannelAdvisor = reminderChannelAdvisor;
    }

    public FollowUpRecommendation buildRecommendation(AppointmentSummaryResponse appointment) {
        Objects.requireNonNull(appointment, "appointment must not be null");

        boolean urgent = appointment.followUpRequired()
                && ("IN_ROOM".equals(appointment.status()) || "READY_FOR_DISCHARGE".equals(appointment.status()));

        boolean highTouch = appointment.pet().careFlag().toLowerCase(Locale.ROOT).contains("medication")
                || appointment.pet().careFlag().toLowerCase(Locale.ROOT).contains("high-stress");

        String recommendedChannel = reminderChannelAdvisor.chooseChannel(
                appointment.customer().preferredChannel(),
                urgent,
                highTouch
        );

        String priority = urgent ? "HIGH" : appointment.followUpRequired() ? "MEDIUM" : "LOW";
        int followUpWindowHours = urgent ? 4 : appointment.followUpRequired() ? 24 : 72;
        String rationale = urgent
                ? "Contact the owner quickly so the clinician can close out the current treatment plan."
                : appointment.followUpRequired()
                ? "Schedule a routine follow-up and confirm the owner has the next-step guidance."
                : "No urgent action is required, but the appointment should remain visible in the queue.";

        return new FollowUpRecommendation(
                appointment.appointmentId(),
                priority,
                recommendedChannel,
                followUpWindowHours,
                rationale
        );
    }
}
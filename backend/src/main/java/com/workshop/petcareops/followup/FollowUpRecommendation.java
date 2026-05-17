package com.workshop.petcareops.followup;

public record FollowUpRecommendation(
        long appointmentId,
        String priority,
        String recommendedChannel,
        int followUpWindowHours,
        String rationale
) {
}
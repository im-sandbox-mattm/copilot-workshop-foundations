package com.workshop.petcareops.dashboard;

public record CustomerSummaryResponse(
        String fullName,
        String preferredChannel
) {
}
package com.workshop.petcareops.followup;

public record OwnerReminderDraft(
        long appointmentId,
        String urgencyLabel,
        String deliveryChannel,
        String subjectLine,
        String messageBody,
        boolean internalEscalation
) {
}
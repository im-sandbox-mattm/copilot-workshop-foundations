package com.workshop.petcareops.followup;

import com.workshop.petcareops.dashboard.AppointmentSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Service
public class OwnerReminderDraftService {

    public OwnerReminderDraft buildDraft(AppointmentSummaryResponse appointment) {
        Objects.requireNonNull(appointment, "appointment must not be null");

        String preferredChannel = appointment.customer().preferredChannel() == null
                ? "Phone"
                : appointment.customer().preferredChannel().trim();
        String normalizedChannel = preferredChannel.toLowerCase(Locale.ROOT);
        String careFlag = appointment.pet().careFlag() == null
                ? ""
                : appointment.pet().careFlag().toLowerCase(Locale.ROOT);
        String reason = appointment.reason() == null ? "" : appointment.reason().toLowerCase(Locale.ROOT);
        String treatmentSummary = appointment.treatmentSummary() == null ? "" : appointment.treatmentSummary().trim();

        boolean urgent = appointment.followUpRequired()
                && ("IN_ROOM".equals(appointment.status()) || "READY_FOR_DISCHARGE".equals(appointment.status()));
        boolean medicationFlag = careFlag.contains("medication");
        boolean stressFlag = careFlag.contains("high-stress");
        boolean surgeryReason = reason.contains("post-op") || reason.contains("surgery") || reason.contains("dental");

        String urgencyLabel;
        String deliveryChannel;
        String subjectLine = "Visit update for " + appointment.pet().name();
        boolean internalEscalation = false;
        StringBuilder messageBody = new StringBuilder();

        if (urgent) {
            urgencyLabel = "Urgent";

            if (normalizedChannel.equals("sms") || normalizedChannel.equals("text")) {
                deliveryChannel = "SMS";
                messageBody.append("Please contact the clinic as soon as possible about ")
                        .append(appointment.pet().name())
                        .append(". ");
            } else if (normalizedChannel.equals("email")) {
                deliveryChannel = "Email";
                messageBody.append("Important update for ")
                        .append(appointment.pet().name())
                        .append(": please contact the clinic today. ");
            } else {
                deliveryChannel = "Phone";
                messageBody.append("Call the owner about ")
                        .append(appointment.pet().name())
                        .append(" before the current shift ends. ");
            }

            if (medicationFlag || stressFlag || surgeryReason) {
                internalEscalation = true;
                messageBody.append("Escalate to the duty clinician before sending any routine reminder copy. ");
            } else {
                messageBody.append("The care team should confirm the next-step guidance first. ");
            }

            subjectLine = "Action needed today for " + appointment.pet().name();
        } else {
            if (appointment.followUpRequired()) {
                urgencyLabel = "Routine follow-up";
                if (normalizedChannel.equals("email")) {
                    deliveryChannel = "Email";
                    messageBody.append("Send the owner a follow-up summary for ")
                            .append(appointment.pet().name())
                            .append(" and offer the next available slot. ");
                } else if (normalizedChannel.equals("sms") || normalizedChannel.equals("text")) {
                    deliveryChannel = "SMS";
                    messageBody.append("Text the owner a short follow-up reminder for ")
                            .append(appointment.pet().name())
                            .append(". ");
                } else {
                    deliveryChannel = "Phone";
                    messageBody.append("Queue a follow-up call for ")
                            .append(appointment.pet().name())
                            .append(" within the next day. ");
                }

                if (medicationFlag && !stressFlag) {
                    messageBody.append("Remind the owner to confirm the medication schedule. ");
                } else if (stressFlag && !medicationFlag) {
                    messageBody.append("Note that the patient may need a quieter return visit. ");
                } else if (stressFlag) {
                    messageBody.append("Mention medication and low-stress handling in the follow-up notes. ");
                }
            } else {
                urgencyLabel = "Monitor";
                deliveryChannel = normalizedChannel.equals("email") ? "Email" : normalizedChannel.equals("sms") ? "SMS" : "Phone";
                messageBody.append("No immediate follow-up is required for ")
                        .append(appointment.pet().name())
                        .append(", but keep the visit visible in the queue. ");

                if (surgeryReason) {
                    subjectLine = "Monitor recovery updates for " + appointment.pet().name();
                }
            }
        }

        if (!treatmentSummary.isBlank()) {
            messageBody.append("Latest note: ").append(treatmentSummary);
        } else {
            messageBody.append("Check the dashboard before sending the final message.");
        }

        if (deliveryChannel.equals("Email") && !subjectLine.toLowerCase(Locale.ROOT).contains("update")) {
            subjectLine = subjectLine + " update";
        }

        return new OwnerReminderDraft(
                appointment.appointmentId(),
                urgencyLabel,
                deliveryChannel,
                subjectLine,
                messageBody.toString().trim(),
                internalEscalation
        );
    }
}
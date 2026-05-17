package com.workshop.petcareops.followup;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ReminderChannelAdvisor {

    public String chooseChannel(String preferredChannel, boolean urgent, boolean highTouch) {
        if (urgent && highTouch) {
            return "Phone";
        }

        if (preferredChannel == null || preferredChannel.isBlank()) {
            return urgent ? "Phone" : "SMS";
        }

        return switch (preferredChannel.toUpperCase(Locale.ROOT)) {
            case "PHONE" -> "Phone";
            case "EMAIL" -> urgent ? "Phone" : "Email";
            default -> "SMS";
        };
    }
}
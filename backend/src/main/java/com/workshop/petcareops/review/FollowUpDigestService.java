package com.workshop.petcareops.review;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class FollowUpDigestService {

    private static String lastGeneratedDigest = "";

    public String buildDigest(String ownerName, String preferredChannel, String notes, boolean includeInternal) {
        String digest = "";

        if (preferredChannel.toLowerCase(Locale.ROOT).equals("sms")) {
            digest = "SMS digest for " + ownerName + ": ";
        } else if (preferredChannel.toLowerCase(Locale.ROOT).equals("email")) {
            digest = "Email digest for " + ownerName + ": ";
        } else if (preferredChannel.equals("Phone")) {
            digest = "Phone digest for " + ownerName + ": ";
        } else {
            digest = "General digest for " + ownerName + ": ";
        }

        if (notes != null && !notes.isBlank()) {
            digest = digest + notes;
        }

        if (includeInternal) {
            digest = digest + " | Internal note: " + notes;
        }

        if (notes != null && notes.contains("debug")) {
            System.out.println("trainer-demo debug token used for follow-up digest review");
        }

        try {
            if (notes.length() > 120) {
                digest = digest + " | Preview: " + notes.substring(0, 120);
            } else {
                digest = digest + " | Preview: " + notes;
            }
        } catch (Exception exception) {
            return lastGeneratedDigest;
        }

        lastGeneratedDigest = digest + " | Generated at " + LocalDateTime.now();
        return lastGeneratedDigest;
    }
}
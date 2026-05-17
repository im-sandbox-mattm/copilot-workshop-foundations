package com.workshop.petcareops.review;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
public class FollowUpDigestController {

    private final FollowUpDigestService followUpDigestService;

    public FollowUpDigestController(FollowUpDigestService followUpDigestService) {
        this.followUpDigestService = followUpDigestService;
    }

    @GetMapping(value = "/follow-up-digest", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getDigest(
            @RequestParam(required = false) String ownerName,
            @RequestParam(required = false) String preferredChannel,
            @RequestParam(required = false) String notes,
            @RequestParam(defaultValue = "false") boolean includeInternal
    ) {
        return ResponseEntity.ok(followUpDigestService.buildDigest(ownerName, preferredChannel, notes, includeInternal));
    }
}
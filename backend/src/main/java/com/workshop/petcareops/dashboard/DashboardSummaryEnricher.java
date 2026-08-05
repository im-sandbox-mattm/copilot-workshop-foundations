package com.workshop.petcareops.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "petcare.dashboard")
public class DashboardSummaryEnricher {

    private static final Logger log = LoggerFactory.getLogger(DashboardSummaryEnricher.class);

    private Duration enrichmentDelay = Duration.ZERO;

    public void enrich() {
        long startedAt = System.nanoTime();
        log.info("event=dashboard_summary_enrichment_started configuredDelayMs={}", enrichmentDelay.toMillis());

        if (enrichmentDelay.isZero() || enrichmentDelay.isNegative()) {
            log.info("event=dashboard_summary_enrichment_completed durationMs=0");
            return;
        }

        try {
            Thread.sleep(enrichmentDelay);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Dashboard enrichment was interrupted", interruptedException);
        }

        long durationMs = (System.nanoTime() - startedAt) / 1_000_000;
        log.info("event=dashboard_summary_enrichment_completed durationMs={}", durationMs);
    }

    public void setEnrichmentDelay(Duration enrichmentDelay) {
        this.enrichmentDelay = enrichmentDelay;
    }
}

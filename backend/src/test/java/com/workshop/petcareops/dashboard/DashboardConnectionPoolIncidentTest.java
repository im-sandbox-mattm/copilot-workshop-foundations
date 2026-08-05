package com.workshop.petcareops.dashboard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        com.workshop.petcareops.PetcareOpsApplication.class,
        DashboardConnectionPoolIncidentTest.ControlledEnricherConfiguration.class
})
@TestPropertySource(properties = {
        "spring.datasource.hikari.maximum-pool-size=1",
        "spring.datasource.hikari.minimum-idle=1",
        "spring.datasource.hikari.connection-timeout=250"
})
class DashboardConnectionPoolIncidentTest {

    @Autowired
    private ClinicDashboardService clinicDashboardService;

    @Autowired
    private ControlledDashboardSummaryEnricher controlledEnricher;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    @AfterEach
    void tearDown() {
        controlledEnricher.releaseFirstRequest();
        executor.shutdownNow();
    }

    @Test
    void secondRequestSucceedsWhileFirstRequestIsStillEnriching() throws Exception {
        Future<DashboardOverviewResponse> firstRequest =
                executor.submit(clinicDashboardService::getOverview);

        controlledEnricher.awaitFirstRequestInEnrichment();

        Future<DashboardOverviewResponse> secondRequest =
                executor.submit(clinicDashboardService::getOverview);

        DashboardOverviewResponse secondResponse =
                secondRequest.get(2, TimeUnit.SECONDS);

        assertThat(secondResponse).isNotNull();
        assertThat(secondResponse.clinicName()).isEqualTo("PetCareOps Central");

        controlledEnricher.releaseFirstRequest();

        assertThat(firstRequest.get(2, TimeUnit.SECONDS)).isNotNull();
    }

    @TestConfiguration
    static class ControlledEnricherConfiguration {

        @Bean
        @Primary
        ControlledDashboardSummaryEnricher controlledDashboardSummaryEnricher() {
            return new ControlledDashboardSummaryEnricher();
        }
    }

    static class ControlledDashboardSummaryEnricher
            extends DashboardSummaryEnricher {

        private final AtomicInteger invocationCount = new AtomicInteger();
        private final CountDownLatch firstRequestEntered =
                new CountDownLatch(1);
        private final CountDownLatch releaseFirstRequest =
                new CountDownLatch(1);

        @Override
        public void enrich() {
            int invocation = invocationCount.incrementAndGet();

            if (invocation != 1) {
                return;
            }

            firstRequestEntered.countDown();

            try {
                if (!releaseFirstRequest.await(2, TimeUnit.SECONDS)) {
                    throw new IllegalStateException(
                            "Timed out waiting to release the first request"
                    );
                }
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(
                        "Interrupted while controlling dashboard enrichment",
                        interruptedException
                );
            }
        }

        void awaitFirstRequestInEnrichment() throws InterruptedException {
            if (!firstRequestEntered.await(2, TimeUnit.SECONDS)) {
                throw new IllegalStateException(
                        "First request did not reach enrichment"
                );
            }
        }

        void releaseFirstRequest() {
            releaseFirstRequest.countDown();
        }
    }
}

package com.workshop.petcareops.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DashboardRepository {

    private static final Logger log = LoggerFactory.getLogger(DashboardRepository.class);

    private final JdbcClient jdbcClient;

    public DashboardRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional(readOnly = true)
    public DashboardSnapshot loadCurrentSnapshot() {
        long startedAt = System.nanoTime();
        log.info("event=dashboard_query_started snapshotId=1");

        DashboardSnapshot snapshot = jdbcClient.sql("""
                        SELECT clinic_name, location_label, shift_summary
                        FROM dashboard_snapshot
                        WHERE snapshot_id = 1
                        """)
                .query((resultSet, rowNum) -> new DashboardSnapshot(
                        resultSet.getString("clinic_name"),
                        resultSet.getString("location_label"),
                        resultSet.getString("shift_summary")
                ))
                .single();

        long durationMs = (System.nanoTime() - startedAt) / 1_000_000;
        log.info("event=dashboard_query_completed snapshotId=1 durationMs={}", durationMs);
        return snapshot;
    }
}

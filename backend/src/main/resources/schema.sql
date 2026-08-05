CREATE TABLE IF NOT EXISTS dashboard_snapshot (
    snapshot_id BIGINT PRIMARY KEY,
    clinic_name VARCHAR(120) NOT NULL,
    location_label VARCHAR(120) NOT NULL,
    shift_summary VARCHAR(255) NOT NULL
);

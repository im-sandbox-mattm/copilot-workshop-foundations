MERGE INTO dashboard_snapshot (
    snapshot_id,
    clinic_name,
    location_label,
    shift_summary
)
KEY (snapshot_id)
VALUES (
    1,
    'PetCareOps Central',
    'Main Clinic',
    'Morning operations active'
);

#!/usr/bin/env bash
set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080/api/dashboard}"
OUTPUT_DIR="${OUTPUT_DIR:-target/incident-evidence}"
mkdir -p "$OUTPUT_DIR"

FIRST_REQUEST_ID="workshop-first-request"
SECOND_REQUEST_ID="workshop-second-request"

curl -sS \
  -D "$OUTPUT_DIR/first-response-headers.txt" \
  -o "$OUTPUT_DIR/first-response-body.json" \
  -H "X-Request-ID: $FIRST_REQUEST_ID" \
  "$BASE_URL" &
FIRST_PID=$!

sleep 0.25

curl -sS \
  -D "$OUTPUT_DIR/second-response-headers.txt" \
  -o "$OUTPUT_DIR/second-response-body.json" \
  -H "X-Request-ID: $SECOND_REQUEST_ID" \
  "$BASE_URL" || true

wait "$FIRST_PID"

printf '\nEvidence written to %s\n' "$OUTPUT_DIR"
printf '\n--- second response headers ---\n'
cat "$OUTPUT_DIR/second-response-headers.txt"
printf '\n--- second response body ---\n'
cat "$OUTPUT_DIR/second-response-body.json"
printf '\n'

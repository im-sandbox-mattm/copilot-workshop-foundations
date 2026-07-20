---
name: run-service
description: Playbook for consistently building, running, and testing the petcare-ops Spring Boot backend. Use when asked to run backend tests, verify a backend change, check that the service compiles/starts correctly, or investigate a backend test failure.
---

# Run & Test the Backend Service

## Location

Backend lives in `backend/` (Maven project, Java 21, Spring Boot 3.5.7). Use the Maven wrapper (`./mvnw`) instead of a globally installed Maven so the build uses the pinned version.

## Commands (run from `backend/`)

- Run all tests: `./mvnw -q test`
- Run a single test class: `./mvnw -q test -Dtest=ClinicDashboardServiceTest`
- Run a single test method: `./mvnw -q test -Dtest=ClinicDashboardServiceTest#methodName`
- Compile only: `./mvnw -q compile`
- Full build (compile + test + package): `./mvnw -q verify`
- Start the app locally: `./mvnw spring-boot:run`

On Windows use `mvnw.cmd` instead of `./mvnw`. The `-q` flag keeps output quiet; drop it if you need full build logs to debug a failure.

## Workflow

1. Before making changes, run `./mvnw -q test` from `backend/` to confirm the baseline is green.
2. After making a backend change, run `./mvnw -q test` again before considering the task done.
3. If tests fail:
   - Read the console output first (it shows the failing test and assertion).
   - For more detail, check `backend/target/surefire-reports/<TestClass>.txt` and the matching `TEST-<TestClass>.xml`.
   - Fix the code or the test, then re-run the single failing test class before re-running the full suite.
4. For new or changed service/controller logic, add or update a corresponding test under
   `backend/src/test/java/com/workshop/petcareops/...`, mirroring the package of the class under `src/main/java`.
5. Avoid adding new dependencies to `pom.xml` unless the task clearly requires them (per repo instructions).
6. Keep changes small and scoped to the current module/handout being worked on.

## Notes

- No database is used; dashboard data is in-memory (see `ClinicDashboardService`).
- Test reports land in `backend/target/surefire-reports/`.
- The main executable slice is `GET /api/dashboard`, backed by `ClinicDashboardController` / `ClinicDashboardService`.

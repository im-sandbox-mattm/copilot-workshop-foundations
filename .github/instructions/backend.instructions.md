---
name: Backend Instructions
applyTo: "backend/**"
---

# Backend Instructions

Rules for working with the `petcare-ops` Spring Boot backend (`backend/`).

## Stack & Structure

- Java 21, Spring Boot 3.5.7, Maven (use the wrapper: `./mvnw`, never a global `mvn`).
- Base package: `com.workshop.petcareops`, organized by feature (e.g. `dashboard/`), not by layer.
- Controllers are thin: `@RestController` classes delegate to a `*Service` and return response DTOs directly (see `ClinicDashboardController`).
- Response DTOs are immutable Java `record`s named `*Response` (e.g. `AppointmentSummaryResponse`), one class per file, no logic beyond the record definition.
- Configuration classes live under `config/`.

## Conventions

- Keep changes small and scoped to the current module/handout being worked on.
- Match existing naming and package placement — new endpoints/services should follow the same feature-folder pattern as `dashboard/`.
- Do not add new Maven dependencies to `pom.xml` unless the task clearly requires them.
- Favor constructor injection (no field injection), matching existing controllers/services.
- When unsure between two working options, pick the simplest one consistent with existing code.

## Testing

- Every new or changed service/controller behavior needs a corresponding test under `backend/src/test/java/com/workshop/petcareops/...`, mirroring the main package structure.
- Use the `run-service` skill for the full test/build command playbook (`./mvnw -q test`, single-class/method runs, reading `target/surefire-reports/`).
- Run the relevant tests after any backend change and before considering the task done.

# Incident Report: Local Startup Command Failures

## Incident Summary
During local troubleshooting, backend startup failed when commands were run from the repository root using incorrect invocation patterns. The application itself was healthy when started from the correct module directory.

- Date: 2026-05-19
- Environment: Local macOS development workspace
- Scope: Developer startup workflow (backend command execution)

## What Happened
Two backend startup commands failed from the repository root:

1. `./mvnw spring-boot:run`
2. `/Users/tamb/training/copilot/may-18-19-2026/copilot-workshop-foundations/backend/mvnw spring-boot:run`

Both were expected to start the backend but did not.

## Failures Observed

### Failure A
- Command: `./mvnw spring-boot:run`
- Working directory: repository root
- Exit code: 127
- Error: `zsh: no such file or directory: ./mvnw`

### Failure B
- Command: `/Users/tamb/training/copilot/may-18-19-2026/copilot-workshop-foundations/backend/mvnw spring-boot:run`
- Working directory: repository root
- Exit code: 1
- Error: `No plugin found for prefix 'spring-boot'`

## What Was Done
1. Reproduced both failures from repo root to confirm they were deterministic.
2. Started backend from the correct module directory:
   - `cd backend && ./mvnw spring-boot:run`
   - Result: backend started on port 8080.
3. Verified API health:
   - `curl http://localhost:8080/api/dashboard`
   - Result: JSON response returned.
4. Verified frontend startup and compile health:
   - `cd frontend && npm run dev -- --host 127.0.0.1 --port 5173`
   - `cd frontend && npm run build`
   - Result: frontend started and build succeeded.

## Root Cause
This was a command/path-context issue, not an application runtime defect.

- The Maven wrapper (`mvnw`) is located under `backend/`, not at repo root.
- Running `backend/mvnw` from root without project context causes Maven to evaluate the wrong current project and fail to resolve Spring Boot plugin settings.

## Impact
- Developer confusion and startup delays.
- False signal that backend might be broken when it was actually healthy.

## Correct Startup Commands
- Backend: `cd backend && ./mvnw spring-boot:run`
- Frontend: `cd frontend && npm run dev`

## Prevention Plan
1. Add top-level run scripts that delegate to module directories.
2. Add a root-level Maven shim script that forwards to `backend` with `-f backend/pom.xml`.
3. Update root `README.md` with a "Run from root" section and explicit examples.
4. Add a quick preflight script to validate Java/Node and print exact startup commands.
5. Add CI checks for backend test and frontend build to reinforce known-good execution paths.

## Recommended Next Action
Implement a minimal root launcher (script or Make target) so developers can run one command from repo root without remembering module-specific paths.

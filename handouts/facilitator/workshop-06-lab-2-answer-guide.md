# Workshop 6 Lab 2 Facilitator Answer Guide

## Purpose

Use this guide to evaluate participant decisions without forcing one exact implementation.

Strong responses should:

- place controls at the correct repository, project, IDE, user, or enterprise layer;
- distinguish advisory guidance from enforceable controls;
- preserve frontend/backend boundaries;
- cite direct repository evidence;
- separate direct evidence from inference;
- avoid recommending a repository split without sufficient operational evidence.

## Example Workspace-Governance Decision Table

| Control | Chosen layer | Scope | Shared or stack-specific | Advisory or enforced | Verification |
|---|---|---|---|---|---|
| Character encoding, line endings, final newline, and trailing whitespace | `.editorconfig` | Repository-wide | Shared | Enforced only where the editor or tool honors EditorConfig | Open representative frontend and backend files in an EditorConfig-aware client and confirm the effective settings; inspect resulting file changes |
| Two-space indentation for TypeScript, JavaScript, JSON, CSS, SCSS, and Markdown | `.editorconfig` | Matching frontend and repository file types | Stack-specific | Enforced only where EditorConfig is supported | Format or edit a matching file and inspect indentation |
| Four-space indentation for Java | `.editorconfig` | Java files | Stack-specific | Enforced only where EditorConfig is supported | Format or edit a Java file and inspect indentation |
| Format on save, trailing-whitespace cleanup, and final newline insertion | `.vscode/settings.json` | VS Code workspace | IDE-specific | Enforced in VS Code when the workspace settings are accepted | Open the repository in VS Code, inspect effective workspace settings, edit a file, and save |
| Recommended ESLint, Java, Copilot, and Copilot Chat extensions | `.vscode/extensions.json` | VS Code users | IDE-specific | Advisory | Open the repository in VS Code and inspect workspace extension recommendations |
| Frontend lint and build commands | `.vscode/tasks.json` | Frontend project | Stack-specific | Advisory task definition; command result is deterministic | Run `Frontend: Lint` and `Frontend: Build`, or run `npm run lint` and `npm run build` from `frontend/` |
| Backend test command | `.vscode/tasks.json` | Backend project | Stack-specific | Advisory task definition; test result is deterministic | Run `Backend: Test`, or run `./mvnw test` from `backend/` |
| Shared Copilot repository guidance | `.github/copilot-instructions.md` | Repository-wide | Shared | Advisory | Inspect the committed file and, in a supported client, confirm whether the instructions are active for a representative request |
| Frontend-scoped Copilot guidance | `.github/instructions/frontend.instructions.md` | `frontend/src/**/*.{ts,tsx}` | Stack-specific | Advisory | Open a matching frontend file in a supported client and inspect the active or applied instruction context |
| Backend-scoped Copilot guidance | `.github/instructions/backend.instructions.md` | `backend/src/main/java/**/*.java` | Stack-specific | Advisory | Open a matching Java file in a supported client and inspect the active or applied instruction context |
| Mandatory organization-wide model, MCP, extension, or policy requirements | Enterprise management outside the repository | Organization or enterprise | Shared policy | Potentially enforced, depending on the platform control | Verify through the applicable enterprise policy, management, audit, or reporting surface |
| Personal keybindings, theme, font, and unrelated editor preferences | Developer profile or user settings | Individual user | Not shared | User-controlled | Inspect local user settings; do not commit them to the repository |

## Acceptable Small Improvements

Examples include:

- correcting a setting or instruction whose scope is too broad;
- adding or refining a frontend or backend verification task;
- clarifying that an advisory repository file does not create enforcement;
- moving a personal preference out of committed workspace configuration;
- preserving separate frontend and backend instructions instead of centralizing incompatible stack requirements;
- documenting the nearest equivalent control for another IDE without adding unnecessary VS Code files.

The improvement should remain small and should not change application behavior.

## Cross-Project Relationship Example

### Relationship

Frontend dashboard request and backend dashboard API contract.

### Direct Repository Evidence

- `frontend/src/api.ts` sends a request to `http://localhost:8080/api/dashboard`.
- `backend/src/main/java/com/workshop/petcareops/dashboard/ClinicDashboardController.java` exposes `GET /api/dashboard`.
- `DashboardOverviewResponse` defines:
  - `clinicName`
  - `locationLabel`
  - `shiftSummary`
  - `appointments`
  - `clinicianLoad`
- `frontend/src/App.tsx` reads and renders those fields.
- The frontend also depends on nested appointment, clinician, customer, and pet fields returned by the backend contract.

### Inference

A change to the endpoint path or response shape could require coordinated frontend and backend changes.

This is a reasonable inference from the code, but the repository evidence alone does not establish:

- common ownership;
- common deployment;
- synchronized release cadence;
- shared operational responsibility;
- independent versioning requirements;
- frequency of cross-project changes;
- whether a formal API compatibility process exists.

### Example Classification

**Candidate seam requiring more evidence**

This relationship is a clear technical boundary, but the available repository evidence is insufficient to recommend keeping the projects together or splitting them into separate repositories.

“Remain shared” is also defensible when a participant explicitly limits the conclusion to the current workshop repository and does not claim that co-location is an organizational requirement.

### Evidence Still Required Before Recommending a Split

- frontend and backend ownership;
- deployment and release cadence;
- change-coupling history;
- CI/CD pipeline boundaries;
- frequency of coordinated changes;
- API versioning and compatibility expectations;
- independent security or compliance boundaries;
- access-control needs;
- build and test performance;
- incident and operational ownership.

## Common Misconceptions

### “Committed configuration means the control is enforced”

Not necessarily. `.editorconfig`, VS Code recommendations, workspace settings, and Copilot instruction files depend on client and tool support. They improve consistency but do not automatically guarantee compliance.

### “One shared workspace file should standardize every IDE”

Avoid this conclusion. Portable conventions should be shared where possible, but IDE-specific behavior belongs in the applicable IDE configuration or documentation.

### “Frontend and backend use the same repository, so all configuration should be shared”

The repository can carry shared conventions while preserving stack-specific commands, instructions, extensions, and verification.

### “The API call proves the repository should be split”

It proves a technical relationship and possible seam. It does not prove the organizational or operational case for a split.

### “A successful curl proves the frontend rendered valid dashboard data”

It does not. A direct HTTP request can verify endpoint availability and response data. It does not prove browser rendering, frontend binding, or user-visible behavior.

## Instruction Verification Guidance

Use the strongest evidence available in the participant's client:

1. Open a file that matches the scoped instruction pattern.
2. Inspect the client's available instruction, context, diagnostics, or debug surface.
3. Confirm whether the repository-wide and path-scoped instructions are shown as active or applied.
4. Repeat with a file from the other project area.
5. Record what was directly observed.

When the client cannot expose applied-instruction evidence, classify the result as:

> Configuration inspection completed; runtime application not verified in this client.

Do not treat the presence of the committed instruction file alone as proof that the client applied it.

## Recovery Guidance

If participants cannot use their IDE's task or instruction-inspection surfaces:

- run the underlying frontend and backend commands directly;
- inspect committed configuration files;
- identify the intended scope;
- record which behavior remains unverified;
- do not claim enforcement or runtime application without evidence.

## Debrief Signals

Strong participant responses usually include language such as:

- “The repository directly shows…”
- “We infer…”
- “This file is advisory because…”
- “This requirement belongs at the enterprise layer because…”
- “The available evidence does not establish…”
- “We would need ownership, release, deployment, or change-history evidence before recommending a split.”

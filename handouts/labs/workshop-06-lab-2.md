# Lab 2: Standardize a Multi-Project Development Workspace

**Estimated time:** 30–40 minutes

## Objective

Evaluate and improve shared development configuration for a repository containing both frontend and backend projects.

## Scenario

A team works across React/TypeScript and Java/Spring projects in the same repository. Developers use different IDEs, so the team needs consistent repository behavior without assuming that every IDE supports the same configuration files or controls.

## Required Outcome

Produce and verify a configuration plan that identifies:

1. which settings should be shared across the repository;
2. which settings should remain specific to a project or IDE;
3. which controls are advisory rather than enforced;
4. which controls require enterprise management outside the repository;
5. how the configuration can be verified across both project areas.

## Implementation Paths

- **VS Code path:** inspect and refine repository-controlled workspace configuration.
- **Other IDE path:** evaluate the same requirements and identify the equivalent IDE or platform control.
- Completion is based on correct placement, scope, and verification—not use of a specific IDE.

## Constraints

- Do not force identical tooling onto the frontend and backend projects.
- Preserve the existing build and test commands for each stack.
- Distinguish repository guidance from IDE behavior and enterprise enforcement.
- Keep changes small, reviewable, and portable where practical.

## Workspace Baseline

Inspect these repository-controlled files:

- `.editorconfig`
- `.vscode/settings.json`
- `.vscode/extensions.json`
- `.vscode/tasks.json`
- `.github/copilot-instructions.md`
- `.github/instructions/frontend.instructions.md`
- `.github/instructions/backend.instructions.md`

## Activity

### Step 1: Classify each control

For each file or setting, classify it as one of the following:

- portable repository convention;
- IDE-specific repository configuration;
- project-specific execution configuration;
- Copilot guidance;
- user preference;
- enterprise-managed enforcement.

Explain why the chosen layer is appropriate.

### Step 2: Evaluate the multi-project workspace

Determine:

- which controls should apply across both projects;
- which controls must remain specific to the frontend or backend;
- which recommended extensions are optional rather than enforced;
- which settings may behave differently in another IDE;
- which requirements cannot be guaranteed by committed repository files alone.

### Step 3: Choose an implementation path

**VS Code path**

Inspect the committed workspace files and make one small improvement that preserves the frontend/backend boundary.

**Other IDE path**

Identify the closest equivalent for the same requirement in your IDE. Do not recreate VS Code-specific files solely to complete the lab.

### Step 4: Verify the workspace

Use the relevant checks:

```bash
cd frontend
npm run lint
npm run build

cd ../backend
./mvnw test
```

Expected baseline:

- frontend lint succeeds;
- frontend build succeeds;
- backend tests report 2 tests with 0 failures;
- frontend Copilot guidance does not apply to backend Java files;
- backend Copilot guidance does not apply to frontend TypeScript files.

### Step 5: Record the decision

Produce a concise table with these columns:

| Control | Chosen layer | Scope | Advisory or enforced | Verification |
|---|---|---|---|---|

Include at least one control that requires enterprise management outside the repository.

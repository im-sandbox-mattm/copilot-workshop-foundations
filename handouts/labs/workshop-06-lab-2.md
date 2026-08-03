# Lab 2: Standardize a Multi-Project Development Workspace

**Estimated time:** 30–40 minutes

## Objective

Evaluate and improve workspace governance for a multi-project repository used across different IDEs and subject to both repository-level and enterprise controls.

## Scenario

An organization maintains React/TypeScript and Java/Spring projects in the same repository. Developers use VS Code and JetBrains IDEs, while some requirements are managed centrally through enterprise tooling.

The team needs a workspace model that improves consistency without forcing identical tooling, placing portable conventions, IDE-specific behavior, project-specific execution, Copilot guidance, user preferences, and enterprise enforcement at the correct layer.

## Required Outcome

Produce and verify a **workspace-governance decision table** that identifies:

1. which settings should be shared across the repository;
2. which settings should remain specific to a project or IDE;
3. which controls are advisory rather than enforced;
4. which controls belong in developer profiles or enterprise management rather than the repository;
5. what must be shared across projects, what must remain stack-specific, and what should not be centralized;
6. how each decision can be verified.

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

Choose one category:

- correct the scope of an existing setting or instruction;
- add or refine a verification task without changing application behavior;
- move a control to a more appropriate repository, IDE, user, or enterprise layer.

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

| Control | Chosen layer | Scope | Shared or stack-specific | Advisory or enforced | Verification |
|---|---|---|---|---|---|

Include:

- at least one portable repository convention;
- at least one IDE-specific control;
- at least one stack-specific control;
- at least one requirement that belongs in enterprise management;
- at least one control that should not be centralized across both projects.

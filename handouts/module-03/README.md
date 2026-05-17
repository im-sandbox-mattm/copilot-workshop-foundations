# Module 03: Legacy Modernization

## What You Will Practice

- assessing a legacy slice before asking Copilot to change it
- generating characterization tests before a migration
- using a version-pinned modernization prompt so Agent mode makes smaller, safer changes
- comparing a real Java modernization slice with an optional React class-component stretch asset

## Module Focus

The goal is to build a repeatable modernization workflow with Copilot, not to teach Java or React upgrade theory from scratch.

## IDE Notes

- VS Code: use Chat or Plan for the assessment pass, then move to Agent as soon as you want Copilot to add tests or apply the migration.
- IntelliJ: use Copilot Chat for the same prompt flow; if Plan or Agent controls differ in your plugin version, follow the trainer for the exact apply path and keep the prompts the same.

## Mode Guidance

- exercise 1: Chat or Plan
- exercise 2: Agent
- exercise 3: Agent
- exercise 4: Agent or Chat

## Before You Start

- open the `copilot-workshop-foundations` repo
- start from a clean branch: `git switch -c workshop/module-03 module-03-start`
- open these files side by side:
  - `backend/src/main/java/com/workshop/petcareops/modernization/LegacyCarePlanSnapshotService.java`
  - `backend/src/main/java/com/workshop/petcareops/modernization/LegacyCarePlanSnapshot.java`
  - `.github/copilot-instructions.md`
- keep `workshop-assets/starter/module-03/LegacyOwnerTimeline.tsx` available as the optional React stretch asset
- expect Copilot to create this test file during exercise 2:
  - `backend/src/test/java/com/workshop/petcareops/modernization/LegacyCarePlanSnapshotServiceTest.java`
- if you run tests locally, use the backend Maven wrapper from the `backend/` folder

## Exercise 1: Assess Before You Migrate

### Goal

Use Copilot to separate low-risk modernization opportunities from behavior-sensitive changes before asking it to rewrite code.

### Steps

1. keep the legacy service and DTO open together
2. start in Chat or Plan mode
3. ask the prompt below
4. review whether Copilot distinguishes mechanical changes from behavior-sensitive ones

### Prompt

```text
Using #file on LegacyCarePlanSnapshotService and LegacyCarePlanSnapshot, analyze this module-03 slice as a Java modernization candidate.
1. list the highest-value Java 8 style improvements for Java 21
2. separate mechanical changes from behavior-sensitive changes
3. propose the first migration step that should happen before any broad rewrite
Keep the answer concrete and tied to these files.
```

### Checkpoint

The response should identify specific targets such as the mutable DTO, old-style `instanceof` checks, and opportunities to simplify branching without promising a broad redesign.

## Exercise 2: Test Before You Migrate

### Goal

Create a safety net for the current behavior before the modernization pass.

### Steps

1. switch to Agent mode
2. paste the test-generation prompt below as a fresh Agent prompt; do not just say "add tests"
3. keep the test scope tight and behavior-focused
4. run the backend tests if time allows

### Expected Generated File

- `backend/src/test/java/com/workshop/petcareops/modernization/LegacyCarePlanSnapshotServiceTest.java`

### Prompt

```text
Create a JUnit 5 test class for LegacyCarePlanSnapshotService that locks in the current behavior before modernization.
Cover:
- same-day follow-up with medication and high-stress flags
- next-day preventive follow-up
- monitor-only visit with no treatment summary
Constraints:
- do not use Spring context
- keep assertions specific
- keep the test file compact and readable
```

### Checkpoint

You should end with a small test class that proves today’s behavior before any migration starts.

## Exercise 3: Modernize With Version Targets

### Goal

Use Agent mode to apply a narrow modernization pass instead of a vague “clean this up” rewrite.

### Steps

1. keep the conversation open so the assessment and tests stay in context
2. ask Agent to modernize the service and DTO from Java 8 style to Java 21
3. paste the modernization prompt again in full when you switch into this step instead of using shorthand from the prior test step
3. review the plan before the code is applied
4. rerun the tests if the trainer does so live

### Prompt

```text
Modernize LegacyCarePlanSnapshotService and LegacyCarePlanSnapshot from Java 8 style to Java 21.
Use a record where it helps, replace old instanceof checks with pattern matching, preserve behavior, and keep the change easy to review.
Show the plan first, then apply the changes.
```

### Checkpoint

The result should look more modern and more compact, but it should still be recognizably the same behavior.

## Exercise 4: Optional React Stretch

### Goal

Reuse the same Assess -> Plan -> Execute pattern on a frontend modernization example.

### Steps

1. open `workshop-assets/starter/module-03/LegacyOwnerTimeline.tsx`
2. ask Copilot to assess the class component before changing it
3. if time allows, switch to Agent and paste the stretch conversion prompt as a fresh Agent prompt
4. keep this as stretch only; the main module succeeds without running the frontend

### Prompt

```text
Using #file on workshop-assets/starter/module-03/LegacyOwnerTimeline.tsx, assess this class component for modernization.
List the lifecycle and state-management changes needed to convert it to a functional component with hooks while preserving behavior.
```

### Stretch Prompt

```text
Convert this class component to a functional React component using hooks.
Preserve the current filtering behavior and keep the result in TypeScript.
```

### Checkpoint

The room should see that the same prompt discipline works across backend and frontend modernization tasks.

## If You Get Stuck

- ask for the assessment only before asking for code changes
- keep the modernization target pinned to Java 8 -> Java 21 or class component -> hooks
- reduce the task to one file and one migration step if Agent starts broadening the scope
- recreate the branch from `module-03-start` if your workspace drifts too far
- if you switch modes, restate the generation or modernization prompt in full instead of using shorthand

## Key Takeaways

- assess first, migrate second is the control pattern that makes Copilot modernization trustworthy
- version-pinned prompts produce much better migrations than vague modernization requests
- characterization tests are the practical safety net for AI-assisted migration work
- the same workflow transfers cleanly from Java modernization to React modernization
# Module 04.2: Refactoring With GitHub Copilot

## What You Will Practice

- using a persona prompt to get a better refactoring plan
- narrowing Copilot to one small refactoring at a time
- reviewing refactoring output before accepting it
- generating characterization tests to validate the result

## Module Focus

The goal is not to teach refactoring theory. The goal is to make Copilot feel like a practical refactoring partner on real code.

## IDE Notes

- VS Code: use Chat or Plan for the initial smell analysis, then switch to Agent quickly for the actual refactoring and test steps.
- IntelliJ: use Copilot Chat for the smell analysis; if Plan or Agent controls differ in your plugin version, keep the same prompt flow and follow the trainer for the exact edit/apply path.

## Mode Guidance

- exercise 1: Chat or Plan
- exercise 2: Agent
- exercise 3: Agent
- exercise 4: Agent

## Before You Start

- open the `copilot-workshop-foundations` repo
- start from a clean branch: `git switch -c workshop/module-04-2 module-04-2-start`
- if your workspace drifts too far, create a new branch from `module-04-2-start` or use a fresh clone
- open these files side by side:
  - `backend/src/main/java/com/workshop/petcareops/followup/OwnerReminderDraftService.java`
  - `backend/src/main/java/com/workshop/petcareops/followup/OwnerReminderDraft.java`
  - `.github/copilot-instructions.md`
- if you run tests locally, use the backend Maven wrapper from the `backend/` folder

## Exercise 1: Ask For Analysis First

### Goal

Use a persona prompt to get a concrete refactoring plan instead of vague cleanup advice.

### Steps

1. keep `OwnerReminderDraftService.java` open by itself first
2. ask Copilot for a prioritized smell analysis
3. review whether the response names concrete issues in this file
4. do not refactor yet; first decide which issue is the best low-risk target

### Prompt

```text
Using #file, act as a senior software architect specializing in Java refactoring.
Analyze OwnerReminderDraftService and give me:
1. the top code smells by severity
2. the first refactoring you would do
3. the constraints needed to preserve behavior
Keep the response concrete and tied to this file.
```

### Checkpoint

The response should mention real issues such as mixed responsibilities, nested conditionals, or repeated channel-handling logic.

## Exercise 2: Refactor One Thing, Not Everything

### Goal

Apply one small refactoring with clear constraints instead of asking for a broad rewrite.

### Steps

1. choose the single highest-value issue from the analysis
2. ask Copilot to preserve behavior and keep the public API unchanged
3. review the plan before accepting the code changes
4. stop after one narrow pass

### Prompt

```text
Refactor only the highest-value issue in OwnerReminderDraftService.
Preserve behavior, keep the public API unchanged, do not add dependencies, and prefer small extract-method changes over redesign.
Show the plan first, then apply the code changes.
```

### Checkpoint

The service should become easier to read without turning into a redesigned feature.

## Exercise 3: Make One More Low-Risk Improvement

### Goal

Practice a multi-turn refactoring conversation instead of treating the first answer as final.

### Steps

1. review the updated file
2. ask for one additional low-risk improvement only
3. compare the second change with the first one
4. stop if Copilot starts broadening the scope again

### Prompt

```text
Review the updated OwnerReminderDraftService again.
Suggest one additional low-risk refactoring that improves readability without changing behavior.
Then apply only that change.
```

### Checkpoint

The second pass should be a small readability improvement, not a major redesign.

## Exercise 4: Validate With Characterization Tests

### Goal

Use Copilot to create a safety net for the refactored code.

### Steps

1. ask for 2-3 characterization tests that lock in the current behavior
2. review whether the assertions are specific enough to prove behavior
3. run the backend tests if time allows
4. keep the generated tests compact and behavior-focused

### Prompt

```text
Generate 2-3 JUnit 5 characterization tests for OwnerReminderDraftService that lock in the current behavior.
Cover:
- urgent follow-up with email
- routine follow-up with SMS
- monitor-only flow with no treatment summary
Constraints:
- do not introduce Spring context
- do not add dependencies
- keep assertions specific
```

### Checkpoint

You should end with a narrower, cleaner service and tests that prove key paths still behave the same way.

## If You Get Stuck

- ask Copilot for the smell analysis only, not the code change yet
- reduce the refactor to one concern such as helper extraction or conditional cleanup
- compare the result against the checkpoint before accepting the change
- create a new branch from `module-04-2-start` or use a fresh clone if your workspace drifts too far

## Key Takeaways

- persona prompts produce much stronger refactoring analysis than vague cleanup requests
- smaller refactoring passes are easier to review and trust
- multi-turn approval beats one-shot rewriting
- characterization tests are a practical validation step for Copilot-assisted refactoring
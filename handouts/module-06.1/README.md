# Module 06.1: Code Quality And PR Reviews

## What You Will Practice

- reviewing changed files with Copilot before asking it to fix anything
- switching from a review pass to an Agent fix pass without losing context
- adding focused regression tests after a review-driven fix
- using a reusable review prompt file instead of retyping the same review rules

## Module Focus

The goal is to make Copilot feel like a practical review partner on a realistic change set, not just a code generator.

## IDE Notes

- VS Code: start in Chat for the review pass, then switch to Agent quickly for the fix and test steps; when you switch, paste the next fix or test prompt again as a fresh Agent prompt; MCP is stretch only if it is already configured.
- IntelliJ: use Copilot Chat for the review pass and the same prompts for the fix and test steps; if Agent or MCP controls differ in your plugin version, follow the trainer for the exact UI path and keep the prompt flow the same.

## Mode Guidance

- exercise 1: Chat first, or Plan only if it stays analysis-only
- exercise 2: Agent
- exercise 3: Agent
- exercise 4: Chat for the prompt-file step; MCP is optional stretch

## Before You Start

- open the `copilot-workshop-foundations` repo
- make sure you have the tags and remote branches: `git fetch --all --tags`
- create a disposable local review branch: `git switch -c workshop/module-06-1-review review/module-06-1-candidate`
- keep this handout open first, or use a second clone or worktree on `main`, because the prepared review branch predates the public `handouts/` folder
- the `FollowUpDigest*` review files appear after you switch to the prepared review branch; they are not part of `main`
- if you need the clean baseline for comparison, keep `module-06-1-start` available locally
- open these files side by side:
  - `.github/prompts/review-quality.prompt.md`
  - `backend/src/main/java/com/workshop/petcareops/review/FollowUpDigestController.java`
  - `backend/src/main/java/com/workshop/petcareops/review/FollowUpDigestService.java`

## Exercise 1: Review The Changed Files First

### Goal

Ask Copilot for ranked review findings before asking it to fix anything.

### Steps

1. keep the controller and service open together
2. stay in Chat for the first pass
3. ask the prompt below
4. review whether the findings are concrete, ranked, and tied to the changed files

### Prompt

```text
Using #file on FollowUpDigestController.java and FollowUpDigestService.java, review this change like a pull request.
Rank the top findings first and categorize each as Security, Reliability, Maintainability, or Best Practices.
Keep the review concrete and tied to the changed code.
```

### Checkpoint

The response should identify specific issues in the changed files, not generic code review advice.

## Exercise 2: Switch To Agent For The Fix

### Goal

Move quickly from the review findings to a small, reviewable fix pass.

### Steps

1. keep the same conversation open so the findings stay in context
2. switch to Agent mode
3. paste the prompt below again as a fresh Agent prompt instead of saying "fix those findings"
4. ask for the top 1-2 issues to be fixed, not a redesign
5. review the plan before accepting the edits

### Prompt

```text
Fix the top 2 review findings in FollowUpDigestController and FollowUpDigestService.
Keep the endpoint small, remove risky patterns, and keep the changes easy to review.
Explain the plan first, then apply the edits.
```

### Checkpoint

The edits should stay narrow and should map clearly back to the review findings.

## Exercise 3: Add Focused Regression Tests

### Goal

Use Agent to add a small safety net after the review-driven fix.

### Steps

1. stay in Agent mode
2. if you open a fresh Agent run for the tests, paste the prompt below again in full
3. ask for focused tests that prove the fix
4. keep the tests small and behavior-specific
5. run the backend tests if the trainer does so live

### Prompt

```text
Add focused JUnit 5 tests for FollowUpDigestService that prove null handling, channel selection, and internal-note behavior are explicit and safe.
Do not introduce Spring context.
```

### Checkpoint

You should end with a small test safety net tied directly to the highest-value fixes.

## Exercise 4: Reuse The Review Standard

### Goal

Make the review workflow repeatable instead of re-explaining the same rules every time.

### Steps

1. open `.github/prompts/review-quality.prompt.md`
2. stay in Chat for this step unless your trainer asks you to edit in Agent
3. ask Copilot to tighten the prompt file for this repo
4. if your setup has GitHub MCP already working, follow the trainer for the stretch workflow

### Prompt

```text
Tighten this review-quality prompt file for this repo.
Keep it short and reusable, and make sure it enforces Security, Reliability, Maintainability, and Best Practices findings in that order.
```

### Checkpoint

The result should be a shorter, more reusable review checklist that still fits this repo.

## If You Get Stuck

- restate the exact files and ask for ranked findings only
- reduce the fix pass to the top 1-2 findings
- recreate your local review branch from `review/module-06-1-candidate` if the workspace drifts too far
- treat MCP as optional stretch, not as the step that must work for the module to succeed
- when you move into Agent, paste the current fix or test prompt again in full instead of using shorthand

## Key Takeaways

- a narrow review prompt beats a broad "review everything" request
- the Review > Fix > Test chain works best when it stays in one conversation
- Agent mode is strongest after the findings are already prioritized
- reusable prompt files make review quality more consistent across a team
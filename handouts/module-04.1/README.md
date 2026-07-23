# Module 04.1: Testing And Unit Test Generation

## What You Will Practice

- using a structured prompt to generate better unit tests
- using an existing source file and existing test file as context
- refining generated tests through follow-up prompts instead of one-shot retries
- reviewing Copilot-generated tests before trusting them

## Module Focus

The goal is not to teach testing theory. The goal is to make Copilot feel like a useful testing partner instead of a boilerplate generator.

## IDE Notes

- VS Code: use Ask for the prompt comparison, then switch to Agent quickly once you want Copilot to add or refine tests; when you switch, paste the next testing prompt again as a fresh Agent prompt.
- IntelliJ: use Copilot Ask for the same prompt flow; if Agent mode differs in your plugin version, keep the analysis in Ask and follow the trainer for the exact apply flow.

## Mode Guidance

- exercise 1: Ask
- exercise 2: Agent
- exercise 3: Agent
- exercise 4: Ask first, then Agent only if you want Copilot to apply more test changes

### When to Plan First vs. Go Straight to Agent
- Single-file, narrow-scope tasks bundle plan-then-apply into one Agent prompt below. Multi-file or ambiguous-scope tasks should stay in Plan mode until the full sequence is approved, then switch to Agent to execute.

### A note on language
- "Plan" shows up two ways here. Sometimes it means the IDE's literal Plan mode — a separate mode that produces a plan you approve before anything runs. Other times, especially inside a prompt ("show the plan first, then apply") or in casual phrasing, it just means asking Copilot to lay out its intent before acting, which can happen inside Ask or Agent without ever switching modes. Every exercise in this module stays narrow enough that literal Plan mode is never required — that changes in the refactoring module.

## Before You Start

- open the `copilot-workshop-foundations` repo
- start from a clean branch: `git switch -c workshop/module-04-1 module-04-1-start`
- open these files side by side:
  - `backend/src/main/java/com/workshop/petcareops/followup/FollowUpRecommendationService.java`
  - `backend/src/test/java/com/workshop/petcareops/followup/FollowUpRecommendationServiceTest.java`
  - `.github/copilot-instructions.md`
- if you run tests locally, use the backend Maven wrapper from the `backend/` folder

## Exercise 1: Use A Structured Prompt

### Goal

See how a structured testing prompt produces better output than a vague request.

### Steps

1. start with a vague request and inspect the result
2. rerun with the structured prompt below
3. compare the generated test names, assertions, and mocking behavior

### Vague Prompt

```text
Write tests for FollowUpRecommendationService.
```

### Structured Prompt

```text
You are a Test Strategy Agent.

Scenario:
Generate JUnit 5 and Mockito tests for FollowUpRecommendationService in this repo.

Pattern:
Follow the style already used in FollowUpRecommendationServiceTest.

Expectations:
- cover priority selection, follow-up window selection, and ReminderChannelAdvisor interaction
- use clear method names
- keep assertions specific

Constraints:
- stay in the existing test class
- do not introduce Spring context
- keep the test file compact and readable
```

### Checkpoint

The structured prompt should produce more targeted tests and better mocking than the vague prompt.

## Exercise 2: Coverage Optimizer Pattern

### Goal

Use Copilot to identify what the current test file still misses.

### Steps

1. keep the source file and existing test file open
2. if you switch into Agent for this step, paste the prompt below again as a fresh Agent prompt
3. review the uncovered paths Copilot identifies
4. generate only 1-2 missing tests, not a huge rewrite

*Since the scope is a single file with a known test class, go straight to Agent Mode*

### Prompt

```text
Using #codebase, act as a Coverage Optimizer for FollowUpRecommendationService.
Compare the source file and the existing FollowUpRecommendationServiceTest file.
List the highest-value missing test cases first, then generate the next 2 tests that would improve confidence the most.
```

### Checkpoint

The response should identify real gaps in the current test file instead of suggesting generic testing advice.

## Exercise 3: Multi-Turn Refinement

### Goal

Improve generated tests through follow-up prompts instead of starting over.

### Steps

1. choose one generated test that still feels thin
2. ask Copilot to strengthen it with a follow-up prompt
3. if you open a new Agent run for the refinement, paste the refinement prompt again in full
4. focus on one refinement at a time: null handling, boundary conditions, or collaborator verification

*Single file, one refinement at a time — no separate plan needed.*

### Prompt

```text
Refine the generated tests for FollowUpRecommendationService.
Add one null-handling test and one non-urgent follow-up scenario.
Keep the assertions specific and verify ReminderChannelAdvisor only where it adds confidence.
```

### Checkpoint

The refined output should be more precise without replacing the whole test class.

## Exercise 4: Review Before You Run

### Goal

Build the habit of reviewing AI-generated tests before treating them as useful coverage.

### Steps

1. inspect whether the tests assert meaningful values
2. check whether collaborator interactions are verified only when relevant
3. if you move from Ask review into Agent to apply one more change, paste that follow-up prompt again as a fresh Agent prompt
4. run the tests if time allows

*Review stays in Ask; only switch to Agent if you're applying a change, and even then it's one small edit.*

### Prompt

```text
Review these generated FollowUpRecommendationService tests like a senior engineer.
Point out any weak assertions, redundant mocks, or cases where the test could pass without proving much.
```

### Checkpoint

You should end with a smaller set of stronger tests, not just a larger file.

## Stretch

- ask Agent mode to add the tests and run them
- if your IDE supports it, let Agent fix a small failing test and rerun
- take your structured prompt from Exercise 1 and save it as .github/prompts/generate-unit-tests.prompt.md. Then fix copilot-instructions.md — add the testing standards this workshop just covered (assertion style, mocking rules, never weaken a failing test)

## If You Get Stuck

- reduce the request to one missing scenario
- point Copilot at both the source file and existing test file again
- ask for a test plan before asking for code
- if you switch into Agent, paste the current testing or refinement prompt again in full instead of using shorthand

## Key Takeaways

- testing prompts improve sharply when you name scenario, expectations, and constraints
- source file plus existing tests is powerful context for Copilot
- multi-turn refinement usually beats rewriting the whole test class
- generated tests are only useful if the assertions actually prove behavior

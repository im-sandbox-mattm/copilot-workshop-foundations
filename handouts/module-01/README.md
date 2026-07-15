# Module 01: Beyond Autocomplete

## What You Will Practice

- using Copilot Chat to understand a codebase quickly
- using Agent mode for a small, well-scoped change
- seeing how `#codebase` and `copilot-instructions.md` change the quality of results
- building the mental model of where this session stops and later modules go deeper

## Module Focus

You are not expected to cover every advanced feature yet. The goal is to leave with a stronger mental model and a few concrete wins you can repeat immediately.

## IDE Notes

VS Code:

- use the Copilot Chat panel for Ask and Agent mode
- use inline chat for targeted in-file edits
- `#codebase` is the main unlock for codebase-aware questions

IntelliJ:

- use the Copilot tool window for chat
- use intention actions for in-editor Copilot help
- Agent mode support may be more limited than VS Code, so following the trainer is enough if the exact UI differs

## Mode Guidance

- exercise 1: Chat
- exercise 2: Agent
- exercise 3: Chat first, then Agent only if you want to apply the change live

## Before You Start

- open the `copilot-workshop-foundations` repo
- if you want to make code changes locally, the optional clean start command is `git switch -c workshop/module-01 module-01-start`
- sign in to GitHub Copilot Chat in your IDE
- open these files now so you do not need to wait for the trainer to name them:
	- `backend/src/main/java/com/workshop/petcareops/dashboard/ClinicDashboardController.java`
	- `backend/src/main/java/com/workshop/petcareops/dashboard/ClinicDashboardService.java`
	- `frontend/src/App.tsx`
	- `.github/copilot-instructions.md`
	- `workshop-assets/solution/copilot-instructions.module-02.md`
- follow the trainer pace; checkpoints are there if you need to catch up

## Exercise 1: Ask The Codebase A Real Question

### Goal

Use Chat with `#codebase` so Copilot answers from this repo instead of giving a generic explanation.

### Steps

1. open `backend/src/main/java/com/workshop/petcareops/dashboard/ClinicDashboardController.java`, `backend/src/main/java/com/workshop/petcareops/dashboard/ClinicDashboardService.java`, `frontend/src/App.tsx`, and `README.md`
2. switch to Chat mode
3. ask the prompt below
4. review whether the answer points to actual files and a real request flow

### Prompt

```text
Using #codebase, explain how dashboard appointment data flows from the backend to the frontend in this repo. Keep the answer concise and point to the key files.
```

### Checkpoint

The answer mentions real files in the repo and explains a believable end-to-end flow.

Bonus check if time allows:

- ask why `#codebase` matters compared with asking the same question without it

## Exercise 2: Compare Chat And Agent

### Goal

Feel the difference between asking Copilot to explain something and asking it to build something.

### Steps

1. stay on the same repo slice and keep `frontend/src/App.tsx` open
2. in Chat mode, ask the planning prompt below and wait for the explanation only
3. switch to Agent mode
4. repeat the implementation request as a fresh Agent prompt; do not just say "do that"
5. if your IDE does not expose the same Agent controls, watch the trainer and focus on the result and prompt shape

### Chat Prompt

```text
Using #codebase and #file:frontend/src/App.tsx, explain which file or files would need to change to add a small follow-up badge next to the existing appointment status pill for visits that require follow-up. Do not implement it yet.
```

### Agent Prompt

```text
Add a small follow-up badge next to the existing appointment status pill for visits that require follow-up. Reuse current styling patterns and do not change backend contracts.
```

### Checkpoint

Agent proposes or makes a small, focused change instead of giving only explanation.

## Exercise 3: See The Impact Of Instructions

### Goal

Compare Copilot output before and after improving repository instructions.

### Steps

1. make sure `.github/copilot-instructions.md` and `workshop-assets/solution/copilot-instructions.module-02.md` are both open
2. ask for a small backend addition using the starting `.github/copilot-instructions.md`
3. let the trainer reveal or copy in the stronger instructions from `workshop-assets/solution/copilot-instructions.module-02.md`
4. rerun the exact same prompt
4. compare naming, structure, and consistency in the result
5. note that the instructions file is version-controlled, so a cloned repo can inherit those conventions automatically

### Files For This Exercise

- weak instructions: `.github/copilot-instructions.md`
- stronger instructions: `workshop-assets/solution/copilot-instructions.module-02.md`

### Prompt

```text
Create a backend DTO and service method for returning a compact appointment summary list for the dashboard. Follow the patterns already used in this project.
```

### Checkpoint

The second result should align more closely with the project conventions than the first.

## If You Get Stuck

- ask the trainer for the checkpoint prompt
- reduce the task scope to one file or one change
- reopen the same files the trainer is using so your context matches
- if you switch from Chat to Agent, repeat the implementation prompt in full instead of using shorthand like "do that"

## Key Takeaways

- `#codebase` is a major quality upgrade for codebase-aware questions
- Chat is best for understanding and Agent is best for scoped execution
- a short `copilot-instructions.md` can noticeably improve Copilot output
- this session is a preview of the four-layer model, not the deep dive on every advanced feature
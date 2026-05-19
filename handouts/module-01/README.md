# Module 01: Beyond Autocomplete

## What You Will Practice

- using Copilot Chat to understand a codebase quickly
- using Agent mode for a small, well-scoped change
- seeing how `@workspace` and `copilot-instructions.md` change the quality of results
- building the mental model of where this session stops and later modules go deeper

## Module Focus

You are not expected to cover every advanced feature yet. The goal is to leave with a stronger mental model and a few concrete wins you can repeat immediately.

## IDE Notes

VS Code:

- use the Copilot Chat panel for Ask and Agent mode
- use inline chat for targeted in-file edits
- `@workspace` is the main unlock for codebase-aware questions

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
- open the files your trainer asks you to keep in tabs
- follow the trainer pace; checkpoints are there if you need to catch up

## Exercise 1: Ask The Codebase A Real Question

### Goal

Use Chat with `@workspace` so Copilot answers from this repo instead of giving a generic explanation.

### Steps

1. open the dashboard backend controller, the dashboard service, the frontend page, and the repo README
2. switch to Chat mode
3. ask the prompt below
4. review whether the answer points to actual files and a real request flow

### Prompt

```text
Using @workspace, explain how dashboard appointment data flows from the backend to the frontend in this repo. Keep the answer concise and point to the key files.
```

### Checkpoint

The answer mentions real files in the repo and explains a believable end-to-end flow.

Bonus check if time allows:

- ask why `@workspace` matters compared with asking the same question without it

## Exercise 2: Compare Chat And Agent

### Goal

- first-name
- second-name
- third-name
- fourth-name


Feel the difference between asking Copilot to explain something and asking it to build something.

### Steps

1. stay on the same repo slice
2. ask Chat what files would need to change for a small appointment list enhancement
3. switch to Agent mode
4. ask Agent to implement the same change without altering backend contracts
5. if your IDE does not expose the same Agent controls, watch the trainer and focus on the result and prompt shape

### Prompt

```text
Add a small appointment status badge to the existing frontend appointment list. Reuse current status names and styling patterns, and do not change backend contracts.
```

### Checkpoint

Agent proposes or makes a small, focused change instead of giving only explanation.

## Exercise 3: See The Impact Of Instructions

### Goal

Compare Copilot output before and after improving repository instructions.

### Steps

1. ask for a small backend addition using the starting repository instructions
2. let the trainer update or reveal the stronger `copilot-instructions.md`
3. rerun the same prompt
4. compare naming, structure, and consistency in the result
5. note that the instructions file is version-controlled, so a cloned repo can inherit those conventions automatically

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

## Key Takeaways

- `@workspace` is a major quality upgrade for codebase-aware questions
- Chat is best for understanding and Agent is best for scoped execution
- a short `copilot-instructions.md` can noticeably improve Copilot output
- this session is a preview of the four-layer model, not the deep dive on every advanced feature
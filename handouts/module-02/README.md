# Module 02: Context Engineering

## What You Will Practice

- improving the same request by changing prompt quality
- shaping Copilot context with open tabs, `@workspace`, and file references
- tightening repository instructions so future output is more consistent
- using a few high-value chat tools without turning the session into a feature tour

## Module Focus

The goal is to leave with a more advanced feeling of control over Copilot output, not to memorize every chat feature.

## IDE Notes

- VS Code: use Chat for the prompt and context comparisons, then switch to Agent when you want Copilot to rewrite the instructions file.
- IntelliJ: keep the same files open and use Copilot Chat for the comparison steps; if `@workspace` or Agent surfaces differ in your plugin version, use explicit file references and follow the trainer for the exact UI.

## Mode Guidance

- exercise 1: Chat
- exercise 2: Chat
- exercise 3: Agent
- exercise 4: Chat or Agent depending on whether you want only analysis or a concrete rewrite

## Before You Start

- open the `copilot-workshop-foundations` repo
- start from a clean branch: `git switch -c workshop/module-02 module-02-start`
- open the dashboard service, the frontend dashboard page, and `.github/copilot-instructions.md`
- keep the trainer pace; each exercise has a visible checkpoint

## Exercise 1: Improve A Weak Prompt

### Goal

See how quickly prompt quality changes the usefulness of Copilot output.

### Steps

1. ask Copilot the weak prompt first
2. review what is vague or generic in the answer
3. rerun the task with the stronger prompt
4. compare structure, specificity, and relevance

### Weak Prompt

```text
Make the dashboard better.
```

### Strong Prompt

```text
Using @workspace, propose a small improvement to the clinic dashboard in this repo. Keep it to one frontend change, do not change backend contracts, reuse current naming and styling patterns, and explain which files would need to change.
```

### Checkpoint

The stronger prompt should reference specific files, a smaller scope, and clearer implementation boundaries.

## Exercise 2: Add Better Context On Purpose

### Goal

Use open tabs and explicit references to improve Copilot's reasoning.

### Steps

1. keep the dashboard service, the frontend dashboard page, and `.github/copilot-instructions.md` open
2. ask the prompt below with `@workspace`
3. if the answer is still generic, add a `#file:` reference to one of the open files
4. note how the answer changes when the context is more directed

### Prompt

```text
Using @workspace, compare the backend dashboard response shape with the frontend view that renders it. Identify one place where tighter naming or typing would make future Copilot edits safer.
```

### Checkpoint

The answer should point to the actual API shape and the actual frontend consumer, not just general advice.

## Exercise 3: Upgrade Repository Instructions

### Goal

Improve the repo-wide instructions so the next Copilot response better matches team expectations.

### Steps

1. review the current `.github/copilot-instructions.md`
2. ask Copilot to suggest 5-8 tighter rules for this repo
3. keep the rules short, directive, and repo-specific
4. compare your result with the stronger solution file if the trainer reveals it

### Prompt

```text
Rewrite this repository's copilot-instructions.md for the pet-care operations app in this workspace. Keep it under 12 bullets. Include backend and frontend conventions, scope control, and 2-3 explicit anti-patterns to avoid.
```

### Checkpoint

The new instructions should be more specific than the starter file and should clearly fit this repo.

## Exercise 4: Few-Shot Or Step-By-Step Prompting

### Goal

Practice one advanced prompting technique that can be reused daily.

### Steps

1. choose one of the prompts below
2. run it against the dashboard slice
3. compare the result with a simpler one-shot request

### Few-Shot Prompt

```text
Follow the naming and response patterns already used in this repo. Using the existing dashboard DTOs as examples, suggest a compact summary shape for overdue follow-up items.
```

### Step-By-Step Prompt

```text
Let's think through this step by step for the dashboard flow in this repo:
1. identify the current inputs and outputs
2. identify one missing edge case or usability gap
3. propose the smallest safe change
4. list the files that would need to change
```

### Checkpoint

The response should be more structured and easier to act on than a vague one-shot prompt.

## Quick Reference

- `@workspace`: ask codebase-aware questions
- `#file:path`: point at one file directly
- `/explain`: understand a selected block
- `/fix`: ask for a targeted fix
- `/tests`: generate tests for a selected slice

## If You Get Stuck

- reopen the same files the trainer is using
- reduce the scope to one file and one change
- ask the trainer for the stronger prompt version instead of retrying vague prompts repeatedly

## Key Takeaways

- prompt quality is often the fastest lever
- context quality is usually the highest-leverage team skill
- repo instructions should be short, specific, and version-controlled
- better Copilot output is usually the result of better setup, not better luck
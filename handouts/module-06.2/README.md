# Module 06.2: Token Optimization

## What You Will Practice

- spotting token waste in prompts and always-on instructions
- rewriting a bloated instructions file into a smaller, stronger version
- separating repo-wide guidance from path-specific instructions and reusable prompt files
- deciding when a repeated task should move to a script or hook instead of staying in Copilot prompts

## Module Focus

The goal is to make Copilot feel sharper and faster by reducing noise, not to turn the module into a billing lecture.

## IDE Notes

- VS Code: start in Chat or Plan for the audit, then switch to Agent quickly for the rewrite steps; if Chat Debug View is available, use it for before-and-after comparison.
- IntelliJ: use Copilot Chat for the same audit and rewrite prompts; if you do not have equivalent token counters, compare context size and response quality instead of exact token numbers.

## Mode Guidance

- exercise 1: Chat or Plan
- exercise 2: Agent
- exercise 3: Agent
- exercise 4: Chat

## Before You Start

- open the `copilot-workshop-foundations` repo
- start from a clean branch: `git switch -c workshop/module-06-2 module-06-2-start`
- open these files side by side:
  - `.github/copilot-instructions.md`
  - `workshop-assets/starter/copilot-instructions.module-06-2.verbose.md`
  - `workshop-assets/solution/copilot-instructions.module-06-2-optimized.md`
  - `workshop-assets/solution/backend-java.module-06-2.instructions.md`
  - `workshop-assets/solution/frontend-react.module-06-2.instructions.md`
  - `workshop-assets/solution/review-findings.module-06-2.prompt.md`
  - `workshop-assets/solution/format-check.module-06-2.sh`

## Exercise 1: Audit Waste Before Editing

### Goal

Identify the biggest token waste before trying to optimize anything.

### Steps

1. keep the live `.github/copilot-instructions.md` and the verbose starter file open together
2. stay in Chat or Plan for the first pass
3. ask the prompt below
4. review whether the findings are concrete and ranked

### Prompt

```text
Compare this repo's live copilot-instructions.md with the verbose starter instructions file.
Identify the biggest token waste first, using the categories always-on, on-demand, and zero-cost.
Keep the findings concrete and action-oriented.
```

### Checkpoint

The response should identify verbose always-on text, missing path-specific separation, and repeated deterministic work that does not belong in prompts.

## Exercise 2: Rewrite The Repo-Wide Instructions

### Goal

Compress the expensive always-on instructions into a smaller, higher-signal version.

### Steps

1. switch to Agent mode
2. ask for a concise rewrite of the verbose starter instructions
3. keep the output in short bullets
4. compare the result with the optimized solution file

### Prompt

```text
Rewrite the verbose starter instructions for this repo into a token-efficient version.
Keep it concise, bullet-based, and focused on the highest-value repo-wide rules.
Do not include path-specific rules that only apply to backend or frontend files.
```

### Checkpoint

The rewrite should be noticeably smaller and more directive than the verbose starter file.

## Exercise 3: Move Specific Rules Out Of Always-On Context

### Goal

Split specific guidance into path-specific instructions and reusable prompt files.

### Steps

1. stay in Agent mode
2. ask for backend and frontend instruction examples plus a reusable prompt template
3. compare the result with the prepared solution assets
4. note how the split reduces always-on noise

### Prompt

```text
Create path-specific instruction examples for backend Java files and frontend React files in this repo.
Also create a reusable review prompt template for repeated file reviews.
Keep the always-on instructions small and move specific guidance into these on-demand assets.
```

### Checkpoint

You should end with a clear split between repo-wide rules, file-specific rules, and reusable prompt templates.

## Exercise 4: Decide What Should Become A Script Or Hook

### Goal

Identify deterministic tasks that should stop consuming Copilot prompt tokens.

### Steps

1. switch back to Chat for the reasoning step
2. open the sample script
3. ask the prompt below
4. compare the answer with the repetitive tasks you currently ask Copilot to do

### Prompt

```text
Review the sample format-check script and identify which repetitive Copilot tasks in this repo should move to deterministic scripts or hooks instead.
Keep the answer specific to this workshop repo.
```

### Checkpoint

The answer should name specific deterministic tasks like formatting, linting, or file hygiene rather than generic automation advice.

## If You Get Stuck

- ask for the waste audit only before asking for rewrites
- constrain the rewrite to short bullets and named file targets
- compare the result with the prepared solution assets instead of guessing what "optimized" should mean
- if token counters are unavailable in your IDE, compare context size and output quality instead

## Key Takeaways

- the biggest waste is usually noisy always-on context, not the prompt itself
- smaller repo-wide instructions usually improve quality as well as speed
- path-specific instructions and prompt files keep context focused
- deterministic tasks are better handled by scripts or hooks than repeated prompts
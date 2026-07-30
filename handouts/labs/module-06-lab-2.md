# Lab 2: Cost-Aware Review Standardization

**No GitHub account, GitHub-hosted repository, or Copilot license is required to complete this lab.** Both the baseline and the optimized review run are prepared and supplied. Your hands-on work is restructuring real instruction, prompt, skill, and script files yourself and reasoning about what belongs where.

## What You Will Practice

- inspecting a deliberately inefficient review workflow and identifying where the waste actually is
- deciding what belongs in repo-wide instructions, path-specific instructions, a prompt file, a skill, a deterministic script, or nowhere at all
- comparing a prepared inefficient baseline against a prepared optimized run
- reading Agent Debug Log / Cache Explorer telemetry instead of guessing at cost
- deciding whether an optimization is actually worth adopting — not just whether it's cheaper

## Module Focus

Both the baseline and the optimized run in this lab are **supplied to you** — that isn't a shortcut, it's how a real controlled comparison has to work. A genuine before-and-after test requires holding everything constant except the one thing being tested, and building both halves live in one session would contaminate exactly that. Your actual work is Step 2: deciding how you'd restructure the baseline's context, and editing real files to do it.

## Before You Start

This lab reuses the same branch as Lab 1.

**If you already did Lab 1:** stay on your `workshop/lab1-review` branch — nothing more to do.

**If you're starting with Lab 2 directly:**

```bash
# If you haven't cloned yet:
git clone https://github.com/im-sandbox-mattm/copilot-workshop-foundations.git
cd copilot-workshop-foundations

# If you already have it cloned, cd into it instead, then:
git fetch --tags

# Everyone runs this:
git switch -C module-06 module-06-2-lab
```

> **What that last command does:** `git switch -C <new-branch-name> <starting-point>` creates a branch named `<new-branch-name>` pointing at `<starting-point>` and switches you onto it. `workshop/lab1-review` isn't anything that needs to exist beforehand — it's just a label for your own working branch. `module-06-2-lab` is the tag it's built from, and that one does need to already exist. The capital `-C` means "create it fresh, or reset it to this point if it's already there."

Open `handouts/assets/lab2-baseline-and-comparison.md` now — it contains the full baseline package (prompt, instructions file, raw context, response, and telemetry) and the full optimized reference package that this lab works from.

## Step 1: Inspect the Baseline (supplied — read, don't generate)

Read the prepared baseline package in `handouts/assets/lab2-baseline-and-comparison.md`. Catalog what's actually being sent, without judging it yet:

- What's in the prompt itself?
- What's in the instructions file, and how much of it is relevant to *this* review?
- What raw, unfiltered content is attached (command output, whole files, prior chat history)?
- What do the credit and telemetry numbers say about where the cost actually went?

## Step 2: Restructure (the hands-on step)

Decide, for every piece of context in the baseline, where it belongs:

- Repository-wide instructions (`copilot-instructions.md`)
- Path-specific instructions (`*.instructions.md` with `applyTo`)
- A reusable prompt file
- An agent skill
- A deterministic script or test (no model involvement at all)
- Nowhere — cut it

Actually write these files — draft the trimmed `copilot-instructions.md`, the path-specific instructions file, or whatever your restructuring calls for. This is the exercise; the files don't need to run against a live Copilot session to be real work.

## Step 3: Inspect the Optimized Reference (supplied)

Read the prepared optimized package in `handouts/assets/lab2-baseline-and-comparison.md` — generated in advance under the same controlled conditions (same model, same diff, same verification commands) against a restructuring similar to what Step 2 asks you to design. Compare your own restructuring decisions against it: where did you agree, where did you diverge, and why?

## Step 4: Compare

Fill in this table using the baseline and optimized packages:

| Measure | Baseline | Optimized |
|---|---|---|
| AI credits consumed | | |
| Input tokens | | |
| Cached tokens | | |
| Output tokens | | |
| Output size (characters) | | |
| Valid findings | | |
| False positives | | |
| Evidence retained (all 6 contract fields present?) | | |
| Verification retained | | |
| Reading time (estimate from output length/structure) | | |

## Step 5: Recommend

Answer, in writing:

1. Which specific restructuring choice produced the biggest cost reduction in the optimized package?
2. Did review quality hold, improve, or degrade between baseline and optimized? Point to specific findings, not a gut feeling.
3. Would you adopt, reject, or pilot each restructuring choice individually? An optimization that saves credits but drops a real finding is not a win — say so if that's what the evidence shows.

## Optional: Caveman Comparison

A prepared baseline-vs-compressed comparison is included in `handouts/assets/lab2-baseline-and-comparison.md`'s Caveman section — use that unless a live demo is available. No participant is required to install it. Evaluate, don't just measure the size difference: did compression reduce visible output and subsequent context growth **without removing** the evidence, uncertainty, commands, rationale, or verification needed to make a review decision? Caveman's savings figures are community-reported, not a GitHub guarantee — treat them as a claim to test, not a fact to cite.

## Optional Licensed Extension

If you already have approved Copilot access, you may — after completing Steps 1–5, not instead of them — run your own restructured setup in a fresh Copilot session (holding model, IDE version, and verification commands constant, and *not* using Auto model selection, which would introduce an uncontrolled variable) and compare your live numbers against the prepared optimized reference. This is optional and not required to complete the lab.

## If You Get Stuck

- If your Step 2 restructuring differs a lot from the prepared optimized reference, that's fine — compare and discuss why in the debrief, it isn't a wrong answer.
- If you're unsure whether something belongs in instructions vs. a skill vs. a script, default to: does this need to run without any model reasoning at all? If yes, script. Does it apply to nearly everything? Instructions. Does it apply only when a specific task is happening? Skill.

## Key Takeaways

- The biggest waste is usually noisy, redundant context — not the prompt itself.
- A restructured setup should be *smaller and higher-signal*, not just differently organized.
- Never call an optimization successful because it used fewer tokens. It has to hold the quality bar too.
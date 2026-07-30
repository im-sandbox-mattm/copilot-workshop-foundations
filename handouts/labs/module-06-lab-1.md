# Lab 1: Review Quality Benchmark

**No GitHub account, GitHub-hosted repository, or Copilot license is required to complete this lab.** You will review local code and evaluate a real, prepared Copilot Code Review. GitHub-native automation is demonstrated centrally by the facilitator after this lab.

## What You Will Practice

- reviewing a real, seeded change manually, against a clock
- evaluating a real GitHub Copilot Code Review — not generating it, evaluating it
- recording every finding — yours and Copilot's — in a consistent, decision-useful format
- comparing two sources on more than finding count: evidence quality, verification quality, false positives, and time
- producing a short review artifact a real reviewer could act on

## Module Focus

The question this lab answers is not "did Copilot find a bug." It's: **which review — your own manual read, or GitHub Copilot Code Review — produced the more decision-useful findings, and what did each miss?** You are not using Copilot as the reviewer here. You are reviewing the AI reviewer: determining which of its findings are supported, which are generic or wrong, what it missed, and whether its evidence is good enough for a human to act on. Some of the seeded issues are visible directly in the diff. Others require knowing how existing code elsewhere in the app behaves — that split is deliberate, and the debrief is where it pays off.

## Scope

- Reliability
- Maintainability
- Best Practices
- Cross-cutting lenses: Behavioral Consistency (does this change agree with how the rest of the app already does the same thing?) and Test Adequacy

Security is deliberately out of scope for this lab — Workshop 4 already covered it in depth.

## Before You Start

```bash
# If you haven't cloned yet:
git clone https://github.com/im-sandbox-mattm/copilot-workshop-foundations.git
cd copilot-workshop-foundations

# If you already have it cloned, cd into it instead, then:
git fetch --tags

# Everyone runs this:
git switch -C module-06 module-06-2-lab
```

> **What that last command does:** `git switch -C <new-branch-name> <starting-point>` creates a branch named `<new-branch-name>` pointing at `<starting-point>` and switches you onto it. `workshop/lab1-review` isn't anything that needs to exist beforehand — it's just a label for your own working branch, and you can name it anything. `module-06-2-lab` is the tag it's built from, and that one does need to already exist. The capital `-C` means "create it fresh, or reset it to this point if it's already there" — so this same command works whether it's your first time running it or you need to start over.

**Build and test commands** (optional — the lab does not require running the app; use these only if you want to confirm the project still compiles):

```bash
cd backend
./mvnw compile
./mvnw test
```

**Files to open side by side:**

```
backend/src/main/java/com/workshop/petcareops/dashboard/EscalationBoardController.java
backend/src/main/java/com/workshop/petcareops/dashboard/EscalationBoardService.java
backend/src/main/java/com/workshop/petcareops/dashboard/EscalationSummaryResponse.java
backend/src/main/java/com/workshop/petcareops/dashboard/ClinicianEscalationLoad.java
```

**Prepared review output** (open when Step 3 starts, included in this checkout):

```
handouts/assets/copilot-code-review.md
```

## The Change Under Review

**Feature request:** Add a "Today's Escalations" board to the clinic dashboard — `GET /api/dashboard/escalations` — so front-desk staff can see which appointments need same-day attention without reading the full appointment list, plus a per-clinician escalation count.

**Acceptance criteria:**
- Surface every appointment that needs same-day follow-up.
- Show an escalation count per clinician.
- Reuse existing channel-selection logic where it already exists in the codebase, rather than reinventing it.

**Expected unchanged behavior:** the existing urgency and channel-selection rules used elsewhere in the app (`followup`, `modernization` packages) must still produce results consistent with this new feature. If the new feature disagrees with them, that disagreement is itself a finding.

**Changed files:** 4 new files, ~100 lines, in the `dashboard` package. No existing files modified.

## Step 1: Orient (3 minutes)

Read the feature request and acceptance criteria above, and confirm you know which four files are in scope. That's it — this step is quick on purpose.

## Step 2: Manual Review (8 minutes)

### Goal

Produce a ranked, evidence-based set of findings without any AI assistance, in a fixed window.

### Steps

1. Read the four new files.
2. For anything that looks off, decide: is this visible from these four files alone (a **local** finding), or would confirming it require knowing how one of the existing files elsewhere in the app behaves (a **relational** finding)? You don't need to go verify relational findings yourself in this step — just flag them as suspected.
3. Record every finding using the **Finding-Quality Contract** below.
4. Stop when the timer ends, even if you're not finished — record what you have. Your goal is the highest-value findings you can support, not locating every issue.

### Finding-Quality Contract

Record every finding — local or relational — with all six fields:

| Field | What it means |
|---|---|
| **Evidence** | The exact file and line/method — not a paraphrase |
| **Category** | Reliability / Maintainability / Best Practices (pick one primary category; note Behavioral Consistency or Test Adequacy as a lens if relevant) |
| **Consequence** | What actually breaks, and for whom |
| **Confidence / Status** | Certain, likely, or "needs a human to confirm" |
| **Smallest Remediation** | The minimal fix — not a redesign |
| **Verification** | The specific test or check that would prove it's fixed |

## Step 3: Evaluate the Copilot Code Review (12 minutes)

### Goal

A real GitHub Copilot Code Review already exists for this exact change — you are not generating it, you are evaluating it.

Open `handouts/assets/copilot-code-review.md`. This is the actual comment thread from GitHub Copilot Code Review, requested against a real pull request containing these four files.

For each finding it makes, apply the same rigor you just used on yourself:

- Is this supported by real evidence, or is it a vague impression?
- Is it accurate?
- Is it something a human could act on directly, or does it need rework first?
- Note anything it appears to have missed.

Record what you conclude using the same six-field contract.

## Step 4: Compare and Produce the Artifact (7 minutes)

## Comparison Matrix

Fill this in using your Step 2 notes and your Step 3 evaluation of `handouts/assets/copilot-code-review.md`.

| Measure | Manual Review | Copilot Code Review |
|---|---|---|
| Valid findings | | |
| False positives | | |
| Seeded issues missed | | |
| Overlap (found by both) | | |
| Unique valid findings | | |
| Evidence quality (specific vs. vague) | | |
| Consequence quality (concrete vs. generic) | | |
| Verification quality (real check vs. none) | | |
| Prioritization quality (ranked by real impact?) | | |
| Time required | | |

**Discussion prompts, once the table is filled in:**

1. What did manual review catch that Copilot's review missed?
2. What did Copilot's review catch that manual review missed?
3. Did Copilot state anything with more certainty than its evidence actually supported?
4. Which findings required knowing how other files in the app behave — and did either source actually catch those?
5. Which output needed the least rework before it could become a real review comment you'd post?

The assisted review is not automatically better because it produced more findings. Its output still has to survive human review before anyone acts on it.

## Optional Licensed Extension

If you already have approved Copilot access and access to the PR, you may — after completing Step 4, not instead of it — open the PR yourself, read the live comment thread, and compare it against `handouts/assets/copilot-code-review.md`. This is optional and not required to complete the lab.

## If You Get Stuck

- If the manual-review window feels short, note that — it's data for the debrief, not a failure.
- If you're unsure whether something is a local or relational finding, flag it as relational — better to over-flag than to guess.
- After this lab's debrief, you'll see how GitHub operationalizes this same review discipline at pull-request scale — that's a demonstration to watch, not something you need to reproduce.

## Key Takeaways

- A review is only as good as its evidence, consequence, confidence, remediation, and verification — not its finding count.
- Some findings are structurally invisible without the right context, regardless of who — or what — is reviewing.
- Real Copilot Code Review findings still need human judgment before anyone treats them as ground truth.
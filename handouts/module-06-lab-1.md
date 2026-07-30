# Lab 1: Review Quality Benchmark

**No GitHub account, GitHub-hosted repository, or Copilot license is required to complete this lab.** You will review local code and evaluate two prepared Copilot outputs. GitHub-native automation is demonstrated centrally by the facilitator after this lab.

## What You Will Practice

- reviewing a real, seeded change manually, against a clock
- evaluating two prepared AI review outputs — not generating them, evaluating them
- recording every finding — yours and each prepared output's — in a consistent, decision-useful format
- comparing three sources on more than finding count: evidence quality, verification quality, false positives, and time
- producing a short review artifact a real reviewer could act on

## Module Focus

The question this lab answers is not "did Copilot find a bug." It's: **which of the three review sources — your own manual read, a broad Copilot request, or a structured Copilot request — produced the most decision-useful review, and what did each miss?** You are not using Copilot as the reviewer here. You are reviewing the AI reviewer: determining which of its findings are supported, which are generic or wrong, what it missed, and whether its evidence and verification are good enough for a human to act on. Some of the seeded issues are visible directly in the diff. Others require knowing how existing code elsewhere in the app behaves — that split is deliberate, and the debrief is where it pays off.

## Scope

- Reliability
- Maintainability
- Best Practices
- Cross-cutting lenses: Behavioral Consistency (does this change agree with how the rest of the app already does the same thing?) and Test Adequacy


## Before You Start

```bash
# If you haven't cloned yet:
git clone https://github.com/im-sandbox-mattm/copilot-workshop-foundations.git
cd copilot-workshop-foundations

# If you already have it cloned, cd into it instead, then:
git fetch --tags

# Everyone runs this:
git switch -c module-06-build module-06-2-lab
```

> **What that last command does:** `git switch -C <new-branch-name> <starting-point>` creates a branch named `<new-branch-name>` pointing at `<starting-point>` and switches you onto it. `workshop/lab1-review` isn't anything that needs to exist beforehand — it's just a label for your own working branch, and you can name it anything. `module-06-lab-starter` is the tag it's built from, and that one does need to already exist. The capital `-C` means "create it fresh, or reset it to this point if it's already there" — so this same command works whether it's your first time running it or you need to start over.

**Build and test commands** (optional — the lab does not require running the app; use these only if you want to confirm the project still compiles):

```bash
cd backend
./mvnw compile
./mvnw test
```

**Files to open side by side (files in scope):**

```
backend/src/main/java/com/workshop/petcareops/dashboard/EscalationBoardController.java
backend/src/main/java/com/workshop/petcareops/dashboard/EscalationBoardService.java
backend/src/main/java/com/workshop/petcareops/dashboard/EscalationSummaryResponse.java
backend/src/main/java/com/workshop/petcareops/dashboard/ClinicianEscalationLoad.java
```

**Prepared review outputs** (open when Step 3 starts, provided alongside this README):

```
copilot-review-broad.md
copilot-review-structured.md
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

## Step 3: Evaluate the Prepared Copilot Reviews (12 minutes — 6 + 6)

### Goal

Two prepared review outputs already exist for this exact change — you are not generating them, you are evaluating them.

### 3a. Broad review (6 minutes)

Open `copilot-review-broad.md`. This was generated from an unscoped prompt: simply "Review this pull request." Read it and, for each claim it makes, decide: is this supported by real evidence? Is it accurate? Is it something a human could act on directly, or does it need rework first? Note anything it appears to have missed.

### 3b. Structured-contract review (6 minutes)

Open `copilot-review-structured.md`. This was generated from a scoped prompt requesting the same six-field contract you just used yourself, with explicit permission to check five named existing files for consistency. Evaluate it the same way.

Across both: which findings are supported? Which are generic or wrong? What did each miss? Is the evidence and verification good enough that a human could act on it without redoing the work?

## Step 4: Compare and Produce the Artifact (7 minutes)

Fill in the comparison matrix (facilitator will display it) across all three sources — your manual review, the broad output, and the structured output.

Then produce:

1. A concise PR summary (2–3 sentences, in your own words).
2. Your three highest-value findings across all three sources, in the six-field contract format.
3. A recommendation: approve, request changes, or investigate further — with a one-line reason.
4. Any remaining question that needs a human, not a re-prompt, to resolve.

## Optional Licensed Extension

If you already have approved Copilot access, you may — after completing Step 4, not instead of it — run the structured-contract prompt yourself and compare your live result against the prepared `copilot-review-structured.md`. This is optional and not required to complete the lab.

Note: the repo's `.github/copilot-instructions.md` will be active during this run — your live result may differ from the prepared one for that reason alone, not because either is wrong.

```text
Review the four new files in backend/src/main/java/com/workshop/petcareops/dashboard/:
EscalationBoardController.java, EscalationBoardService.java,
EscalationSummaryResponse.java, ClinicianEscalationLoad.java.

For each finding, give:
1. Exact evidence (file + method)
2. Category: Reliability, Maintainability, or Best Practices
3. Consequence
4. Confidence or status
5. Smallest remediation
6. A verification method

You may inspect FollowUpRecommendationService, ReminderChannelAdvisor,
OwnerReminderDraftService, LegacyCarePlanSnapshotService, and
ClinicDashboardService ONLY to check whether this change is behaviorally
consistent with how urgency and channel selection already work elsewhere
in this app. Do not propose unrelated architecture changes. Do not give
generic style advice. State explicitly where you are uncertain.
```

## If You Get Stuck

- If the manual-review window feels short, note that — it's data for the debrief, not a failure.
- If you're unsure whether something is a local or relational finding, flag it as relational — better to over-flag than to guess.
- After this lab's debrief, you'll see how GitHub operationalizes this same review discipline at pull-request scale — that's a demonstration to watch, not something you need to reproduce.

## Key Takeaways

- A review is only as good as its evidence, consequence, confidence, remediation, and verification — not its finding count.
- Some findings are structurally invisible without the right context, regardless of who — or what — is reviewing.
- A structured, bounded review request produces more decision-useful output than "review this PR" — but neither guarantees completeness.
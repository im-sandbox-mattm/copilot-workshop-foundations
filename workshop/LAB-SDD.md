# Lab: Spec-Driven Development with GitHub Copilot and Spec Kit

## What you'll do

In this lab, you'll use **GitHub Spec Kit through GitHub Copilot** to work through a spec-driven development workflow for an existing PetCareOps feature.

You'll initialize Spec Kit in the repository, establish and review project governance, create and clarify the feature specification, generate an implementation plan and task list, implement the approved work, and assess whether the result has converged on the specification.

Because this is an existing brownfield codebase, you'll also use Copilot outside the built-in Spec Kit workflow at a few points to understand existing behavior and review important assumptions before they become durable downstream inputs.

The emphasis is not on reproducing one exact model response. Your generated artifacts and clarification questions may differ. The goal is to practice **reviewing AI-generated artifacts before allowing them to become authoritative inputs to the next stage**.


> **About these instructions:** This lab is intentionally written as a guide rather than a solution key. It serves to provide direction on what to examine, decisions to consider, and helpful ways to verify progress without pre-answering the brownfield behavior or the exact output Copilot should generate.

# Before You Begin

## Open the Lab Repository

Use the GitHub repository link provided in Teams.

Open the lab instructions in your IDE:

```text
workshop/LAB-SDD.md
```

Keep this file available while you work through the exercise.

You will also receive a **Supplemental Lab Reference** in Teams. It contains expected outcomes and troubleshooting guidance for key checkpoints. Use it if your results differ from the lab directions or you need help confirming that you are on the right path.

Keep the Supplemental Lab Reference outside the repository so it does not become part of the codebase context Copilot may inspect during the exercise.

---

# Required Tools

| Requirement | Lab expectation |
|---|---|
| GitHub Copilot | Signed in and available in your IDE with **Agent mode** enabled |
| Specify CLI (`specify-cli`) | **1.0.1** for this lab |
| Python | **3.11+** |
| Java | **JDK 21 or compatible newer JDK** |
| Git | Available on the command line |
| Maven | **No separate Maven installation required**; the repository includes the Maven wrapper |

Complete any required package installation before beginning the exercise.

---

# Step 0 — Preflight

Run these checks **before initializing Spec Kit**.

### macOS / Linux

```bash
python3 --version
specify version
java -version
git --version
```

### Windows

```powershell
py --version
specify version
java -version
git --version
```

### Checkpoint

Confirm:

```text
Python:      3.11 or later
Spec Kit:    1.0.1
Java:        JDK 21 or compatible newer JDK
Git:         command available
```

## If `specify` is not available

`specify` is the command installed by **Specify CLI (`specify-cli`)**, the command-line package for GitHub Spec Kit.

Install **`specify-cli` version 1.0.1** using your organization's approved Python tooling or package source, then verify the installation:

```bash
specify version
```

Official installation options include `uv`, `pipx`, and `pip`.

See the [official Spec Kit installation guide](https://github.github.com/spec-kit/installation.html) if you need installation details.

---

# Step 1 — Verify Your Local Baseline

Before Spec Kit changes anything, confirm that the known-good backend builds and tests successfully in your local environment.

### macOS / Linux

```bash
cd backend
./mvnw test
cd ..
```

### Windows

```powershell
cd backend
.\mvnw.cmd test
cd ..
```

### Expected checkpoint

```text
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

> **Why do this first?**  
> Establishing a known-good baseline lets you distinguish failures introduced during the feature work from failures that were already present.

If the baseline does **not** pass, do not ask Copilot to modify the application to repair it. Confirm that your JDK and local Maven-wrapper execution are working, resolve the local environment issue, and rerun the baseline before continuing.

---

# Step 2 — Initialize Spec Kit in the Existing Repository

Return to the repository root and run:

```bash
specify init --here --integration copilot --script py
```

Because this is an **existing repository**, Spec Kit will warn that the current directory is not empty and that template files will be merged.

When prompted:

```text
Do you want to continue? [y/N]:
```

enter:

```text
y
```

### You may also see an Agent Folder Security warning

Spec Kit may warn that agent directories can contain credentials and suggest considering `.github/` for `.gitignore`.

**Do not add the entire `.github/` directory to `.gitignore` for this lab.**

This repository already contains project guidance under `.github/`, and Spec Kit installs its Copilot skills there. The warning is general credential-hygiene guidance, not an instruction to hide the repository's Copilot configuration.

### Checkpoint

After initialization:

```bash
git status --short
```

You should see new Spec Kit infrastructure similar to:

```text
?? .github/skills/
?? .specify/
```

At this point:

- `.github/skills/` contains the installed Spec Kit skills;
- `.specify/` contains the Spec Kit workflow infrastructure and templates.

---

# Step 3 — Establish and Review the Project Constitution

Open GitHub Copilot Chat in **Agent mode** and run:

```text
/speckit-constitution
```

The constitution is intended to capture durable project governance for the Spec Kit workflow. That makes this an important point to review what Copilot generated rather than treating a structurally valid artifact as automatically authoritative.

## Review the generated governance

Your constitution may not match someone else's line for line. Focus on the **source and authority** behind strong statements.

Pay particular attention to language such as:

- `MUST` or `MUST NOT`;
- `required`;
- universal workflow rules;
- approval gates;
- review-process requirements.

The goal is **not** to remove every strong requirement. A `MUST` can be appropriate when an established repository source actually supports it. The important question is:

> **Where did this rule come from?**

Repository-wide guidance and path-scoped guidance are also different kinds of authority. A rule that applies only to backend or frontend paths should not automatically become a universal repository rule.

## Use Copilot to perform a provenance review

The following prompt is one useful way to perform that review. You do not need to reproduce it verbatim; the objective is to challenge potentially unsupported absolutes before they become durable project governance.

```text
Review the current Spec Kit constitution against:
- `.github/copilot-instructions.md`
- `.github/instructions/backend.instructions.md`
- `.github/instructions/frontend.instructions.md`

Keep durable governance that is directly supported by those repository
instructions.

Remove or soften MUST-level, approval, review-process, or universal workflow
rules that were introduced by inference rather than established repository
guidance.

Preserve the distinction between repository-wide expectations and guidance
that is intentionally path-scoped.

Do not add new policy.

Update only `.specify/memory/constitution.md`.

Afterward, briefly identify the most important inferred rules you removed or
changed and why.
```

### Checkpoint

Before continuing, review the resulting constitution and confirm that:

- strong governance statements can be traced to established repository guidance;
- backend/frontend-specific guidance remains appropriately scoped;
- plausible engineering advice has not been promoted into mandatory project policy without a source.

A successful Spec Kit validation is useful evidence that the artifact is well formed. It does **not** by itself establish that every governance statement has the right authority.

---

# Step 4 — Understand the Existing Feature Before Specifying It

Before creating the feature specification, use Copilot to understand how the existing escalation summary works.

Open a **new Copilot Chat** in Agent mode and run:

```text
Trace how GET /api/dashboard/escalations is implemented in this repository.

Show me:
- the request path from controller through service to response;
- the main data used to compute the result;
- what "totalEscalations" and "busiestClinician" currently mean in the code;
- any existing tests that establish escalation behavior.

Do not modify any files.

Keep the explanation focused on what I need to understand before changing this feature.
```

### Why do this first?

This prompt is meant to give you enough context about the existing feature to understand what it does today before you ask Spec Kit to define how it should behave going forward.

Copilot may surface additional issues while tracing the endpoint. Do not fix them yet; use this step only to understand the current behavior.

### Checkpoint

Before continuing, you should be able to explain:

- what currently causes an appointment to qualify as an escalation;
- what `totalEscalations` represents in the current implementation;
- what `busiestClinician` represents today;
- whether existing tests establish those behaviors.

---

# Step 5 — Create the Feature Specification

Stay in the **same Copilot Chat** you used to trace the escalation endpoint.

Run:

```text
/speckit-specify Improve the escalation summary so staff can trust it during repeated refreshes and quickly identify the busiest clinician.
```

### What to look for

Review the generated specification as a proposed interpretation of the feature request, not as an automatically approved set of requirements.

At this point, pay attention to places where the specification may have made product decisions that were not explicitly established by:

- the original feature request;
- the repository behavior you just traced;
- an explicit decision from you.

Also note any numeric targets, thresholds, percentages, durations, or other highly specific criteria whose source is unclear. Do not correct those assumptions yet. The next step will use clarification and review to decide which ones should become authoritative.

### Checkpoint

You should now have a new feature specification under `specs/` and an active Spec Kit feature for the escalation-summary work.

Spec Kit also creates a requirements quality checklist under the active feature's `checklists/requirements.md`. Treat it as a useful specification-quality check, not as proof that every product decision in the specification has been explicitly established.

### Check generated branch metadata

In the validated run, `/speckit-specify` created the numbered feature directory and wrote this into `spec.md`:

```text
Feature Branch: 001-improve-escalation-summary
```

That metadata implied a matching feature branch had been created or selected, but Git showed that no such branch existed and the repository had remained on the original working branch.

If your generated `spec.md` contains a `Feature Branch` value, verify it with:

```bash
git branch --show-current
```

If the value does not match the branch you are actually on, update the **`Feature Branch` field in the active feature's `spec.md`** to match the current Git branch. Do not create or switch branches solely to make the generated metadata true.

Keep this Copilot Chat open. You will return to it for the next Spec Kit step.

---

# Step 6 — Clarify Material Decisions Before Planning

Be sure you're in the **same Copilot Chat window** you've been using for the feature-specific Spec Kit work.

Run:

```text
/speckit-clarify
```

Spec Kit may ask you one or more questions about the generated specification. Your questions, recommendations, and answer choices may differ from those seen by other participants.

Answer each question based on the behavior you want the feature to establish. Do not select an option only because Copilot marks it as **Recommended**.

### What are you looking to clarify?

Focus on material product decisions such as:

- what an ambiguous business term actually means;
- what should happen at important edge or boundary conditions;
- which existing behavior should remain unchanged;
- what is inside or outside the feature scope.

When the clarification session completes, review the specification again before moving to planning.

## Check for assumptions Clarify may have missed

A completed clarification session or passing checklist does not necessarily mean every important decision was explicitly established.

Look for places where the specification may have filled in details that did not come from:

- the original feature request;
- repository behavior you inspected;
- an explicit clarification answer.

Pay particular attention to **unsupported precision**: numeric targets, thresholds, percentages, durations, sample sizes, retry counts, or similar values whose source is unclear.

A number is not automatically wrong. The question is:

> **Where did this number or decision come from?**

If the specification needs an objectively testable outcome, that does not necessarily require inventing a numeric threshold.

## Use Copilot for a final pre-planning assumption review

The following is one useful way to perform that review. Adapt it to what you see in your own specification.

```text
Before planning, review the specification for assumptions that were introduced
without support from the original feature request, repository evidence, or my
clarification answers.

Pay particular attention to:
- unresolved business semantics;
- edge-case and boundary behavior;
- invented quantitative targets or thresholds;
- scope decisions that I did not explicitly make.

For any numeric target, threshold, percentage, duration, or sample size, identify
its source. If there is no established source, do not treat it as a requirement.

Do not invent replacement numbers merely to make a criterion measurable.
Prefer objectively testable behavioral outcomes where appropriate.

Ask me about any material unresolved product decision before changing it.
```

Continue answering any material questions Copilot surfaces until the feature boundaries and behavior are clear enough to plan.

### Checkpoint

Before moving on, confirm that:

- important business terms have an explicit meaning;
- material edge and boundary behavior is clear where relevant;
- feature scope reflects decisions you actually made;
- numeric or other highly specific requirements have an identifiable source;
- unsupported assumptions have not been promoted into approved requirements.

Continue within the same Copilot Chat window for the planning step.

---

# Step 7 — Create and Review the Implementation Plan

Continue within the **same Copilot Chat window**.

Run:

```text
/speckit-plan
```

The planning step should translate the approved specification into an implementation approach and supporting design artifacts. Review those artifacts before allowing them to become the basis for task generation.

### What to look for

Check that the plan:

- stays within the feature scope you established during clarification;
- preserves behavior that the specification says should remain unchanged;
- does not introduce unnecessary architecture, dependencies, persistence, or cross-project work;
- proposes verification that can actually prove the behavior described by the specification.

A plan can be technically reasonable and still have an **evidence gap**. For externally observable behavior, consider whether the planned test or check exercises the layer where that behavior is exposed.

This does **not** mean every feature needs broad integration testing. Use the smallest verification layer that can actually prove the requirement.

## Review the verification plan before generating tasks

A recommended follow-up prompt along the lines of the following can help surface whether the plan has any verification gaps, especially where externally observable behavior may need to be proven at a different layer than internal service behavior:

```text
Before generating tasks, review the current implementation plan against the
approved specification and API contract.

Do not change product requirements or broaden feature scope.

Check whether the proposed verification can deterministically prove each
externally observable behavior at the layer where it is exposed.

In particular, distinguish between:
- internal implementation behavior; and
- behavior exposed through an external contract or system boundary.

If the current plan has a verification gap, identify the smallest additional
verification needed. Do not add broad integration testing or unrelated test
coverage.

Update only the planning artifacts if a correction is needed, then briefly
summarize what changed and why.
```

Your plan may already contain sufficient verification. The purpose of this review is to evaluate the evidence, not to force an additional test layer when one is unnecessary.

### Checkpoint

Before continuing, confirm that:

- the plan reflects the decisions recorded in the approved specification;
- implementation scope has not expanded without a reason;
- planned verification covers both internal behavior and externally observable contracts where applicable;
- any added verification is the smallest useful layer rather than unrelated test expansion.

You should also see planning artifacts under the active feature directory, such as `plan.md`, `research.md`, `data-model.md`, `contracts/`, and `quickstart.md`.

Continue within the same Copilot Chat window for task generation.

---

# Step 8 — Generate and Review the Task List

Continue within the **same Copilot Chat window**.

Run:

```text
/speckit-tasks
```

The task list should translate the approved specification and implementation plan into executable work.

### What to look for

Review whether the generated tasks:

- remain within the approved feature scope;
- preserve the implementation and verification decisions from the plan;
- are small enough to execute and verify incrementally;
- distinguish tests that expose incorrect behavior from tests that preserve behavior that may already be correct.

A test written before an implementation change does not automatically need to fail. A defect-revealing test should expose the behavior being corrected, while a characterization or contract-preservation test may already pass because its purpose is to protect behavior that should remain unchanged.

## Review the test-first execution guidance

A recommended follow-up prompt along the lines of the following can help surface misleading assumptions about what each planned test is expected to prove:

```text
Before implementation, review the task list's test-first execution guidance.

Do not change the approved requirements, plan, task scope, or task ordering.

Distinguish between:
- tests intended to expose behavior that is currently incorrect; and
- characterization/contract tests intended to preserve behavior that may already pass.

Do not state that a contract-preservation test must fail merely because it is
written before the implementation change.

Correct only inaccurate execution guidance in `tasks.md`, if present.
Briefly summarize the correction.
```

Your task list may already make this distinction correctly. The purpose of this review is to make sure the execution guidance matches what each test is actually intended to prove.

### Checkpoint

Before implementation, confirm that:

- the tasks still reflect the approved specification and plan;
- implementation work has not expanded beyond the intended scope;
- verification tasks are mapped to the behaviors they are meant to prove;
- defect-revealing and contract-preservation tests are not being treated as though they must have the same pre-implementation result.

Continue within the same Copilot Chat window for implementation.

---

# Step 9 — Implement the Approved Work

Continue within the **same Copilot Chat window**.

Run:

```text
/speckit-implement
```

Spec Kit will work through the approved task list and update the implementation and verification artifacts.

### What to look for

As implementation runs, pay attention to the evidence it produces along the way:

- whether defect-revealing tests fail before the related fix;
- whether characterization or contract-preservation tests are allowed to pass when the behavior they protect is already correct;
- whether implementation stays within the approved scope;
- whether the relevant test suite is actually run;
- whether task completion is backed by observable evidence rather than only by a completion claim.

Copilot may also identify a weakness in one of its own tests or assertions while implementing the feature. If it does, evaluate whether the correction strengthens the intended evidence without broadening the feature.

### Checkpoint

Before continuing, confirm that:

- the implementation reflects the approved specification, plan, and tasks;
- behavior the approved specification says should remain unchanged remains unchanged;
- relevant automated tests pass;
- the task list has been updated to reflect completed work;
- any live or deterministic verification described in the plan has actually been performed.

A completed task list is useful evidence, but it is not the final assessment of whether the implementation fully satisfies the approved artifacts.

Continue within the same Copilot Chat window for convergence.

---

# Step 10 — Assess Convergence

Continue within the **same Copilot Chat window**.

Run:

```text
/speckit-converge
```

Converge reviews the implemented feature against the approved specification, planning artifacts, task list, and applicable project governance.

### What to look for

A healthy convergence result may take either form:

- **No actionable gaps remain** and the feature is ready for review; or
- Spec Kit identifies remaining work and appends new tasks that should be completed before the feature is considered converged.

Do not assume that Converge must find something. Its purpose is to assess the current evidence and implementation state, not to manufacture additional work.

Also keep in mind that a converged result does not mean every conceivable test has been written. A requirement may already be sufficiently established by the implementation and existing verification, while additional hardening could still be valuable.

### Checkpoint

Review the convergence result and confirm that:

- Spec Kit compared the implementation against the approved feature artifacts;
- any remaining gaps are specific and actionable;
- no unrelated work was introduced;
- the relevant backend verification remains green;
- if no gaps were found, `tasks.md` was not changed simply to force additional work.

If Converge appends tasks, run:

```text
/speckit-implement
```

to complete the newly added work, then run:

```text
/speckit-converge
```

again to reassess the updated implementation.

If no actionable gaps remain, the feature is ready for review.

---

# Wrap-Up

You have now used GitHub Spec Kit through GitHub Copilot to take an existing feature from current-state understanding through specification, clarification, planning, task generation, implementation, and convergence.

The important outcome is not that every generated artifact was accepted unchanged. At each stage, you reviewed the artifact before allowing it to become an input to the next stage.

When `/speckit-converge` reports no actionable gaps and the relevant verification remains green, the lab workflow is complete. The convergence result is backed by the specification, implementation, tests, and other verification accumulated throughout the workflow.

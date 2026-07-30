# Lab 2: Cost-Aware Review Standardization

**This lab uses GitHub Copilot in the IDE. Access to a GitHub-hosted repository or GitHub Copilot Code Review is not required.**

Work in pairs or small groups and perform **one live Copilot run per group**.

Your challenge is to restructure how review context is supplied, then determine whether your review beats the provided baseline without lowering its decision quality.

## What You Will Practice

- identifying costly or low-value review context
- choosing the appropriate place for reusable guidance
- restructuring Copilot instructions, skills, agents, attachments, or deterministic checks
- comparing model usage and review quality
- deciding whether an optimization is worth adopting

---

## Before You Start

This lab uses the same starting point as Lab 1.

### If You Already Completed Lab 1

Remain on the branch you created from `module-06-2-lab`.

### If You Are Starting Directly with Lab 2

```bash
# If you have not cloned the repository:
git clone https://github.com/im-sandbox-mattm/copilot-workshop-foundations.git
cd copilot-workshop-foundations

# If you already cloned it:
git fetch --tags

# Create or reset your workshop branch:
git switch -C module-06 module-06-2-lab
```

> The capital `-C` creates the branch if it does not exist or resets it to the lab starting tag if it does.

---

## Step 1: Understand the Baseline

Open:

```text
handouts/assets/baseline.md
handouts/assets/lab2-baseline-response.md
```

`baseline.md` defines:

- the benchmark to beat;
- the settings that must remain fixed;
- the review-quality threshold.

`lab2-baseline-response.md` contains the complete response and the context used to generate it.

Do not rerun the baseline.

As a group, identify:

- which findings are well supported;
- which findings are weak, overstated, or low value;
- anything important the baseline missed;
- where the response would still require human validation.


---

## Step 2: Restructure the Context

Inspect the baseline context files:

```text
.github/copilot-instructions.md
.github/instructions/dashboard-review.instructions.md
.github/agents/lab2-review.agent.md
handouts/assets/lab2-baseline-test-output.txt
```

Decide what should:

- remain as-is;
- move to a narrower or task-specific scope;
- load only when relevant;
- be replaced by a deterministic check;
- or be removed.

You may revise or create:

- repository-wide or path-specific instructions;
- an agent skill;
- reference files;
- the manually attached context;
- a deterministic script or test;
- a custom agent that retains the permitted read-only tool capabilities.

You are not required to use every mechanism.

Do not change:

- the application code;
- the review scope;
- the exact review prompt;
- the controlled execution settings in `baseline.md`.

Implement your chosen changes.

Before continuing, be ready to state:

> We changed __________ because we expect it to reduce or improve __________.

---

## Step 3: Run the Review Once

Verify that your setup follows the controls in:

```text
handouts/assets/baseline.md
```

Start a **fresh Copilot chat**.

Provide only the context required by your redesigned configuration.

Submit the exact prompt from:

```text
handouts/assets/lab2-review-prompt.md
```

Do not:

- alter the prompt;
- add hints from the baseline response;
- tell Copilot which findings to reproduce;
- reprompt to improve the answer;
- ask Copilot to rewrite the result.

Save the response exactly as generated.

---

## Step 4: Compare the Results

Open the Agent Debug Log for your live run.

### Model Usage

| Measure | Baseline | Your Run |
|---|---:|---:|
| Copilot Usage (AIC) | | |
| Input tokens | | |
| Cached input tokens | | |
| Fresh input tokens | | |
| Output tokens | | |
| Total tokens | | |
| Model turns | | |
| Tool calls | | |
| Errors | | |

Use the baseline values and formulas in:

```text
handouts/assets/baseline.md
```

### Review Quality

Compare the two responses and record:

| Question | Result |
|---|---|
| Which supported baseline findings were retained? | |
| Which supported findings were missed? | |
| Which unsupported or overstated findings were removed? | |
| Did the new run introduce unsupported findings? | |
| Did it identify any new valid findings? | |
| Were evidence, consequence, confidence, remediation, and verification preserved? | |
| Which response requires less human validation? | |

Finding count alone does not determine quality.

---

## Step 5: Decide Whether You Beat the Baseline

Complete:

> Our configuration did / did not beat the baseline because ____________________________________.

Support your conclusion with:

1. the Agent Debug Log telemetry;
2. specific differences between the findings;
3. the human effort required to validate and use each response.

Then classify your restructuring:

- **Adopt**
- **Reject**
- **Pilot further**

A cheaper run that loses an important finding is not a successful optimization.

---


## Key Takeaways

- Context optimization is more than shortening a prompt.
- Measure cost and review quality together.
- Load guidance at the narrowest useful scope.
- Use deterministic checks where model reasoning adds no value.
- The best configuration reduces model and reviewer effort without weakening the decision.
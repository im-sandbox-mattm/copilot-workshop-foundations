# Lab 1 — Bound, Reproduce, and Document the Incident

## Before You Start

### Get the workshop repository

If you have not cloned it yet:

```bash
git clone https://github.com/im-sandbox-mattm/copilot-workshop-foundations.git
cd copilot-workshop-foundations
```

If you already have it cloned:

```bash
cd copilot-workshop-foundations
git fetch --tags
```

Everyone runs:

```bash
git switch -C workshop-7-lab workshop-7-lab
```

This creates or resets a local branch named `workshop-7-lab` at the `workshop-7-lab` starting tag, then switches to that branch.

### Environment check

Before beginning, confirm:

- you opened the repository root as a trusted workspace;
- Java 21 or later is available;
- `curl` is available;
- you are using Bash or Zsh for the supplied commands;
- port `8080` is available;
- Copilot can inspect repository files and request approval to run terminal commands.

Run:

```bash
java -version
curl --version
```
[Confirm that Java reports version 21 or later and that curl returns version information without an error]

The repository includes the Maven wrapper, so Maven does not need to be installed separately. No external database setup is required, and the frontend does not need to be running for the core lab.

## Goal

Use GitHub Copilot to turn a user-facing dashboard failure into a bounded, correlated evidence package and a provisional incident record.

You are **not** trying to fix the issue yet.

## End result

By the end of this lab, you should have:

- one fresh timestamped evidence run under `backend/target/incident-evidence`;
- two overlapping `/api/dashboard` requests with distinct request IDs;
- captured status, headers, bodies, timing, and runtime-log correlation;
- a verified cross-layer timeline;
- `backend/target/incident-evidence/provisional-incident-record.md`;
- ranked hypotheses and one reviewed next targeted check.

A good result should look like this:

> “I can trace the failing request from the frontend-visible status and request ID to the matching backend runtime events. I have separated observed evidence from inference, and I have not yet claimed a final root cause.”

---

## Working rules

- Use one continuous Copilot session for this lab. Keep the work in one chat thread if you are using Copilot Chat, or in the same session if you are using Copilot CLI.
- Unless a step says otherwise, have Copilot run commands from the repository root.
- Treat runtime logs and raw HTTP artifacts as primary evidence.
- Treat generated summaries and extracts as secondary evidence that must be checked.
- Do not modify production code, tests, or configuration.
- Do not diagnose the issue during evidence capture.
- Stop at each checkpoint before moving on.

### How to use the recommended language

For each step, write a prompt that includes the task described in the instructions, then add the recommended language as constraints. **Do not submit the recommended language by itself.**

---

## Step 1 — Trace the request across layers


Prompt Copilot to inspect:

- `frontend/src/App.tsx`
- `frontend/src/api.ts`
- only the backend files directly involved in `/api/dashboard`, request-ID handling, and runtime logging

Your prompt should direct Copilot to explain:

- where the dashboard request starts;
- how `X-Request-ID` is created and sent;
- how status and request ID reach the user-visible error;
- how the same ID appears in backend runtime logs;
- what evidence a controlled reproduction should capture.

> **Recommended language to include in the prompt you craft:**

```text
Do not diagnose the root cause.
Do not modify code.
Finish with a concise cross-layer evidence plan.
Stop before designing or executing the reproduction.
```

### Checkpoint

You should get something like:

- frontend request origin;
- request-ID flow;
- backend correlation points;
- a short evidence checklist.

If Copilot begins proposing fixes or a root cause, refine the prompt and restate the stop condition.

---

## Step 2 — Design the controlled reproduction

Before continuing, start the backend from the `backend` directory using the command below. Keep that terminal open for the rest of Lab 1.


```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=workshop-incident \
  2>&1 | tee target/incident-evidence/backend-runtime.log
```

*`tee` keeps the backend logs visible in the terminal while also saving a copy to `target/incident-evidence/backend-runtime.log`.*

Wait until the application reports that it has started. Keep that terminal open.

Use the evidence plan to prompt Copilot for a reproduction plan only.

In the request pattern below, **start** means send the HTTP request. It does not mean start the backend server.

The required request pattern is:

1. Start `GET /api/dashboard` with `X-Request-ID: incident-run-1`.
2. Confirm request 1’s start timestamp has been recorded.
3. Wait approximately 200 ms.
4. Start a second overlapping request with `X-Request-ID: incident-run-2`.
5. Capture both requests through completion.

The plan must capture:

- start and end timestamps;
- status;
- response headers;
- response body;
- curl exit code;
- measured start delta;
- overlap result;
- matching runtime events.

> **Recommended language to include in the prompt you craft:**

```text
Save new evidence under:
backend/target/incident-evidence/run-<timestamp>

Do not execute yet.
Do not start, stop, or restart the backend.
Stop after presenting the plan.
```

### Checkpoint

The plan should clearly state:

- exact request order;
- how overlap will be verified;
- where evidence will be stored;
- how each request maps back to the frontend-visible status and request ID.

If the plan assumes the 200 ms delay without verifying request 1 actually started, prompt Copilot to correct that before execution.

---

## Step 3 — Execute one evidence run

Review the proposed plan before approving it. At minimum, it should:

- use the two required request IDs in the correct order;
- verify request 1 has actually started before beginning the delay;
- allow the requests to overlap;
- capture separate response and timing evidence for both requests;
- save fresh artifacts in a timestamped run directory;
- preserve the existing runtime log.

If those elements are present, send Copilot a follow-up message authorizing it to execute the plan once. If any are missing, refine the plan first.

> **Recommended language to include in the prompt you craft:**

```text
Execute the approved reproduction once.

Use Python-based timestamps.
Confirm request 1’s start timestamp exists before beginning the delay.
Save every new artifact under:
backend/target/incident-evidence/run-<timestamp>

Do not overwrite backend-runtime.log.
Do not retry automatically.
Do not diagnose the incident.
```

### Checkpoint

Before continuing, confirm the evidence package contains at least:

- separate artifacts for each request;
- actual measured timing;
- `overlap=true`;
- echoed request IDs;
- matching backend runtime events.

If the measured delay differs from 200 ms, preserve and report the actual value. Do not rewrite it as exactly 200 ms.

---

## Step 4 — Verify the evidence package

Prompt Copilot to identify the timestamped run directory created in Step 3, then independently compare that run with `backend/target/incident-evidence/backend-runtime.log`. Treat the runtime log as authoritative.

> **Recommended language to include in the prompt you craft:**

```text
Independently verify the latest evidence package rather than relying on your previous summary.

Check ordering, measured delay, overlap, statuses, echoed request IDs, and matching runtime events.

Flag missing, contradictory, ambiguous, or stale evidence.
Do not diagnose or propose remediation.
```

### Checkpoint

The review should distinguish:

- verified evidence;
- missing or ambiguous evidence;
- cross-layer correlation;
- whether the package is complete for the defined transport-to-backend boundary.

A direct browser screenshot is optional. Its absence should be noted, but it should not make the package incomplete if the frontend code, HTTP evidence, and backend request ID correlate correctly.

---

## Step 5 — Create the provisional incident record

Prompt Copilot to use the verified evidence and only the minimal production code needed to create and save:

`backend/target/incident-evidence/provisional-incident-record.md`

The record should include:

- evidence boundary;
- cross-layer request path;
- correlated timeline;
- findings with explicit confidence labels;
- ranked hypotheses;
- one next targeted check.

Label each material finding so the reader can tell how strongly the evidence supports it. Use one of these labels at the beginning of each finding:

- **Observed** — directly present in a raw artifact or runtime event
- **Verified** — independently checked against the available evidence
- **Inferred** — derived from evidence or code, but not directly observed at runtime
- **Hypothesis** — a possible explanation that still requires a targeted check
- **Contradicted** — inconsistent with available evidence
- **Not established** — the available evidence does not support a conclusion

> **Recommended language to include in the prompt you craft:**

```text
Distinguish runtime evidence from conclusions derived from code.
Do not declare a final root cause unless the evidence proves it.
Cite the exact evidence artifact, runtime event, request ID, or code location supporting each material claim.
Do not propose remediation.
```

### Checkpoint

Before continuing, confirm the provisional record communicates that:

> “The failing request is correlated across transport and backend runtime evidence. The suspected cause is supported but not yet verified. One candidate next targeted check is proposed for review.”

If Copilot overstates certainty, ask it to downgrade unsupported claims and update the record.

---

## Step 6 — Evaluate the proposed next check

Do not automatically accept Copilot’s first proposed check.

Prompt Copilot to compare the proposed check with at least one reasonable alternative, using this order:

1. Address the largest unresolved uncertainty.
2. Directly test the suspected cause.
3. Produce evidence that could strengthen or weaken that suspected cause.
4. Stay narrowly scoped.
5. Use the smallest reasonable and least invasive method that still meets criteria 1–4.
6. Ensure temporary changes are reversible.

> **Recommended language to include in the prompt you craft:**

```text
“Least invasive” does not mean “requires no code changes.”
Do not prefer an easy check if it tests an alternative that is not materially supported by the evidence.
Update only the parts of the provisional incident record affected by the refined decision.
Do not execute the check.
```

### Lab 1 stop point

Stop when the provisional incident record contains a reviewed, causally useful next targeted check.

Do not begin instrumentation or remediation in this lab.

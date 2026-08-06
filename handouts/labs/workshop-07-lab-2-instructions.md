# Lab 2 — Test the Suspected Cause, Remediate, and Verify

## Prerequisite

Complete Lab 1 and confirm that `backend/target/incident-evidence/provisional-incident-record.md` exists.

## Goal

Use the provisional incident record from Lab 1 to test the suspected cause, apply the smallest justified remediation, and verify the result with tests and fresh runtime evidence.

## End result

By the end of this lab, you should have:

- corrected diagnostic evidence that verifies or weakens the suspected cause;
- an incident record updated with the verified evidence and remaining uncertainty;
- the smallest evidence-supported remediation implemented;
- a focused concurrency test that verifies the corrected behavior;
- a passing full backend test suite;
- runtime evidence showing the remediation holds under the same overlapping-request conditions;
- a clean final production state with all temporary diagnostics removed.

## Working rules

- Continue in the same Copilot session. Keep the work in the same chat thread if you are using Copilot Chat, or in the same CLI session if you are using Copilot CLI.
- Use `provisional-incident-record.md` as the working investigation record. Treat runtime logs and test results as the authoritative evidence.
- Separate planning, implementation, execution, and verification.
- Review generated changes in the IDE diff before compiling or running them. Use Copilot for targeted analysis of scope, correctness, risk, or verification.
- Temporary diagnostics should optimize for evidence value and removability.
- Generated extracts must be checked against the authoritative runtime log.
- Use one dedicated terminal for the backend process and separate terminals for compilation, tests, and Copilot-run capture commands. Keep the backend running during evidence-capture steps, and restart it only when the instructions require a newly compiled code state.
- Do not let Copilot manage backend processes during evidence-capture steps unless explicitly instructed.

### How to use the recommended language

For each step, write a prompt that includes the task described in the instructions, then add the recommended language as constraints. **Do not submit the recommended language by itself.**

---

## Step 1 — Design the targeted diagnostic check

Using `backend/target/incident-evidence/provisional-incident-record.md`, craft a prompt that directs Copilot to design the smallest temporary diagnostic change needed to test the suspected cause.

The response should contain only:

1. file to modify;
2. temporary diagnostic change;
3. expected evidence;
4. risk and cleanup;
5. stop condition.

> **Recommended language to include in the prompt you craft:**

```text
Capture the complete exception stack and cause chain.
Capture request-correlated pool state at the failure point when safely supported.
Do not alter response behavior or exception classification.
Do not restate the full reproduction workflow.
Stop after a concise diagnostic plan.
```

### Checkpoint

A good plan should be short enough to review and should identify:

- one bounded failure path;
- what evidence would strengthen or weaken the hypothesis;
- how temporary code will be removed;
- when to stop rather than broaden the change.

If the plan becomes a long implementation specification, prompt Copilot to return only the five items above.

---

## Step 2 — Implement the temporary diagnostic

Craft a prompt that directs Copilot to implement the approved temporary diagnostic plan. This step includes more recommended constraint language because unconstrained implementation can easily expand into unnecessary helper structures or broad instrumentation.

> **Recommended language to include in the prompt you craft:**

```text
Modify only the existing dashboard failure handler.
Emit exactly one new temporary diagnostic event.
Pass the exception as the final logger argument.
Do not add nested classes, records, DTOs, snapshot objects, builders, or reusable abstractions.
Do not add a separate cause-chain formatter.
Use at most one injected DataSource field, one constructor, and one small helper only if necessary.
Keep the net addition under 40 lines. If that is not possible, stop before editing and explain why.
```

Additional constraints to include:

- no reflection;
- do not log credentials, connection strings, request bodies, or other sensitive values;
- unchanged 503 response behavior.

After Copilot makes the change:

1. review the changes in the IDE diff before compiling;
2. prompt Copilot to report any constraint violations, unrelated edits, or remaining verification needs.

### Checkpoint

The temporary patch should be a small, removable diagnostic—not a mini-framework.

If Copilot creates large helper structures or broad lifecycle instrumentation, stop and refine the original implementation prompt rather than adding a corrective workflow to the lab.

---

## Step 3 — Compile the diagnostic patch

Open a **new terminal** at the repository root. Do not use the terminal reserved for the backend process.

Run:

```bash
cd backend
./mvnw -DskipTests compile
```

This moves into the backend project and asks Maven to compile the code while skipping test execution. It confirms that the temporary diagnostic patch is syntactically valid before you restart the application or collect runtime evidence.

If compilation fails, stop and review the exact compiler error before prompting Copilot to make any additional changes.

---

## Step 4 — Run one diagnostic reproduction

In the **dedicated backend terminal**, stop the Lab 1 backend process if it is still running. From the `backend` directory, start the backend with the temporary diagnostic logging added in Step 2:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=workshop-incident   2>&1 | tee target/incident-evidence/backend-diagnostic-runtime.log
```

Keep this terminal open during Steps 4–8. The command starts the backend with the workshop incident profile, keeps logs visible in the terminal, and saves the same output to `target/incident-evidence/backend-diagnostic-runtime.log`.

Craft a prompt that directs Copilot to execute one overlap attempt using the established Lab 1 request pattern. Copilot may run the capture commands in a separate terminal, but it must not start, stop, restart, or terminate the backend process.

> **Recommended language to include in the prompt you craft:**

```text
Use the already running backend with the temporary diagnostic logging enabled.
Do not start, stop, restart, or terminate any process during the capture step.
Run exactly one attempt.
Save all new artifacts under:
backend/target/incident-evidence/diagnostic-run-<timestamp>
Capture the complete multiline diagnostic event and all nested causes.
If the expected diagnostic evidence is missing, report the hypothesis as unresolved rather than filling gaps from prior runs or code.
```

### Checkpoint

A useful result should contain:

- overlapping request outcomes;
- complete exception chain;
- pool-state values when supported;
- a cautious hypothesis assessment.

Do not accept an “unresolved” conclusion until you check whether the extraction itself failed.

---

## Step 5 — Verify generated extracts against the source log

Use the diagnostic evidence captured in Step 4. Do not restart the backend or rerun the reproduction during this step.

First identify the `diagnostic-run-<timestamp>` directory created in Step 4. Then craft a prompt that directs Copilot to compare the generated artifacts in that directory with:

`backend/target/incident-evidence/backend-diagnostic-runtime.log`

This step provides recommended constraints because Copilot must treat the raw runtime log—not its own generated summaries—as authoritative.

> **Recommended language to include in the prompt you craft:**

```text
Treat the source runtime log as authoritative.

Check generated extracts and summaries for the correct time window, request IDs, request outcomes, complete multiline diagnostic event, nested causes, pool metrics, and supported conclusion.

Flag every discrepancy, omission, stale match, or unsupported conclusion.
Do not modify evidence yet.
```

### Checkpoint

You should get a discrepancy review that explains:

- what the derived artifact says;
- what the source log says;
- how the difference affects the conclusion;
- which artifact needs correction.

If discrepancies are found, authorize a short follow-up.

> **Recommended language to include in the follow-up prompt you craft:**

```text
Apply the recommended discrepancy corrections to the affected generated artifacts only.
Do not rerun the reproduction or modify raw evidence.
```

---

## Step 6 — Update the provisional incident record

Craft a prompt that directs Copilot to update the record from the corrected diagnostic evidence.

> **Recommended language to include in the prompt you craft:**

```text
Update only the sections directly affected by the new evidence.
Cite the corrected diagnostic artifacts and authoritative runtime log.
Distinguish what is verified from what remains inferred.
Do not propose remediation yet.
```

### Checkpoint

The record should now distinguish:

- repeated incident behavior;
- detailed diagnostic evidence captured in the Step 4 run;
- what cause is now verified;
- any remaining limits on certainty.

---

## Step 7 — Propose the smallest remediation

Craft a prompt that directs Copilot to inspect only the minimal production path needed to understand:

- where the transaction begins and ends;
- which work requires database access;
- which work occurs after the query.

> **Recommended language to include in the prompt you craft:**

```text
Propose the smallest remediation that corrects the verified cause of the failure.
Prefer correcting the transaction boundary over increasing pool size, timeout, or retries.
Do not modify files yet.
```

### Checkpoint

The proposal should explain:

- verified cause addressed;
- smallest code change;
- why it releases the connection earlier;
- broader or weaker alternatives;
- focused verification needed.

If Copilot proposes larger pool settings, longer timeouts, retries, or exception masking as the primary fix, prompt it to return to the verified cause of the failure.

---

## Step 8 — Implement the remediation

Craft a prompt that directs Copilot to apply the approved remediation while keeping the temporary diagnostic in place for the post-fix comparison (rerunning the same overlapping-request reproduction after the remediation and comparing the results with the pre-fix evidence). For now, keep the current backend process running. The remediation will not take effect in that process until you restart it in Step 11.

> **Recommended language to include in the prompt you craft:**

```text
Apply the smallest code change that narrows the transaction to the database work while preserving existing behavior.

Modify only the necessary production files.
Do not modify tests yet.
Do not change pool configuration, retry behavior, frontend code, request correlation, logging, response contracts, or exception handling.
Do not remove the temporary diagnostics yet.
After I review the changes in the IDE diff, report any constraint violations, unrelated edits, and a concise explanation of the new transaction boundary.
```

### Checkpoint

The IDE diff should contain only the intended transaction-boundary change.

Review the scoped changes in the IDE diff for the files Copilot intended to modify. Then prompt Copilot for a concise findings summary focused on violations, unrelated edits, and anything still requiring verification. Do not assume every workspace change belongs to the lab.

---

## Step 9 — Focused verification

Craft a prompt that directs Copilot to:

1. compile the backend;
2. inspect the existing focused backend test for overlapping dashboard requests;
3. update it only if needed;
4. run only that focused test.

Copilot should use a separate terminal from the one running the backend process.

The test should verify:

- request 1 remains active while completing additional processing after its database query;
- while request 1 is still completing that processing, request 2 begins and successfully acquires a database connection;
- both overlapping requests finish successfully without a connection timeout or HTTP 503 response.

> **Recommended language to include in the prompt you craft:**

```text
Do not change production code, pool configuration, or timing merely to make the test pass.
Do not run the full suite yet.
Save the focused test output to:
backend/target/incident-evidence/focused-test.log

When piping output through `tee`, preserve the test command's failure exit status by using `set -o pipefail`.
```

### Checkpoint

The result should explain what the focused test proves and what still requires runtime verification.

---

## Step 10 — Run the full backend suite

In a **separate terminal** from the backend process, run:

```bash
cd backend
mkdir -p target/incident-evidence
set -o pipefail
./mvnw test 2>&1 | tee target/incident-evidence/full-suite-test.log
```

This runs the complete backend test suite and saves the full output to `backend/target/incident-evidence/full-suite-test.log`.

If the full test suite fails, stop and review the failing test output before making further changes.

Expected outcome:

- all backend tests pass;
- no failures or errors;
- the focused concurrency test passes in the full suite.

---

## Step 11 — Run the post-fix overlap reproduction

Return to the **dedicated backend terminal**. Stop the running pre-remediation backend server, then start the server again with the remediated code:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=workshop-incident   2>&1 | tee target/incident-evidence/backend-postfix-runtime.log
```

Keep this terminal open during the capture. The command starts the remediated backend with the same incident profile and saves a separate authoritative post-fix runtime log.

Craft a prompt that directs Copilot to collect one comparable post-fix run against the already running remediated backend. Ensure the capture commands run in a separate terminal from the backend server.

> **Recommended language to include in the prompt you craft:**

```text
Use the already running remediated backend.
Do not start, stop, restart, or terminate any process during the capture step.

Use the same overlap pattern as the incident run.
Save all artifacts under:
backend/target/incident-evidence/postfix-run-<timestamp>

Report both request outcomes, overlap, correlated runtime events, and whether any acquisition-failure or temporary diagnostic event appears.
```

### Checkpoint

The end result should be something like:

- both requests return success;
- overlap is verified;
- both request IDs complete;
- no connection-acquisition failure appears;
- no temporary diagnostic event is emitted because no connection-acquisition failure occurs.

If the result differs, do not declare the remediation verified. Compare the new run with the earlier incident and diagnostic evidence before iterating.

---

## Step 12 — Finalize the code and incident record

After the post-fix evidence has been captured, stop the backend process in the dedicated backend terminal.

Craft a prompt that directs Copilot to remove the temporary diagnostic logging and connection-pool inspection code while preserving the remediation. Do not update the incident record yet.

Review the final production changes in the IDE diff. Confirm that the diff contains the remediation and focused test, but no temporary diagnostic code or unrelated changes.

Then, in a separate terminal, run:

```bash
cd backend
mkdir -p target/incident-evidence
set -o pipefail
./mvnw -DskipTests compile 2>&1 | tee target/incident-evidence/final-compile.log &&
./mvnw test 2>&1 | tee target/incident-evidence/final-full-suite-test.log
```

These commands save the final clean-state compile and full-suite outputs under `backend/target/incident-evidence/`. The test suite runs only if compilation succeeds, and `pipefail` prevents `tee` from hiding a failed Maven command.

After both commands succeed, craft a final prompt that directs Copilot to update the incident record with the verified cause, implemented remediation, final verification evidence, resolution status, and any remaining uncertainty.

The diagnostic runtime log from Step 4, the focused-test output from Step 9, the full-suite output from Step 10, the post-fix runtime log from Step 11, and the final clean-state compile and test outputs from Step 12 remain the authoritative evidence. The incident record is the final summary and conclusion grounded in those sources.

The final production state should ensure:

- the remediation is still in place;
- the focused test is still in place;
- all temporary diagnostic code has been removed;
- no unrelated changes remain.

## Lab 2 stop point

Stop when the final code state is clean, tests pass, the post-fix runtime evidence supports the remediation, and the incident record reflects what was verified.

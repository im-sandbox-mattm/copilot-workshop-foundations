# Module 05: Secure Coding With GitHub Copilot

## What You Will Practice

- diagnosing a trust boundary and dangerous sink before asking Copilot to change anything
- proving a vulnerability before fixing it, and re-proving it is closed afterward
- writing your own security-explicit prompts and task contracts, not filling in a supplied one
- distinguishing a cosmetic fix from a structural remediation, and a test that proves containment from one that merely rejects a single payload
- using Copilot Chat as a security reviewer that must cite evidence, not adjectives
- authoring scoped instruction files from the failures you actually observed, and testing whether they influence output
- comparing manual review, Copilot review, and `/security-review` (or its fallback) as complementary layers
- threat-modeling an agent/tool integration using the Lethal Trifecta and OWASP ASI categories, without connecting a live MCP server

## Module Focus

This lab is intermediate-to-advanced. It assumes you can already recognize common vulnerability classes on sight. The goal is not to re-teach OWASP; it is to practice the workflow:

**diagnose with evidence → prompt with intent → prove the fix → challenge the proof → encode the guardrail → verify across layers**

Expect ambiguity. Several exercises intentionally withhold the vulnerability name, the exact prompt, or the completed instruction file. Deciding what the security property is, and what would count as proof that it holds, is the point of the exercise.

## Why This Matters In A Regulated Environment

The vulnerable code in this lab is deliberately realistic. A template preview endpoint and an administrative preview panel are plausible features in applications that generate customer-facing communications.

The files and review outputs you create in this lab are version-controlled artifacts. When retained, reviewed, and validated under your organization's controls, artifacts like these can contribute to an approved evidence process. This lab is not itself a compliance control; it builds the developer-level habits that make those controls effective.

## IDE Notes

- **VS Code:** Add the target file to the Copilot Chat context, or confirm it is the active file. Use **Ask** for an explanation, **Agent** for remediation.
- **IntelliJ:** Same workflow in Copilot Chat. If controls differ in your installed plugin, use the closest available equivalent to Ask or Agent.
- If you start a new chat session, reattach the target files and paste your complete task prompt. Do not rely on a previous conversation's context carrying forward.

## Mode Guidance

| Exercise | Mode |
|---|---|
| 1 — Rapid triage | Ask, then Agent for the frontend warm-up fix |
| 2 — Backend remediation with proof | Agent |
| 3 — Adversarial patch review | Ask |
| 4 — Derive scoped instructions | Ask for the baseline/comparison runs; author the instruction file directly |
| 5 — Compare review layers | Ask, plus Copilot CLI or the Copilot app if available |
| 6 — Threat-model an agent integration | Ask (no external services required) |

### Timing

Setup and starting both applications happen **before** the lab clock starts — see Before You Start. Once both applications are running, the technical scope fits about 50 minutes:

- Exercise 1: 8 minutes
- Exercise 2: 14 minutes
- Exercise 3: 3 minutes
- Exercise 4: 8 minutes
- Exercise 5: 7 minutes for the required manual-versus-Ask comparison. Treat `/security-review` as an optional extension, not part of the timed 7 minutes.
- Exercise 6: 10 minutes

If you are short on time, Exercise 2 is the one exercise you should not compress.

### Prompting Approach

For Exercise 2, build your own security-explicit prompt rather than a generic repair request. A strong prompt for this kind of task usually establishes, in your own words:

- the trust boundary and which value is attacker-controlled
- the security property that must hold no matter what value arrives at that boundary
- the behavior that must be preserved for legitimate use
- one or two patterns that would not count as a fix
- the scope you want Copilot to touch, and nothing beyond it
- how you or Copilot will verify the result

Do not ask Copilot to inspect, rewrite, or refactor unrelated parts of the repository. A tightly scoped prompt makes the proposed change easier to review and gives you concrete criteria for accepting or rejecting it.

---

## Before You Start

Open the `copilot-workshop-foundations` repository.

Create a clean working branch from the Module 05 starter tag:

```bash
git switch -c workshop/module-05 module-05-start-v2
```

If the working branch already exists locally, switch to it instead:

```bash
git switch workshop/module-05
```

Open these files:

- `frontend/src/security/OwnerNoticePreview.tsx`
- `backend/src/main/java/com/workshop/petcareops/security/TemplatePreviewController.java`

Do not read ahead into later exercises before you reach them; several depend on you not already knowing the answer.

### Prerequisites

Confirm that Node.js, npm, and Java are available:

```bash
node --version
npm --version
java --version
```

A global Maven installation is not required. The backend includes the Maven Wrapper, which downloads and uses the repository's configured Maven version.

### Confirm The Starter Projects Build

Before making any changes, confirm both starter projects build successfully. Run these from the repository root.

**Frontend**

```bash
cd frontend
npm ci
npm run lint
npm run build
cd ..
```

**Backend — macOS or Linux**

```bash
cd backend
./mvnw test
cd ..
```

**Backend — Windows PowerShell**

```powershell
cd backend
.\mvnw.cmd test
cd ..
```

The first Maven Wrapper run may take longer while it downloads Maven and project dependencies. Resolve any environment or build failures now, so later failures can be attributed to your changes rather than the initial setup.

### Start Both Applications

Do this now, before the lab clock starts. Both applications need to stay running for the rest of the lab.

Open three terminals from the repository root.

**Terminal 1 — Backend**

```bash
cd backend && ./mvnw spring-boot:run
```

(Windows: `cd backend` then `.\mvnw.cmd spring-boot:run`.) Leave running until Spring Boot reports it has started.

**Terminal 2 — Frontend**

```bash
cd frontend && npm run dev
```

Leave running. Open the local URL Vite prints.

**Terminal 3 — Verification commands**

Keep this free for lint, build, test, and Git commands for the rest of the lab.

---

## Lab 1: Prove, Remediate, Challenge The Evidence

*Exercises 1–3 — approximately 25 minutes.*

## Exercise 1: Rapid Triage

### Scenario

Two internal admin tools ship together: an owner-reminder preview panel (`OwnerNoticePreview.tsx`) and a message-template preview endpoint (`TemplatePreviewController.java`). Both take input from an internal user and use it in a way that was never validated against what an untrusted or malicious value could do.

### Your Task

For each file, without changing any code yet, produce a short triage note:

- the trust boundary — where does attacker-influenced input enter, and where does it end up?
- the dangerous operation — what does the code do with that value?
- one exploit hypothesis — what could go wrong, concretely?
- the security property that should hold regardless of input

Then, for the frontend file only, demonstrate the behavior live (steps below).

### Constraints

Eight minutes, strict — this covers both files' triage notes and the frontend fix below. This is diagnosis first, not remediation; do not ask Copilot to fix anything until your triage notes are written.

### Prove The Frontend Behavior

Both applications should already be running from Before You Start. In the browser, scroll to **Internal Admin Preview** and enter this as the custom note:

```text
<img src=x onerror="alert('XSS demonstration')">
```

Confirm the alert executes. Use only this benign demonstration payload.

### Required Evidence

A one-paragraph triage note per file, citing the specific line or expression, not a vulnerability name copied from memory.

<details>
<summary>Hint if blocked — frontend</summary>

Look at how the two state values reach the DOM. Is there a step between "user typed this" and "browser renders this" that would normally neutralize special characters?

</details>

<details>
<summary>Hint if blocked — backend</summary>

Look at how the request parameter is used to build the value passed to the file-read call. Is there anything that checks where the resulting path actually points?

</details>

### Fix The Frontend As A Warm-Up

Once your triage is written, use **Agent** to remediate `OwnerNoticePreview.tsx`. Preserve the visible wording and layout; the fix should change *how* content reaches the DOM, not merely rename a variable or wrap the call in a try/catch. This feature only needs to display plain text — a sanitization library is not an appropriate fix here, and none is a project dependency.

After Copilot proposes a change, run:

```bash
cd frontend
npm run lint
npm run build
cd ..
```

Then refresh the browser and repeat the same payload. It should render as literal text with no alert. If it still executes, hard-refresh the browser; if that does not resolve it, restart the dev server (`Ctrl+C`, then `npm run dev` again in Terminal 2).

---

## Exercise 2: Backend Remediation With Proof

### Scenario

`TemplatePreviewController.java` accepts a `templateName` request parameter and reads a file from an approved template directory. Nothing currently establishes that the resolved file actually stays inside that directory, and the error path returns exception details to the client.

### Your Task

Write your own security-explicit prompt (see Prompting Approach above) and use it in **Agent** to remediate the endpoint. Then add focused tests that prove the remediation, not just that one specific bad input was rejected.

Take two minutes to draft your prompt before submitting it — the goal is practicing how you formulate the constraint, not pasting a template.

Your prompt is your decision to make, but before writing it you should be able to answer:

- Where does the real security boundary belong — on the string itself, or on where it resolves to?
- What is the smallest change that enforces that boundary regardless of the input's format or encoding?
- What should a client see on rejection, and what should never appear in that response?
- What test would prove containment, as opposed to proving only that one specific bad string was rejected?

### Constraints

Keep changes scoped to `TemplatePreviewController.java` and directly relevant tests. Do not add authentication or authorization — the starter application has no security dependency for it, and inventing one may not compile or may assume a role model the application doesn't have. If Copilot raises missing auth, record it as an architectural note rather than implementing it here.

### Required Evidence

Add or update focused tests under:

```text
backend/src/test/java/com/workshop/petcareops/security/TemplatePreviewControllerTest.java
```

Your test matrix must go beyond a single traversal string. At minimum:

- an approved template can still be read successfully
- a traversal attempt toward a file **outside** the approved directory, but with an allowed extension, is rejected

  A fixture is provided at `workshop-assets/outside/unauthorized-template.html` for exactly this purpose. A test that only tries `../../etc/passwd` can pass for the wrong reason — for example, because an extension check rejects anything without `.html`, not because containment was actually verified. If your test would still pass after temporarily deleting any containment check, it does not prove containment.

- the client-facing error response contains no exception message or stack trace

Optional stretch: a request for a directory instead of a file; a symlink created inside the approved directory that resolves outside it (symlink creation is less portable on Windows — skip if your environment doesn't support it easily); choosing and justifying an appropriate non-success HTTP status for a rejected traversal attempt, and verifying both the status and the response body (the controller currently returns a bare `String`, so even a generic rejection message could still be returned with a 200 status).

For each test, write one line stating which security property it actually proves.

### Verification

```bash
cd backend
./mvnw test
cd ..
```

Confirm `BUILD SUCCESS`. A remediation that has not been re-tested is a hope, not a verified fix.

<details>
<summary>Hint if blocked</summary>

Blocklisting the literal string `..` is not sufficient — traversal values can use alternate encodings and representations. The property you need is: after resolving the untrusted value against the approved directory, does the result still live inside that directory? That check has to happen against the resolved location, not the raw input text.

</details>

---

## Exercise 3: Adversarial Patch Review

### Scenario

Copilot's first answer to a security prompt is not always its best one. Below is a patch that looks like a reasonable response to a traversal report.

```java
@GetMapping("/preview")
public String previewTemplate(@RequestParam String templateName) {
    Path base = Path.of("..", "workshop-assets", "message-templates").normalize();
    Path candidate = base.resolve(templateName).normalize();

    if (!candidate.toString().endsWith(".html")) {
        return "Unsupported template type";
    }

    try {
        return Files.readString(candidate);
    } catch (IOException exception) {
        return "Template not found";
    }
}
```

Alongside it, a test that was submitted as proof the fix works:

```java
@Test
void rejectsTraversal() {
    // Requests templateName=../../etc/passwd
    // Expects the request to be rejected.
}
```

### Your Task

In **Ask**, without applying this code to the project, determine:

- what this patch actually fixes
- which safeguards are real but insufficient, and which security property they fail to establish
- which claim is unproven
- the smallest additional change needed
- the exact test that would expose the remaining gap

### Constraints

Do not add this snippet to the codebase — this is a review exercise, not an implementation task.

### Required Evidence

A short written verdict covering the five points above, with the specific line of the patch cited for each claim.

<details>
<summary>Hint if blocked</summary>

The traversal test above passes — but would it still pass if the `.html` extension check were the *only* thing rejecting the payload, with no directory-containment check at all? What request would tell you the difference?

</details>

---

## Lab 2: Encode, Compare, Threat-Model

*Exercises 4–6 — approximately 25 minutes.*

## Exercise 4: Derive Scoped Instructions

### Scenario

Everything you enforced by hand in Lab 1 — the containment check, the generic error response, the scope discipline — depended on you remembering to ask for it. An instruction file encodes that guidance into version-controlled guardrails that Copilot can draw on when matching files are involved on supported surfaces.

### Baseline Run

1. Open or attach `TemplatePreviewController.java`.
2. Start a clean Copilot Chat session with **Ask**, and record which model you selected.
3. Submit exactly:

   ```text
   Write a Spring Boot endpoint that serves template files for preview.
   ```

4. Save the response without applying it. Note which controls are present, which are missing, and which claims would need verification.

### Your Task

Create `.github/instructions/backend-security.instructions.md` (and optionally a frontend equivalent) yourself. Derive 3–5 rules directly from what you personally observed failing or succeeding in Lab 1 — do not start from a supplied ruleset.

Before writing the file, decide:

- Which rules belong in repository-wide guidance versus a path-scoped file?
- Which rules can be expressed as Copilot guidance, and which security properties still require a deterministic test, pipeline gate, or runtime control instead?
- What is the narrowest `applyTo` glob that still matches the code this applies to?

Start from this frontmatter scaffold rather than a supplied ruleset:

```markdown
---
applyTo: "<choose the narrowest applicable glob>"
---

# Security guidance

<!-- Derive 3-5 rules from Lab 1 -->
```

### Comparison Run

Repeat the identical baseline prompt in a new clean session, same model, with the same file attached. Compare the two responses. Path-scoped instructions apply when Copilot is working in the context of a file matching the `applyTo` glob — merely asking the question without attaching a matching file may not activate it.

### Required Evidence

A short note on which rules appear to have shifted the output, which were missed, and which claims still require a deterministic test rather than guidance. Instructions are guidance supplied to the model, not an enforcement control — that distinction is the actual learning objective here, not whether the second response was "more secure."

---

## Exercise 5: Compare Review Layers

### Scenario

Manual review, Copilot-as-reviewer, and `/security-review` each catch different things and can each miss different things.

### Your Task

In a new **Ask** session, attach both lab files and submit:

```text
Review these two implementation files for security risks and evaluate the current remediations. You may inspect directly relevant focused tests solely to evaluate whether the remediations are adequately verified. Do not expand into unrelated files or architecture.

For each finding, provide:
1. the untrusted input or trust boundary;
2. the vulnerable operation;
3. the most specific applicable CWE identifier and name;
4. where it can be verified, the corresponding OWASP Top 10:2025 category;
5. the specific file and code evidence;
6. the smallest remediation;
7. a deterministic way to verify the fix.

Identify whether each finding is: present in the current implementation; addressed by the current remediation; partially addressed; or not established from the available code.

Use CWE as the primary classification. Do not guess an OWASP mapping from memory — if you cannot verify it against the official OWASP Top 10:2025 mapped-CWE lists, label it "not verified." Cite the official category page or mapped-CWE list for any mapping you do assert.

When evaluating tests, identify the exact security control or code branch each test exercises, and note when a test could pass because of a different validation rule rather than the one it claims to prove.

Distinguish confirmed findings from hypotheses. Do not provide generic advice or invent architecture the code doesn't establish.
```

The required comparison for this exercise is manual review versus Copilot Ask review, captured in the table below.

If `/security-review` is available in Copilot CLI (`copilot`, then `/help` to confirm it's listed) or the Copilot app, you may optionally run it against your Lab 1 changes as an additional layer beyond the required comparison — check your own version and environment, since availability can vary. It reviews active local changes and is not a full repository security audit; a clean result is not proof the code is secure.

If you save the review prompt as `.github/prompts/security-review.prompt.md` for future reuse, do not count that as an independent verification layer — it is the same prompt, model, and review process packaged for reuse, not a second reviewer. Prompt files are fully supported in VS Code and Visual Studio, currently under preview in JetBrains IDEs and Xcode, and not supported on GitHub.com or in Copilot CLI.

### Required Evidence

A short comparison table:

| Finding | Manual review | Copilot Ask review | `/security-review` (optional) | Confirmed by deterministic test? |
|---|---|---|---|---|

The output you're after is not "which layer won." It's: which layer found what, which claims were unsupported until a test confirmed them, and what would you actually submit as audit evidence.

<details>
<summary>Optional stretch — STRIDE</summary>

If time remains, ask Copilot for a brief, feature-scoped STRIDE pass over the same two files and their known data flow (user input → frontend render, request parameter → backend file read). Require it to cite code evidence per category and to mark anything unsupported as "not established from the available code" rather than inventing authentication, storage, or deployment architecture the code doesn't show.

</details>

---

## Exercise 6: Threat-Model An Agent Integration

### Scenario

Agent and MCP integrations raise a different class of risk than the code you've been reviewing: an agent that reads untrusted content, has access to sensitive data, and can reach the network can leak that data even when every line of application code is secure. This is the Lethal Trifecta, and it is why agentic risk cannot be solved by prompting alone.

### Environment And Security Boundary

This exercise does not require you to install, connect, or authorize an MCP server, and you should not do so unless a server has already been approved through your organization's security process, is permitted in your current environment, and your instructor or organizational policy explicitly authorizes the connection. Do not connect an unapproved public or community MCP server for this lab.

Use the representative configuration below as a design-review artifact only.

```yaml
# Representative configuration for threat-modeling only.
# Do not install or execute.

agent:
  identity:
    type: service-account
    scope: payments-platform

  data_access:
    - gitlab_projects: payments/*
    - merge_requests: read
    - issues: read
    - local_workspace: read

  tools:
    - name: repository_search
      access: read
    - name: merge_request_update
      access: write
    - name: external_http
      access: unrestricted

  approvals:
    required_for:
      - merge

  untrusted_inputs:
    - issue_body
    - merge_request_description
    - code_comments
    - tool_output
```

### Your Task

Working from this configuration alone, produce:

1. which of the three Lethal Trifecta legs are present, and the specific field that establishes each
2. which OWASP Agentic Security Initiative (ASI) categories apply
3. the likely blast radius if the agent is manipulated through one of the listed untrusted inputs
4. the one leg you would cut first, and why
5. the specific control that cuts it, and who owns that control — GitHub Enterprise policy, endpoint/MDM policy, the MCP server or runtime itself, network egress control, or GitLab/Bitbucket IAM
6. how you would verify that control is actually enforced, not merely configured

### Optional Approved-Sandbox Extension

If your organization provides an already-approved sandbox with a pre-vetted MCP server, you may compare your design-review findings against that server's actual tool manifest and permission model. This is optional and should not be treated as required to complete the exercise.

### Required Evidence

Fill in this decision template yourself — do not copy an example answer:

```text
We would cut the __________ leg by __________.

This control is owned by __________.

We would verify enforcement by __________.

The following listed control would not be sufficient on its own because __________.
```

<details>
<summary>Hint if blocked</summary>

The three legs are: access to private/sensitive data, exposure to untrusted content, and a path to exfiltrate data externally. You don't need all three removed — removing any one of them breaks the attack chain. Which field above gives the agent that third leg, and is it something the agent identity actually needs for its stated purpose?

</details>

---

## Apply The Pattern To Your Codebase

If you finish early, choose one:

- trace the template-preview feature across every file involved in this lab — controller, tests, fixture, and error handling — and produce one evidence-backed finding you have not yet documented; this is a bounded feature review, not a full-repository scan
- run a timeboxed (10 minute) repository triage: identify the two highest-confidence risks elsewhere in the codebase, each with a trust boundary, code evidence, CWE, smallest remediation, and a deterministic way to prove it
- adapt your Exercise 4 instruction files to your own team's stack and security requirements, keeping the scope narrow and tied to real code paths
- open a file from your own codebase, if accessible, and run the Exercise 5 review prompt or `/security-review` against it
- review community security instruction templates and identify what would actually apply to your environment, without copying a large ruleset wholesale:
  - `github.com/github/awesome-copilot/blob/main/instructions/security-and-owasp.instructions.md`
  - `github.com/Robotti-io/copilot-security-instructions`

---

## If You Get Stuck

- reduce the task to one vulnerability, or one test, at a time
- ask Copilot to explain the risk before asking for remediation
- compare changed code against the Required Evidence for that exercise instead of trusting the first draft
- if you start a new session, reattach the target files and paste your complete task prompt
- **Exercise 4 instructions not appearing to influence output:** confirm the `applyTo` glob matches the attached file's path; confirm the file is attached, selected, or referenced; confirm it was saved before the comparison run; confirm both runs used the same model and the identical prompt from a clean session
- **frontend fix not taking effect:** hard-refresh the browser; if that fails, stop the dev server with `Ctrl+C` and run `npm run dev` again
- **`/security-review` not listed:** do not troubleshoot preview access during lab time — use the prompt-file fallback in Exercise 5
- **symlink stretch test in Exercise 2:** skip on Windows if symlink creation requires elevated permissions in your environment

## Key Takeaways

- a structural remediation changes the unsafe data flow; a cosmetic change merely rearranges it
- a test can pass for the wrong reason — proving containment requires a payload that would only be rejected by the control you actually care about
- Copilot is useful as a reviewer and threat-modeling partner, not only as a code generator, but its findings still need evidence and deterministic verification
- instruction files are guidance supplied to the model on supported surfaces; they are not an enforcement control
- `/security-review` and manual review are complementary layers, not competitors — neither alone is a complete audit trail
- agentic risk — private data, untrusted content, and an exfiltration path together — is an architecture decision, not something a prompt or an instruction file can fix
- when the first answer is weak, reject and redirect with explicit constraints
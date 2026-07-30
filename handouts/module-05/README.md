# Module 05: Secure Coding With GitHub Copilot

## What You Will Practice

- using security-explicit prompts that shift Copilot output toward safer code
- proving a vulnerability before fixing it, and re-proving it is closed afterward
- identifying the difference between a cosmetic fix and a structural remediation
- using Copilot Chat as a security reviewer that must cite evidence, not adjectives
- recognizing when to reject and redirect insecure AI output, and formulating your own constraints
- encoding security guidance into scoped instruction files and testing whether they influence output
- using `/security-review` as one review layer among several, and comparing it against manual review

## Module Focus

The goal is not to teach OWASP from scratch. The goal is to build muscle memory for the security workflow introduced in the workshop:

**prompt securely → review structurally → encode guardrails → verify**

Every exercise reinforces one of those four stages.

## Why This Matters In A Regulated Environment

The vulnerable code in this lab is deliberately realistic. A template preview endpoint and an administrative preview panel are plausible features in applications that generate customer-facing communications.

In an applicable regulated workflow, vulnerabilities in preview and file-access features may affect systems that fall within security or compliance scope. The files and review outputs you create in this lab are version-controlled artifacts that may contribute to an approved evidence process when they are retained, reviewed, and validated under your organization's controls.

The exercise is not itself a compliance control. It builds the developer-level habits that make those controls effective.

## IDE Notes

- **VS Code:** Open the target file and add it to the Copilot Chat context, or confirm that it is the active file. Use **Ask** for an explanation if needed, then use **Agent** for remediation.
- **IntelliJ:** Follow the same workflow in Copilot Chat. If the controls differ in your installed plugin, open or attach the target file and use the closest available equivalent to Ask or Agent.
- Context behavior depends on the IDE and whether you remain in the same conversation. If you intentionally start a new session, reattach the target files and paste the complete task prompt rather than relying on previous conversation context.

## Mode Guidance

- Exercise 1: **Agent** with a scaffolded security-explicit prompt; use **Ask** first only if you need an explanation of the vulnerable code
- Exercise 2: **Agent** — write your own security-explicit prompt from scratch
- Exercise 3: **Ask** — perform a brief, evidence-based review
- Exercise 4: **Agent** — reject and redirect an incomplete response; stretch if time is tight
- Exercise 5: **Ask** for the controlled baseline and comparison; create the instruction file manually
- Exercise 6: **Copilot CLI** or the **Copilot app** — stretch, with a reusable prompt-file fallback

### Timing

Lab 1 runs about 25 minutes. Exercises 1 and 2, including their verification steps, are the required core. Exercise 3 is required but brief: one review pass, not an exhaustive audit.

Exercise 4 and the STRIDE portion of Exercise 3 are stretch activities if time is limited.

Lab 2 runs about 25 minutes. Exercise 5 is required. Exercise 6 is stretch, with a fallback that everyone can complete.

### When To Plan First vs. Go Straight

Exercises 1–2 are narrow-scope, single-file fixes, so use Agent with a targeted security prompt. An optional Ask step may be used when an explanation is needed before editing.

Exercise 3 is review-only and stays in Ask. Exercise 4 is iterative refinement in Agent. Exercises 5–6 are controlled comparison and scanning activities using Ask and the CLI, respectively.

### Prompting Approach

For Exercises 1–2, use a targeted, security-explicit prompt rather than a generic repair request.

Keep the task scoped to the target file or method and state:

- the vulnerability or unsafe trust boundary
- the specific dangerous operation or pattern
- the required security control
- the behavior that must be preserved
- any unsafe pattern that must not remain
- the command or test that will verify the remediation

Do not ask Copilot to inspect, rewrite, or refactor unrelated parts of the repository. A tightly scoped prompt makes the proposed change easier to review, reduces unnecessary exploration, and gives you concrete criteria for accepting or rejecting the result.

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

Optionally open `.github/copilot-instructions.md` to see which repository-level instructions already exist.

Complete the exercises in order. Each exercise includes its own prompt, checkpoint, and verification steps so you can work through the lab independently.

### Prerequisites

Confirm that Node.js, npm, and Java are available:

```bash
node --version
npm --version
java --version
```

A global Maven installation is not required. The backend includes the Maven Wrapper, which downloads and uses the repository's configured Maven version.

### Confirm The Starter Projects Build

Before making any changes, confirm that both starter projects build successfully.

Run these commands from the repository root.

#### Frontend

```bash
cd frontend
npm ci
npm run lint
npm run build
cd ..
```

#### Backend — macOS or Linux

```bash
cd backend
./mvnw test
cd ..
```

#### Backend — Windows PowerShell

```powershell
cd backend
.\mvnw.cmd test
cd ..
```

The first Maven Wrapper run may take longer while it downloads Maven and project dependencies.

Resolve any environment, dependency, or build failures before beginning the exercises. This establishes a clean baseline so later failures can be attributed to the lab changes rather than to the initial setup.

---

## Lab 1: Prove, Fix, Prove

*Exercises 1–4 — approximately 25 minutes. Exercises 1–2 are required with verification. Exercise 3 is brief. Exercise 4 and STRIDE are stretch activities.*

---

## Exercise 1: Fix The Frontend XSS Risk

### Goal

Use a security-explicit prompt to remediate unsafe HTML rendering.

This exercise provides a scaffolded prompt so you can see the structure before writing your own prompt in Exercise 2.

### Context

`OwnerNoticePreview.tsx` is an internal administrative tool that lets reception staff preview reminder wording before it is sent to pet owners.

The component takes user input, including an owner name and a custom note, interpolates those values into an HTML string, and injects that string into the DOM through `dangerouslySetInnerHTML`.

This is a DOM-based XSS vulnerability: user-controlled values reach an HTML sink without escaping.

If a staff member, or an attacker with access to the administrative panel, enters the following as the custom note, the event handler executes in the browser:

```text
<img src=x onerror="alert('XSS demonstration')">
```

### Start The Application And Prove The Vulnerability

Before asking Copilot to modify the component, open three terminal tabs or windows. Start each one from the repository root.

#### Terminal 1 — Backend Server

macOS or Linux:

```bash
cd backend
./mvnw spring-boot:run
```

Windows PowerShell:

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Leave this terminal running. Wait until Spring Boot reports that the application has started.

#### Terminal 2 — Frontend Development Server

```bash
cd frontend
npm run dev
```

Leave this terminal running. Open the local URL printed by Vite in your browser.

#### Terminal 3 — Verification Commands

Leave this terminal available for one-off commands such as lint, build, tests, and Git checks.

Unless a later step says otherwise, run commands in Terminal 3 from the repository root.

With the application running:

1. scroll to the **Internal Admin Preview** section
2. enter the following as the custom note:

```text
<img src=x onerror="alert('XSS demonstration')">
```

3. confirm that the alert executes

This establishes the vulnerable behavior before remediation.

Keep Terminals 1 and 2 running so you can repeat the same browser test after applying the fix.

Use only this benign demonstration payload. Do not test unrelated or destructive payloads.

### Why A Rename Is Not A Fix

A common weak fix is renaming the variable or wrapping the `dangerouslySetInnerHTML` call in a try-catch. Neither addresses the vulnerability.

The fix must change *how* the content reaches the DOM.

Because this feature only needs to display an owner name and a plain-text note, the expected fix is to stop building an HTML string and render the values through normal JSX expressions, which React escapes by default.

A sanitization library is appropriate only when rendering rich HTML is an explicit, approved requirement. That is not the case for this feature, and DOMPurify is not a dependency in the project. Adding a sanitizer would preserve an HTML-rendering pathway that the exercise does not require.

### Steps

1. open `frontend/src/security/OwnerNoticePreview.tsx`
2. add the file to the Copilot Chat context, or confirm that it is the active file in your editor
3. before prompting, identify:
   - which values are user-controlled
   - where those values are rendered as HTML
   - what a structural fix would change
4. submit the scaffolded security prompt below in **Agent**
5. review the proposed changes before accepting them
6. determine whether the fix structurally changes how content reaches the DOM or merely rearranges the unsafe pattern
7. if the fix is cosmetic or incomplete, reject it and use the follow-up prompt below

### Scaffolded Security Prompt

```text
This React component has a DOM-based XSS vulnerability. User-controlled values, including the owner name and custom note, are interpolated into an HTML string and injected into the DOM through dangerouslySetInnerHTML.

Fix the component so it renders the preview safely. Do not use dangerouslySetInnerHTML in any form.

Preserve the visible wording and layout, but treat the owner name and custom note as plain text rather than executable markup. Render them through normal JSX expressions that React escapes by default.

Keep the change scoped to this component. Do not inspect, rewrite, or refactor unrelated files.

After making the change, run npm run lint and npm run build from the frontend directory and report whether they pass.
```

Notice the structure:

- it names the vulnerability class
- it identifies the dangerous rendering pattern
- it provides a negative constraint
- it states the behavior that must be preserved
- it limits the requested scope
- it names the verification steps

### Follow-Up If The Fix Is Weak

If the first output still uses unsafe rendering or only renames variables:

```text
The fix still renders user input as HTML.

Remove every raw HTML rendering path from this component. Render the owner name and custom note as plain text using React's default escaped output, through standard JSX expressions such as {ownerName}.

Do not use dangerouslySetInnerHTML, an HTML string, or a new sanitization dependency.

Do not modify unrelated files or refactor unrelated component behavior.
```

### Checkpoint

The expected fix:

- removes the HTML-string construction
- removes `dangerouslySetInnerHTML`
- renders the owner name and custom note through normal JSX expressions
- preserves the expected visible wording and layout

A weak fix will:

- rename the HTML variable but still use `dangerouslySetInnerHTML`
- wrap the unsafe rendering in a try-catch
- add a comment saying "sanitize before production" without changing the rendering path
- add a sanitizer even though the feature requires only plain text
- modify unrelated files or component behavior

### Verify The Remediation

#### Confirm The Frontend Build

Copilot was instructed to run the frontend lint and build commands. Review its terminal output and confirm that both commands passed.

If Copilot did not run them, or you need to repeat the verification yourself, use **Terminal 3**:

```bash
cd frontend
npm run lint
npm run build
cd ..
```

The final `cd ..` returns Terminal 3 to the repository root.

Do not run these commands in Terminal 2, because that terminal should remain occupied by the frontend development server.

#### Re-Test The Browser Behavior

After accepting the code change, allow the frontend development server a moment to reload the modified component, then refresh the browser.

Enter the same payload again:

```text
<img src=x onerror="alert('XSS demonstration')">
```

Confirm that:

- no alert appears
- the payload displays as literal text
- the owner name and note retain the expected visible wording and layout

Seeing the payload rendered as visible text rather than executed proves that the unsafe DOM sink has been removed.

If the previous behavior still appears:

1. perform a hard refresh in the browser
2. if the alert still executes, stop Terminal 2 with `Ctrl+C`
3. restart the frontend development server in Terminal 2:

```bash
npm run dev
```

4. refresh the browser and repeat the payload test

Because Terminal 2 was already in the `frontend` directory, you do not need to run `cd frontend` again before restarting the development server.

A backend restart should not normally be required for this frontend-only code change.

When Exercise 1 is complete, you may leave the servers running for later exploration or stop Terminals 1 and 2 with `Ctrl+C`.

---

## Exercise 2: Fix The Backend File Preview Endpoint

### Goal

Use Copilot to remediate path traversal and exposed error details in a Spring controller.

This time, write your own security-explicit prompt from scratch. No complete prompt is provided.

### Context

`TemplatePreviewController.java` is a Spring Boot endpoint that lets administrators preview message templates.

It takes a `templateName` request parameter and reads a file from:

```text
../workshop-assets/message-templates/
```

The method contains two distinct vulnerabilities.

### Path Traversal — CWE-22

The `templateName` parameter is used directly in path construction without verifying that the resolved file remains inside the approved template directory.

A traversal value such as:

```text
../../etc/passwd
```

or an encoded equivalent may cause the application to read a file outside the approved directory, depending on the operating environment and request decoding.

### Information Disclosure — CWE-209

The catch block returns exception details and a stack trace to the client.

This may expose:

- internal file paths
- Java and library details
- implementation structure
- other information useful when planning a deeper attack

### Write Your Own Prompt

Write a security-explicit prompt based on the three tactics from the workshop.

1. **Security perspective:** establish the security-review role or priority
2. **Scenario warning:** describe what the endpoint does and why the file boundary matters
3. **Specific constraints:** identify the vulnerabilities and require the appropriate controls

Your prompt should address:

- an absolute, normalized approved base directory
- resolution of the untrusted value against that base
- normalization of the candidate path
- validation that the candidate remains inside the approved base directory
- an appropriate file-type allowlist
- a generic client-facing error response
- focused tests or another deterministic verification method
- a narrow file and change scope

Take two minutes to draft your prompt before submitting it. The goal is to practice *formulating* security constraints, not merely pasting them.

### Scope Note: Authentication And Authorization

Authentication and authorization are legitimate architectural concerns for an endpoint like this.

Do not ask Copilot to implement them in this exercise. The starter application does not include Spring Security, so an authorization annotation may not compile, may introduce an unapproved dependency, or may invent a role model that the application does not establish.

If Copilot raises missing authentication or authorization, record it as an architectural concern rather than implementing it in this lab.

### Steps

1. open `backend/src/main/java/com/workshop/petcareops/security/TemplatePreviewController.java`
2. add the file to the Copilot Chat context, or confirm that it is the active file in your editor
3. review the `previewTemplate` method and identify both vulnerabilities before asking Copilot
4. draft your own security-explicit prompt
5. keep the requested changes scoped to:
   - `TemplatePreviewController.java`
   - directly relevant focused tests
6. submit the prompt in **Agent**
7. review the proposed changes before accepting them
8. verify that the result addresses both:
   - path traversal
   - information disclosure
9. confirm that the fix resolves the input against an approved base directory and verifies containment

Normalization alone is insufficient. The resolved path must be verified to remain inside the approved directory.

### Checkpoint

A good fix will:

- convert the base template directory to an absolute, normalized path
- resolve the untrusted `templateName` against that base
- normalize the candidate path
- verify that the candidate starts with the normalized base directory
- optionally confirm that the target is a regular file
- optionally restrict the file to approved extensions such as `.html`, `.txt`, or `.md`
- return a generic client-facing response without exception messages or stack traces
- keep unrelated application behavior unchanged

A fix that normalizes the path but does not check containment remains vulnerable.

A fix that protects the file path but still returns exception details has fixed only one of the two vulnerabilities.

Blocklisting the raw string `..` is not a sufficient defense. Traversal attempts can use alternate encodings and representations. The correct control is validation of the resolved path's containment.

### Add Focused Security Tests

In the same Agent session, ask Copilot to create or update a focused test for `TemplatePreviewController` under the corresponding backend test package.

A suitable target path is:

```text
backend/src/test/java/com/workshop/petcareops/security/TemplatePreviewControllerTest.java
```

Keep the tests limited to the template-preview security behavior.

The focused tests should prove that:

- an approved template can be read successfully
- a traversal request toward a file outside the approved directory is rejected
- the client response does not contain exception text or a stack trace

Review the generated tests before accepting them. Confirm that they assert observable behavior rather than merely duplicating the implementation.

### Verify The Remediation

Use Terminal 3 from the repository root.

#### macOS or Linux

```bash
cd backend
./mvnw test
cd ..
```

#### Windows PowerShell

```powershell
cd backend
.\mvnw.cmd test
cd ..
```

Confirm that Maven reports:

```text
BUILD SUCCESS
```

The final `cd ..` returns Terminal 3 to the repository root.

Copilot proposes the remediation. The tests prove the security behavior.

A remediation that has not been re-tested is a hope, not a verified fix.

---

## Exercise 3: Security Review And Threat Model

### Goal

Use **Ask** in Copilot Chat as a security reviewer and compare its findings against the vulnerabilities and remediations from Exercises 1–2.

The point is to identify:

- what the reviewer catches that you might have missed
- what it misses that you caught
- whether the proposed fixes introduce or leave behind additional risks

### Context

This exercise shifts Copilot from code generator to code reviewer.

Copilot's pattern recognition can be useful for identifying known vulnerability patterns in existing code, but its findings still require evidence, human review, and deterministic verification.

The OWASP-oriented review and STRIDE analysis provide two different lenses:

- **OWASP-oriented review:** specific and finding-oriented
- **STRIDE:** asset, trust-boundary, and abuse-scenario oriented

### Steps

1. keep the modified frontend and backend files open
2. open your IDE's Source Control or Git view
3. open each modified file to view the before-and-after diff
4. if your IDE does not display the diff clearly, use Terminal 3 from the repository root:

```bash
git diff -- frontend/src/security/OwnerNoticePreview.tsx
git diff -- backend/src/main/java/com/workshop/petcareops/security/TemplatePreviewController.java
```

5. review the original vulnerable code and the remediation together
6. start a new Copilot Chat session with **Ask** selected
7. attach or reference:
   - `OwnerNoticePreview.tsx`
   - `TemplatePreviewController.java`
8. submit the review prompt below
9. compare the response against:
   - the vulnerabilities you identified in the starter code
   - the fixes now present in the working tree
   - any remaining or newly introduced risks
10. note which findings are confirmed by code and which require additional context

### Review Prompt

```text
Review these two files for security risks and evaluate the current remediations.

For each finding, provide:
1. the untrusted input or trust boundary;
2. the vulnerable operation;
3. the CWE or OWASP category;
4. the specific file and code evidence;
5. the smallest remediation;
6. a deterministic way to verify the fix.

Identify whether each finding is:
- present in the current implementation;
- addressed by the current remediation;
- partially addressed; or
- not established from the available code.

Distinguish confirmed findings from hypotheses. Do not provide generic advice or invent application architecture.
```

### OWASP Review Checkpoint

The review should identify concrete risks in the supplied code rather than generic secure-coding advice.

If the output only says things such as "always validate inputs" or "always use parameterized queries," the supplied context or prompt was too broad.

Narrow the request to the specific files, inputs, sinks, and operations.

You should be able to answer:

- "Copilot the reviewer caught X that I missed while fixing the code."
- "Copilot the reviewer missed Y that I caught manually."
- "Copilot identified Z, but the available code does not establish whether it is a confirmed issue."

### STRIDE Threat Analysis — Stretch

STRIDE works best when the feature boundary, assets, actors, and data flow are explicit.

Use this feature-level model:

- an administrative or reception user enters an owner name and custom reminder note
- the frontend renders a reminder preview
- the backend receives a template name and reads a template from an approved directory
- user-controlled input crosses into browser rendering and backend file access
- relevant assets include:
  - reminder content
  - approved template files
  - the server filesystem
  - internal application and error details

Do not assume that the application has a particular identity provider, role model, database, deployment platform, or network architecture unless the supplied code establishes it.

In **Ask**, submit:

```text
Perform a brief, feature-scoped STRIDE threat analysis for the owner reminder preview and template preview functionality represented by the supplied files.

Use this known data flow:
1. an administrative or reception user enters an owner name and custom reminder note;
2. the frontend renders a reminder preview;
3. the backend receives a template name and reads a template from an approved directory;
4. user-controlled input crosses into browser rendering and backend file access.

Relevant assets include reminder content, approved template files, the server filesystem, and internal application details.

For each applicable STRIDE category:
- identify the asset or trust boundary involved;
- cite the specific code evidence;
- describe the plausible threat;
- recommend the smallest useful mitigation;
- provide a deterministic verification step.

Mark categories with no supported finding as "not established from the available code."

Separate confirmed threats from assumptions and architectural questions. Do not invent authentication, storage, deployment, or network architecture.
```

### STRIDE Checkpoint

The result should be a lightweight threat analysis grounded in the supplied feature and code, not six generic paragraphs merely because STRIDE contains six categories.

Compare the STRIDE output with the OWASP-oriented review:

- the OWASP review should emphasize concrete vulnerability patterns in the code
- STRIDE should connect those findings to assets, actors, trust boundaries, and abuse scenarios
- unsupported system-level risks should be labeled as assumptions or open questions

---

## Exercise 4: Reject And Redirect

### Goal

Practice steering Copilot away from insecure or incomplete output when the first answer is not good enough.

This time, formulate your own redirect constraints rather than using a provided remediation prompt.

### Context

The first response Copilot generates for a security task may be incomplete.

It might:

- normalize a path but omit the containment check
- protect the file path but continue returning exception details
- modify unrelated code
- provide a conceptual answer without a verifiable implementation
- add controls that the application architecture does not support

The reject-and-redirect pattern is:

1. review the first output
2. identify what is still missing
3. add explicit constraints
4. compare the revised response
5. verify the behavior

Specific negative constraints such as "do not return stack traces" close a particular unsafe path rather than leaving a broad instruction such as "handle errors safely" open to interpretation.

### Steps

1. start a new Copilot Chat conversation
2. select **Agent**
3. open or attach `TemplatePreviewController.java`
4. submit this deliberately broad prompt:

```text
Write a Spring Boot endpoint that serves template files for preview.
```

5. review the response before applying it
6. identify any missing security control, unsupported assumption, irrelevant change, or unverifiable claim
7. write a redirect prompt containing two or three specific constraints
8. compare the redirected response with the first response
9. if the result remains incomplete, add one more constraint and iterate

Useful redirect constraints may include:

- do not return stack traces or exception details to clients
- resolve the requested path against an approved base directory
- normalize the resolved path and reject it if it escapes the approved base directory
- do not inspect or modify unrelated files
- do not introduce authentication or authorization dependencies that the project does not already contain
- add a focused test that proves traversal is rejected

Prefer constraints that describe the correct control, such as validating containment, over string-blocklist constraints such as rejecting inputs containing `..`.

### Fallback If The First Response Is Already Secure

Copilot may produce a response that already includes the expected safeguards.

Do not try to force it to generate insecure code.

Instead, paste the intentionally incomplete example below into the conversation and ask Copilot to review and improve it. Do not add this fallback code to the project.

```java
@GetMapping("/preview")
public String previewTemplate(@RequestParam String templateName) {
    try {
        Path templatePath =
                Path.of("../workshop-assets/message-templates/", templateName)
                        .normalize();

        return Files.readString(templatePath);
    } catch (IOException exception) {
        return "Template not found";
    }
}
```

This example normalizes the path and returns a generic error, but it does not prove that the resolved path remains inside the approved template directory.

Ask Copilot to improve the example using your own redirect constraints.

### What To Look For

The progression should be visible. Each iteration should become more precise as the constraints become clearer.

A specific constraint such as:

```text
Resolve the requested path against an absolute, normalized approved base directory and reject the request unless the normalized candidate remains inside that directory.
```

provides clearer acceptance criteria than:

```text
Make the endpoint secure.
```

### Checkpoint

The second or third response should be more explicit, focused, and secure than the first.

You should be able to articulate:

- which weakness you identified
- which constraint you added
- how that constraint changed the response
- which claims remain unverified
- how you would deterministically verify the implementation

Save your redirect constraints in a note or scratch file. You will use them in Exercise 5.

---

## Lab 2: Encode And Verify

*Exercises 5–6 — approximately 25 minutes. Exercise 5 is required. Exercise 6 is stretch, with a fallback everyone can complete.*

---

## Exercise 5: Security Guardrails As Code

### Goal

Create a scoped security instruction file that encodes the prompting discipline from Lab 1 into version-controlled guidance.

Then test whether that guidance influences Copilot's output when a matching file is in context on a supported surface.

### Context

The reject-and-redirect constraints you wrote in Exercise 4 are valuable, but they depend on the developer remembering to include them in every prompt.

An instruction file encodes those constraints into version-controlled guidance that Copilot can include when matching files are involved on supported surfaces.

You will run a controlled comparison. The goal is to isolate one variable: the instruction file.

For this comparison, a **clean session** means starting a new Copilot Chat conversation with no messages or context carried over from the previous run. You do not need to restart the IDE.

If you reuse a conversation from Lab 1, the previous discussion of path traversal and stack traces may explain any improvement, and the comparison would not isolate the instruction file.

### Baseline Run — Before The Instruction File Exists

1. open or attach `TemplatePreviewController.java`
2. start a clean Copilot Chat session with **Ask** selected
3. select a fixed model and record which model you chose
4. submit this exact prompt:

```text
Write a Spring Boot endpoint that serves template files for preview.
```

5. save the response without applying any code changes
6. record:
   - which security controls appear
   - which controls are missing
   - whether the response stays within the target context
   - which statements would require verification

### Create The Scoped Instruction File

From the repository root, create this directory if it does not already exist:

```text
.github/instructions/
```

Create:

```text
.github/instructions/backend-security.instructions.md
```

Add:

```markdown
---
applyTo: "backend/src/main/java/**/security/**/*.java"
---

# Security rules for backend security-sensitive code

- Resolve user-controlled paths against an approved base directory; normalize and verify containment before reading.
- Do not expose exception or stack-trace details to clients; return a generic message.
- Keep changes scoped to the requested security-sensitive code and directly relevant tests.
- Run the relevant build and tests after modifying security-sensitive code.
```

Optionally create:

```text
.github/instructions/frontend-security.instructions.md
```

Add:

```markdown
---
applyTo: "frontend/src/security/**/*.tsx"
---

# Security rules for frontend security-sensitive code

- Never render user-controlled strings as raw HTML.
- Do not use dangerouslySetInnerHTML for plain-text content.
- Render user-controlled values through escaped JSX.
- Keep changes scoped to the requested component and directly relevant tests.
```

A tight scope containing only the rules the lab code exercises is deliberate.

It keeps the comparison clean so you can attribute any change in output to specific guidance rather than to a long ruleset covering unrelated topics such as SQL, cryptography, or secrets.

### Comparison Run — With The Instruction File Active

1. start another clean Copilot Chat session with **Ask** selected
2. use the same model as the baseline run
3. open or attach the same target file
4. submit the identical prompt:

```text
Write a Spring Boot endpoint that serves template files for preview.
```

5. look for the instruction file in the references, context, or custom-instructions information shown with the response
6. if your IDE does not expose this information, record that you could not directly confirm activation and treat the comparison as suggestive rather than conclusive
7. save the response without applying the implementation
8. compare the two responses

Path-specific instructions apply when Copilot is working in the context of a file that matches the `applyTo` glob.

Merely asking for an endpoint without attaching, selecting, or explicitly referencing a matching file may not activate the instruction file.

### Checkpoint

Compare whether and how the scoped instructions influenced the response.

Identify:

- which rules appear to have influenced the output
- which rules were missed
- whether the response remained focused on the target file
- whether unrelated code or architecture was introduced
- which claims still require deterministic verification

Instructions are guidance supplied to the model, not an enforcement control.

The honest question is not simply:

> Did it produce secure code?

Ask instead:

> Did the scoped guidance shift the output, and where did it still fall short?

If you finish early, tighten or broaden the `applyTo` glob and observe how activation changes.

---

## Exercise 6: `/security-review` On Your Changes

### Goal

Run `/security-review` against the changes you made in Lab 1 and compare what it catches with the manual review from Exercise 3.

### Context

`/security-review` is an interactive slash command available as an experimental public-preview capability in supported Copilot CLI and Copilot app environments.

It reviews active local changes rather than the entire repository and may return findings scored by severity and confidence.

It is a preview-stage complement to manual review and deterministic security tooling, not a replacement.

A clean result is not proof that the code is secure.

### Prerequisites

This exercise requires one of:

- Copilot CLI, launched with the `copilot` command
- a Copilot app version that supports `/security-review`

Check the CLI version from a terminal:

```bash
copilot --version
```

If neither surface is available, skip to the alternative exercise below.

### Steps — Copilot CLI

1. make sure you have uncommitted changes from Lab 1
2. open a terminal at the repository root
3. launch the interactive Copilot CLI session:

```bash
copilot
```

4. run:

```text
/help
```

5. if `/security-review` is listed, run:

```text
/security-review
```

6. if `/security-review` is not listed, do not spend lab time troubleshooting preview availability; proceed to the prompt-file fallback
7. review each finding, including:
   - severity
   - confidence
   - evidence
   - suggested remediation
8. compare the output against your Exercise 3 results:
   - which findings appear in both?
   - which findings does `/security-review` surface that the manual review missed?
   - which findings did the manual review catch that `/security-review` missed?
9. if `/security-review` flags issues in your fixes, determine whether each finding represents:
   - an incomplete remediation
   - a valid additional concern
   - a hypothesis requiring more context
   - a false positive

In the Copilot app, run `/security-review` directly in the chat interface instead of launching the CLI session.

### Alternative Exercise: Security Review Prompt File

If you cannot access `/security-review`, create a reusable prompt file.

Create this directory if it does not already exist:

```text
.github/prompts/
```

Create:

```text
.github/prompts/security-review.prompt.md
```

Add:

```markdown
# Security Review

Review the currently open files for OWASP Top 10 vulnerabilities.

For each finding, provide:

1. the untrusted input or trust boundary
2. the vulnerable operation
3. the CWE number and name
4. severity: critical, high, medium, or low
5. the specific file and code evidence
6. the smallest remediation
7. a deterministic way to verify the fix

Focus on:

- injection, including command and path injection
- cross-site scripting
- insecure deserialization
- exposed error details
- sensitive data exposure
- insecure cryptographic choices

Treat missing authentication or authorization as an architectural concern unless the supplied code and dependencies establish an existing authorization model.

Distinguish confirmed findings from hypotheses.

Do not provide generic advice. Every confirmed finding must reference specific code in the current context.
```

Then:

1. save the prompt file
2. invoke it against one of the lab files
3. compare the output against the ad hoc OWASP-oriented review from Exercise 3
4. note whether the prompt file produces more consistent:
   - evidence
   - categorization
   - remediation guidance
   - verification guidance

### Checkpoint

The comparison between `/security-review`, or the prompt-file fallback, and the manual review from Exercise 3 is the key output.

Neither approach catches everything. Together they may catch more than either catches alone.

You should leave with a concrete understanding of:

- what each review layer adds
- what each review layer can miss
- why evidence is still required
- why deterministic verification is still required

---

## Apply The Pattern To Your Codebase

If you finish Exercises 5–6 early, choose one of these activities:

- adapt the scoped instruction files from Exercise 5 to your team's stack and security requirements
- keep the scope narrow and tie every rule to real code paths and controls
- open a file from your own codebase, if accessible, and run the evidence-based review prompt or `/security-review`
- compare the findings against what you would have caught during manual review
- review community security instruction templates and identify rules that may be relevant to your team:
  - `github.com/github/awesome-copilot/blob/main/instructions/security-and-owasp.instructions.md`
  - `github.com/Robotti-io/copilot-security-instructions`

Do not copy a large community ruleset into your repository without reviewing its scope, assumptions, and applicability to your environment.

---

## If You Get Stuck

- reduce the task to one vulnerability at a time
- do not ask Copilot to fix everything in one prompt
- ask Copilot to explain the risk before asking for remediation
- compare changed code against the checkpoint criteria instead of trusting the first draft automatically
- if you intentionally start a new session, reattach the target files and paste the complete task prompt
- if the instruction file in Exercise 5 does not appear to influence the response:
  - confirm that the `applyTo` glob matches the attached file's path
  - confirm that the matching file is attached, selected, or explicitly referenced
  - confirm that the instruction file was saved before the comparison run
  - confirm that both runs used the same model
  - confirm that both runs used the identical prompt
  - confirm that both runs started from clean sessions

## Key Takeaways

- security wording in prompts and comments can shift Copilot output toward safer patterns
- a structural remediation changes the unsafe data flow; a cosmetic change merely rearranges it
- Copilot can be useful as a reviewer and threat-modeling partner, not only as a code generator
- when the first answer is weak, reject and redirect it with constraints that describe the correct control
- verification is part of the remediation, not an optional final step
- instruction files are version-controlled guidance supplied on supported surfaces when a request matches their scope
- instruction files shape model output; they do not enforce security controls
- `/security-review` and manual review are complementary layers
- a clean AI review result is not proof that the code is secure
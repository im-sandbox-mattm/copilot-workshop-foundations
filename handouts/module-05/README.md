# Module 05: Secure Coding With GitHub Copilot

## What You Will Practice

- using select-code plus `/fix` to remediate vulnerable code quickly
- using security-aware prompts to steer Copilot toward safer patterns
- using Copilot Chat as a security reviewer and lightweight threat-modeling partner
- recognizing when to reject and redirect insecure AI output

## Module Focus

The goal is not to teach OWASP from scratch. The goal is to build muscle memory for secure coding workflows you can use with Copilot immediately.

## IDE Notes

- VS Code: select the vulnerable code, use Chat for a brief explanation if needed, and move to Agent or `/fix` quickly for the remediation steps.
- IntelliJ: keep the same prompt flow in Copilot Chat; if slash commands or Agent surfaces differ in your plugin version, follow the trainer for the exact UI path.

## Mode Guidance

- exercise 1: Agent or `/fix`, with Chat only for a brief explanation if needed
- exercise 2: Agent or `/fix`
- exercise 3: Chat
- exercise 4: Agent

## Before You Start

- open the `copilot-workshop-foundations` repo
- start from a clean branch: `git switch -c workshop/module-05 module-05-start`
- open these files side by side:
  - `frontend/src/security/OwnerNoticePreview.tsx`
  - `backend/src/main/java/com/workshop/petcareops/security/TemplatePreviewController.java`
- follow the trainer pace; the prompts and checkpoints below should track the live demo directly

## Exercise 1: Fix The Frontend XSS Risk

### Goal

Use `/fix` or a targeted security prompt to remediate unsafe HTML rendering.

### Steps

1. select the rendered preview block in `OwnerNoticePreview.tsx`
2. ask Copilot to explain the security risk briefly if needed
3. use `/fix` or the prompt below
4. review whether the fix removes unsafe HTML rendering rather than just renaming variables

### Prompt

```text
Fix this React preview so it no longer allows unsafe HTML injection. Keep the same user-facing behavior, but render the content safely without dangerouslySetInnerHTML.
```

### Checkpoint

The fix should remove `dangerouslySetInnerHTML` and render the preview as ordinary React content.

## Exercise 2: Fix The Backend File Preview Endpoint

### Goal

Use Copilot to remediate path traversal and exposed error details in a Spring controller.

### Steps

1. select the `previewTemplate` method in `TemplatePreviewController.java`
2. ask Copilot to identify the main security risks
3. use `/fix` or the prompt below
4. review whether the result normalizes the path and avoids returning stack traces to the client

### Prompt

```text
Fix this Spring Boot endpoint for secure template preview. Prevent path traversal, keep reads inside the template directory, and stop returning raw exception details to the client.
```

### Checkpoint

The rewritten method should constrain file access and return a safer error response.

## Exercise 3: Security Review And Threat Model

### Goal

Use Copilot Chat as a security reviewer instead of relying only on code generation.

### Steps

1. keep both the frontend preview file and backend template preview controller open
2. ask the review prompt below
3. follow with the STRIDE prompt if time allows
4. note which issues Copilot found that were not obvious at first glance

### Review Prompt

```text
Review this reminder preview flow for OWASP Top 10 risks. Focus on XSS, path traversal, exposed error details, and any missing validation or output encoding.
```

### STRIDE Prompt

```text
Perform a brief STRIDE threat analysis for the owner reminder preview feature in this repo. Keep it to the top risks and the most useful mitigations.
```

### Checkpoint

The review should identify concrete risks in the actual code, not generic secure coding advice.

## Exercise 4: Reject And Redirect

### Goal

Practice steering Copilot away from insecure output when the first answer is not good enough.

### Steps

1. ask Copilot for a quick fix if the first result feels weak
2. add a stronger constraint using the prompt below
3. compare the new answer with the first one

### Prompt

```text
Regenerate the fix, but do not use dangerouslySetInnerHTML, do not return stack traces to clients, and keep file reads constrained to the approved template folder.
```

### Checkpoint

The second answer should be more explicit and safer because the constraints are clearer.

## If You Get Stuck

- reduce the task to one vulnerability at a time
- ask Copilot to explain the risk before asking for the fix
- compare the changed code against the checkpoint rather than trusting the first draft automatically

## Key Takeaways

- your security wording in prompts and comments materially changes Copilot output
- `/fix` is one of the fastest secure-coding workflows when the problem is local and visible
- Copilot is useful as a reviewer and threat-modeling partner, not just as a code generator
- when the first answer is weak, reject and redirect with explicit constraints
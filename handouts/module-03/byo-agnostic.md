# Module 03 (BYO Variant): Legacy Modernization — Bring Your Own Codebase

## What You Will Practice

- assessing a legacy slice of *your own* codebase before asking Copilot to change it
- discovering what testing convention (if any) already exists in your stack
- generating characterization tests to lock in current behavior before migrating
- using a version-pinned modernization prompt so Agent mode makes smaller, safer changes
- separating mechanical changes from behavior-sensitive changes, regardless of language

## Module Focus

This variant exists because not everyone in the room is working in the same stack. The workflow — assess, lock in behavior, modernize with a plan, verify against the lock-in — is not Java-specific. If you're working in VB6, VC++, legacy .NET, or anything else, this is your version of the exercise. If you don't have your own codebase to bring, use the main Module 03 exercises instead — they're fully built and tested against a real repo.

## Before You Start

- pick one small, self-contained piece of your own legacy codebase — a single class, function, or module, not a whole file if the file is large
- confirm you can open it in your IDE with Copilot Chat available
- confirm you have *some* way to compile or run this code, even manually — if you can't build or run it at all, this exercise will be analysis-only, and that's fine; skip the test-generation and verification steps and say so explicitly when the trainer checks in
- if you're not sure whether a modern test framework exists for your stack, that's not a blocker — Exercise 2 starts by asking Copilot that exact question

## Mode Guidance

- exercise 1: Chat or Plan
- exercise 2: Chat, then Agent once you're ready to generate real test files
- exercise 3: Plan first, review, then Agent to execute — do not combine "show the plan" and "apply the changes" into one prompt (this costs more than it needs to; see the trainer's note on token usage if you're curious why)
- exercise 4: Agent, once the modernization is applied

## Exercise 1: Assess Before You Migrate

### Goal

Use Copilot to separate low-risk modernization opportunities from behavior-sensitive changes before asking it to rewrite anything.

### Steps

1. open the file(s) containing the code you picked
2. start in Chat or Plan mode
3. ask the prompt below, filling in your own details
4. review whether Copilot distinguishes mechanical changes from behavior-sensitive ones — if it doesn't, ask it to explicitly separate the two before continuing

### Prompt

```text
Using #file on [your file name(s)], analyze this as a modernization candidate for [target
language version / framework — e.g. "VB6 to VB.NET", "VC++/MFC to modern C++", ".NET
Framework 4.x to .NET 8"].
1. list the highest-value improvements for reaching that target
2. separate mechanical changes (safe, output-preserving) from behavior-sensitive changes
   (need explicit review and tests before changing)
3. propose the first migration step that should happen before any broad rewrite
Keep the answer concrete and tied to this code, not general advice.
```

### Checkpoint

The response should name specific things in your actual code — not generic language-upgrade advice — and should not propose a broad rewrite as the first step.

## Exercise 2: Test Before You Migrate

### Goal

Create a safety net for the current behavior before the modernization pass — even if that safety net looks different than a typical unit test, depending on your stack.

### Steps

1. **First, ask what testing looks like here.** If you're not certain your stack has an established, modern testing convention, ask Copilot directly before asking for tests:
```text
   What testing framework or convention, if any, is already used in this codebase or is
   standard for [your language/stack]? If nothing modern and well-supported exists, suggest
   the most practical way to verify behavior before I modernize this code — even if that's
   something simpler than a formal unit test.
```
2. Switch to Agent mode
3. Paste the test-generation prompt below as a fresh Agent prompt — don't just say "add tests"
4. Keep the scope tight and behavior-focused: cover the specific scenarios Exercise 1 flagged as behavior-sensitive, not everything the code does
5. Run the tests if your setup allows it

### Prompt

```text
Using [the testing approach we just identified], create tests for [your function/class]
that lock in its current behavior before modernization. Cover:
- [scenario 1 — pull this from what Exercise 1 flagged as highest-risk]
- [scenario 2]
- [scenario 3, if applicable]
Constraints:
- keep assertions specific, not just "did it run without error"
- keep the test file compact and readable
```

### Checkpoint

You should end with something — a formal test file, a script, or even a documented set of manual verification steps if that's genuinely the most your stack supports — that proves today's behavior before any migration starts.

## Exercise 3: Modernize With Version Targets

### Goal

Use Agent mode to apply a narrow modernization pass instead of a vague "clean this up" rewrite — and use Plan mode deliberately, not by habit.

### Steps

1. keep the conversation open so the assessment and tests stay in context — don't start a new chat
2. **first, ask for the plan only:**
```text
   Analyze what would be needed to modernize [your file(s)] to [target version/framework].
   Preserve behavior, keep the change easy to review. Show me the plan only — do not make
   any changes yet.
```
3. review the plan against what Exercise 1 flagged as behavior-sensitive — does it respect those boundaries, or does it quietly touch something it shouldn't?
4. **only if you're satisfied, send a second, short prompt to proceed:**
```text
   Proceed with the plan as described.
```
5. if you switch modes at any point, paste the full prompt again — don't rely on shorthand like "do it" from a different mode

### Checkpoint

The result should look more modern and more compact, but it should still be recognizably the same behavior — and the plan, reviewed before execution, should not have surprised you.

## Exercise 4: Verify

### Goal

Confirm the modernization didn't change behavior — using whatever safety net Exercise 2 actually produced.

### Steps

1. re-run whatever you built in Exercise 2 — automated tests, a script, or manual verification steps
2. if anything differs from before, stop and ask Copilot to explain the discrepancy before deciding whether it's an intentional improvement or an accidental behavior change

### Checkpoint

Same result as before modernization, or a clearly understood and intentional difference — not an unexplained one.

## If You Get Stuck

- ask for the assessment only before asking for any code changes
- if you don't have a way to run tests in your stack, say so — analysis-only is a legitimate outcome for this exercise, not a failure
- reduce the task to one file and one migration step if Agent starts broadening the scope
- if you switch modes, restate the prompt in full instead of using shorthand
- if your own codebase turns out to be too large or tangled to use live, fall back to the main Module 03 Java exercises — they're fully built and ready to go

## Key Takeaways

- assess first, migrate second is the control pattern that makes Copilot modernization trustworthy — in any language
- version-pinned prompts produce much better migrations than vague modernization requests
- characterization tests (or the closest equivalent your stack supports) are the practical safety net for AI-assisted migration work
- plan first, then proceed as a second short prompt in the same conversation — not one combined instruction — gives you a real review point without meaningfully increasing cost
- this workflow is not tied to Java, React, or any specific stack — it's tied to the discipline of understanding behavior before changing it

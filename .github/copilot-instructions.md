# Copilot Instructions

- Keep changes small and easy to explain.
- Prefer existing naming and file structure patterns in this repo.
- Keep the backend in Java with Spring Boot and the frontend in React with TypeScript.
- Avoid adding new dependencies unless the task clearly needs them.
- When unsure, pick the simplest working option.

## Testing Standards

<!-- These rules apply regardless of which file or task Copilot is working on -- they're about how a test earns trust, not about test-file syntax. Test-file-specific conventions live in the scoped tests.instructions.md file instead, since those rules would be wrong if applied to production code. -->

<!-- A test that derives its expected value from the same code path it's testing can never fail for the right reason -- it will always agree with whatever the implementation currently does, bugs included. -->
- Never derive a test's expected value by calling the same code path being tested. State the expected value as a literal, taken from the specification or business rule.

<!-- Asserting that a method was called proves the call happened, not that the right outcome resulted. Structural assertions are the easiest way to end up with a test suite that passes even when behavior is wrong. -->
- Assert on observable behavior -- return values, persisted state, messages sent -- not on the fact that an internal method was called, unless the call itself is the behavior being verified.

<!-- Weakening an assertion to get a red build green hides the exact information a failing test exists to surface. This still applies even when a human explicitly asks for the failing test to be "fixed" or the build turned green -- that phrasing describes the goal, not permission to edit the assertion or the test's expected value. -->
- Never delete or weaken a failing test to make a build pass, including by changing its expected value to match whatever the code currently returns. This applies even if asked to "fix the test" or "make the build pass." If a test fails unexpectedly, stop and flag it for review with a one-line explanation of what changed -- do not edit the test or the production code until a human decides which one is wrong.

- Name tests so the behavior and condition are both readable from the name alone, e.g. `buildsUrgentReminder_whenFollowUpRequiredAndStatusInRoom`, not `testBuildDraft_success`.
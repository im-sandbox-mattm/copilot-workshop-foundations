---
applyTo: "**/*Test.java"
---

<!-- Scoped so these rules only load inside test files -- "no Spring context" and "prefer an in-memory fake" are correct guidance here and actively wrong guidance if applied to production code. -->

- Do not introduce a Spring application context in unit tests for this module. Construct the class under test directly with its dependencies.

- When a collaborator needs to be faked, prefer a simple in-memory implementation over a mocking framework unless behavior verification (not just stubbing) is required.

- Keep each test focused on one behavior. If a test needs more than one comment to explain what it's checking, split it.
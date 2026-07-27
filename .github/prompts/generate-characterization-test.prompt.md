---
description: Generate characterization tests that lock in a class's current behavior before refactoring.
---

You are generating characterization tests, not testing a specification.

For the class specified below:
1. Do not consult any external spec or "correct" expected behavior -- read only what the code currently does.
2. Identify the distinct branches of behavior (status conditions, channel selection, message construction).
3. Generate one test per distinct branch, asserting on the actual current output for each.
4. Use literal expected values in assertions, not values derived by calling the method under test.
5. Do not introduce a Spring context. Do not add dependencies.
6. Keep assertions specific -- assert on the actual message content and channel, not just that a result was returned.

Class to characterize: ${input:className:OwnerReminderDraftService}
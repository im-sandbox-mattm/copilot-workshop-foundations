---
applyTo: "backend/src/main/java/com/workshop/petcareops/followup/**"
---

<!-- Scoped to this package specifically -- these are refactoring-in-progress rules, not something that should apply to a brand-new file with no smells to address yet. -->

- When refactoring, preserve the public method signature unless the task explicitly calls for an API change.

- Address exactly one code smell per change. If you notice a second smell while working, name it and stop -- do not fix it in the same pass.

- Do not refactor a method that has zero test coverage. Recommend characterization tests first instead of proceeding.
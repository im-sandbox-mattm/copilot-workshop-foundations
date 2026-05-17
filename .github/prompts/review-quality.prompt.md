# Review Quality

Review the referenced changed files or selected diff in this repo.

Return findings in this order:

1. highest severity first
2. category for each finding: Security, Reliability, Maintainability, or Best Practices
3. concrete evidence tied to the changed code
4. the smallest safe fix path
5. any regression test or validation check that should follow

Keep the review focused on the actual changed files. Avoid generic advice.
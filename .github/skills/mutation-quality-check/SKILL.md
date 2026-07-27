---
name: mutation-quality-check
description: Assess whether a test suite actually catches defects or only executes code. Use when asked to check test quality, review test suite strength, or verify a suite isn't just chasing coverage numbers.
---

<!-- Kept narrow on purpose -- skills use progressive loading, so this file only gets pulled into context on requests that actually match its description. A broader skill would cost more tokens on unrelated requests for no benefit. -->

# Mutation Quality Check

When asked to assess test quality for a class:

1. Identify 3-5 small, realistic mutations to the implementation: flip a conditional, remove a branch, change a boundary value, swap a default, invert a boolean flag.
2. For each mutation, determine whether the existing test suite would catch it.
3. Report mutations that would NOT be caught -- these are the tests worth writing or strengthening. Do not report mutations that are already caught; a clean list of gaps is more useful than a restatement of what's already covered.
4. Do not modify the implementation. This skill only assesses; it does not fix.
5. Present findings as a table: mutation description, whether caught, and if not, the specific assertion that would catch it.
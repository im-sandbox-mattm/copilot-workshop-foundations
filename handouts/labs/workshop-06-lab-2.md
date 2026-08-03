# Lab 2: Standardize a Multi-Project Development Workspace

**Estimated time:** 30–40 minutes

## Objective

Evaluate and improve shared development configuration for a repository containing both frontend and backend projects.

## Scenario

A team works across React/TypeScript and Java/Spring projects in the same repository. Developers use different IDEs, so the team needs consistent repository behavior without assuming that every IDE supports the same configuration files or controls.

## Required Outcome

Produce and verify a configuration plan that identifies:

1. which settings should be shared across the repository;
2. which settings should remain specific to a project or IDE;
3. which controls are advisory rather than enforced;
4. which controls require enterprise management outside the repository;
5. how the configuration can be verified across both project areas.

## Implementation Paths

- **VS Code path:** inspect and refine repository-controlled workspace configuration.
- **Other IDE path:** evaluate the same requirements and identify the equivalent IDE or platform control.
- Completion is based on correct placement, scope, and verification—not use of a specific IDE.

## Constraints

- Do not force identical tooling onto the frontend and backend projects.
- Preserve the existing build and test commands for each stack.
- Distinguish repository guidance from IDE behavior and enterprise enforcement.
- Keep changes small, reviewable, and portable where practical.

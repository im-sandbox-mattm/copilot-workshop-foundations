# Lab 1: Evaluate a Governed MCP Workflow

**Estimated time:** 20–30 minutes

## Objective

Evaluate a bounded MCP-assisted workflow using supplied configuration, tool inventory, and execution evidence.

## Scenario

A development team has connected GitHub Copilot to a workshop-safe Chrome DevTools MCP server. Before approving the workflow for broader use, the team must determine:

- what the server can access;
- which tools are available;
- where approval is required;
- what changes when a required tool is unavailable;
- what the captured evidence proves;
- what still requires independent verification.

## Required Outcome

Produce a short recommendation that states:

1. whether the workflow is acceptable for the stated task;
2. which MCP tools and permissions are required;
3. which capabilities are unavailable or intentionally restricted;
4. what evidence supports the recommendation;
5. how the result should be independently verified.

## Constraints

- Use only the supplied configuration, tool inventory, and execution evidence.
- Do not assume that an installed or declared server exposes every possible capability.
- Distinguish observed behavior from assumptions.
- Do not treat MCP output as independent verification.

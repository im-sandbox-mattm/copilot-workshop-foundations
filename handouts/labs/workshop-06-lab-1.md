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

## Before You Start

### Get the workshop repository

If you have not cloned it yet:

```bash
git clone https://github.com/im-sandbox-mattm/copilot-workshop-foundations.git
cd copilot-workshop-foundations
```

If you already have it cloned:

```bash
cd copilot-workshop-foundations
git fetch --tags
```

Everyone runs:

```bash
git switch -C module-07 <STARTING_TAG>
```

This creates or resets a local branch named `module-07` at the workshop starting tag, then switches to that branch.

> The final starting tag will be confirmed before delivery.

## Evidence Provided

Review these files before making a recommendation:

- `../assets/mcp-tool-inventory.md`
- `../assets/mcp-run-tool-enabled.md`
- `../assets/mcp-run-tool-disabled.md`

## Activity

### Step 1: Map the boundary

Identify:

- the MCP server in use;
- the application and browser surfaces it could reach;
- the tools that were available;
- the capability that was intentionally not approved;
- any capability that was configured but not usable for the task.

### Step 2: Compare the two runs

Create a comparison covering:

- the task requested;
- the relevant allowed tool set;
- the result with `list_network_requests` enabled;
- the result with `list_network_requests` disabled;
- whether the fallback tool was equivalent;
- which conclusions are observations and which are assumptions.

### Step 3: Decide whether the workflow is acceptable

Evaluate the workflow for this bounded task only. Consider:

- least-privilege access;
- whether the available tool is necessary;
- whether the workflow exposes more access than the task requires;
- what approval or review should occur before use;
- what must be verified outside the MCP workflow.

### Step 4: Produce the recommendation

Write a recommendation of no more than 250 words using this structure:

**Decision:** Approve, approve with conditions, or do not approve.

**Required access:** Name the minimum server tools and permissions required.

**Evidence:** Cite the specific observed behavior that supports the decision.

**Limitations:** State what the evidence does not establish.

**Independent verification:** Describe at least one deterministic check outside the MCP workflow.

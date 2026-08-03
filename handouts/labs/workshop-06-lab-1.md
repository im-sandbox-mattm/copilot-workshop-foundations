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

Produce a concise **task-level MCP decision record** for this specific workflow.

Your record must state:

1. the approved task and server context;
2. the minimum required tools;
3. any capabilities intentionally denied;
4. the exception or escalation path if the task cannot proceed;
5. the evidence supporting the decision;
6. the independent verification method;
7. any conditions that should trigger reassessment or revocation.

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

### Step 4: Produce the decision record

Write no more than 300 words using this structure:

**Task and server context:** State the bounded task and the server involved.

**Decision:** Approve, approve with conditions, or do not approve.

**Minimum required tools:** Name only the tools necessary for this task.

**Denied capabilities:** Identify broader capabilities that should remain unavailable.

**Exception or escalation path:** State what should happen if the approved tool set is insufficient.

**Evidence:** Cite the observed behavior supporting the decision.

**Independent verification:** Describe at least one deterministic check outside the MCP workflow.

**Reassessment trigger:** Name at least one condition that would require the decision to be reviewed again.

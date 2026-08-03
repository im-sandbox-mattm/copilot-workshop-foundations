# Facilitator Demo: Governed MCP Access

**Target duration:** 10–15 minutes

## Purpose

Demonstrate that an MCP server’s configured capabilities are not automatically available to every task. The effective boundary depends on the tools exposed, enabled, approved, and independently verified for the current workflow.

## Required Setup

- GitHub Copilot in VS Code
- Chrome DevTools MCP server configured
- workshop frontend available at `http://localhost:5173/`
- workshop backend available at `http://localhost:8080/` when started
- `list_network_requests` initially enabled
- `evaluate_script` available but not approved for the read-only task

## Demonstration Sequence

### Part 1: Establish the trust boundary

1. Show the configured Chrome DevTools MCP server.
2. Show the available tool inventory.
3. Distinguish configured server, exposed tools, enabled tools, and approved tools.
4. Ask: Which of these states represents actual task capability?

### Part 2: Use the minimum required capability

1. Open the workshop frontend.
2. Inspect network activity with `list_network_requests`.
3. Show `/api/dashboard` failing while the backend is unavailable.
4. Start the backend and independently confirm the endpoint returns HTTP 200.
5. Refresh the frontend and show `/api/dashboard` returning HTTP 200.
6. Ask: What does the MCP evidence prove, and what remains unverified?

### Part 3: Restrict the tool set

1. Disable `list_network_requests`.
2. Submit the same inspection task.
3. Show that `get_network_request` requires a specific request identifier and is not an equivalent replacement.
4. Do not approve `evaluate_script` merely to force completion.
5. Ask: Is the correct outcome task failure, broader approval, or a different verification path?

## Governance Discussion

Use the observed behavior to evaluate:

- whether `list_network_requests` is the minimum necessary capability;
- why `evaluate_script` should remain blocked for this task;
- what evidence would justify broader access;
- whether policy should differ for local, public, and internal enterprise MCP servers;
- what should be logged, reviewed, or verified outside the MCP workflow.

## Teaching Point

The prompt did not change. The allowed tool set changed, so the workflow’s effective capability and result changed. Advanced MCP use depends on controlling and proving that boundary, not merely demonstrating that a server can do something useful.

## Evidence to Preserve

- tool inventory
- successful tool-enabled run
- restricted tool-disabled run
- independent HTTP verification
- approval and restriction decisions

## Fallback

If the live MCP server is unavailable, present the verified evidence files in `handouts/assets/` and run the same comparison as a guided governance walkthrough.

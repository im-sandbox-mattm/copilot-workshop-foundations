# Facilitator Demo: Governed MCP Access

**Target duration:** 10–15 minutes

## Purpose

Demonstrate that an MCP server’s configured capabilities are not automatically available to every task. The effective boundary depends on the tools exposed, enabled, approved, and independently verified for the current workflow.

## Audience Framing

Assume the organization already has an MCP registry, server-assessment process, and approved deployment paths.

This demonstration focuses on the layer that remains after server approval: whether a specific task should be allowed to use a specific tool, what evidence that tool produces, and how the result should be independently verified.

Treat the local Chrome DevTools server as an exception to a registry-first operating model rather than as an introduction to MCP.

## Required Setup

- GitHub Copilot in VS Code
- Chrome DevTools MCP server configured
- workshop frontend available at `http://localhost:5173/`
- workshop backend available at `http://localhost:8080/` when started
- `list_network_requests` initially enabled
- `evaluate_script` available but not approved for the read-only task

## Preflight and Reset Checklist

Before Demo 1:

1. confirm the frontend is running at `http://localhost:5173/`;
2. stop the backend if it is already running;
3. open the frontend in the browser;
4. confirm `list_network_requests` is enabled;
5. confirm `evaluate_script` remains blocked;
6. begin from a fresh Copilot chat;
7. reload the page immediately before collecting State 1 evidence.

Between observed states:

- before State 2, start the backend and reload the frontend;
- run `curl -i http://localhost:8080/api/dashboard` outside the MCP workflow;
- before State 3, disable `list_network_requests`;
- start a fresh Copilot chat or clearly repeat the original task without carrying forward prior request identifiers;
- reload the frontend before attempting the restricted-tool run.

After Demo 1:

- leave `list_network_requests` disabled for Demo 2;
- leave `evaluate_script` blocked;
- keep the frontend and backend running;
- restore the approved default tool state only after Demo 2.

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
4. Start the backend (./mvnw spring-boot:run) and independently confirm the endpoint returns HTTP 200 (curl -i http://localhost:8080/api/dashboard).
5. Refresh the frontend and show `/api/dashboard` returning HTTP 200.
6. Ask: What does the MCP evidence prove, and what remains unverified?

## Presenter Note: Why Use MCP Here?

For a known endpoint, a direct command such as `curl` is cheaper and more deterministic. The MCP workflow adds value because it inspects what the frontend actually did inside the browser: which requests were made, whether they failed, and how browser-side evidence changed after the backend became available.

The same evidence could be gathered manually in DevTools or through browser automation. Copilot’s value is guided discovery, correlation, and explanation across those browser signals—not replacing every command-line check.

Use the independent `curl` result as verification rather than asking MCP to verify its own conclusion.

### Part 3: Establish the bounded failure

1. Disable `list_network_requests`.
2. Submit the same inspection task.
3. Show that `get_network_request` requires a specific request identifier and is not an equivalent replacement.
4. Keep `evaluate_script` blocked.
5. Establish that the approved MCP toolset cannot complete the browser-inspection claim.
6. Stop before choosing whether to broaden access, require a human browser check, perform a narrower independent check, or leave the claim unverified.

Use Demo 2 to make and defend that decision.

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

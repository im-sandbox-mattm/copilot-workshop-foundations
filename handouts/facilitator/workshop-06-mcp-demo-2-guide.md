# Facilitator Demo 2: Permission Tradeoff and Verification Path

**Target duration:** 10–15 minutes

## Purpose

Force a decision between broadening MCP access, accepting a bounded failure, or switching to a narrower independent verification method.

## Consulting Framing

Based on patterns observed across different organizations, this demonstration uses a task-level decision pattern that I would recommend layering on top of an existing MCP registry.

It is not an official MCP standard. It applies established principles—least privilege, explicit authorization, auditability, independent verification, and revocable access—to the decision at hand.

## Starting State

- Chrome DevTools MCP server configured
- `list_network_requests` disabled
- `evaluate_script` disabled
- frontend open at `http://localhost:5173/`
- backend running

## Preflight and Reset Checklist

Before Demo 2:

1. confirm the frontend is running at `http://localhost:5173/`;
2. confirm the backend is running;
3. confirm `list_network_requests` remains disabled from Demo 1;
4. confirm `evaluate_script` remains blocked;
5. open the frontend in the browser;
6. start a new Copilot chat;
7. do not carry forward request identifiers or browser evidence from Demo 1;
8. reload the frontend immediately before submitting the Demo 2 prompt.

During the demo:

- keep the original claim explicit: whether the dashboard loaded valid data;
- do not silently narrow that claim to endpoint availability;
- use `curl` only when discussing the narrower backend check;
- require approved browser evidence or a human browser check for frontend request, binding, rendering, or user-visible claims;
- leave any unsupported portion of the claim unverified.

After the demo:

- restore `list_network_requests` to its approved default state;
- leave `evaluate_script` blocked unless a later workshop activity explicitly requires it;
- preserve the final decision and remaining unverified claims for debrief.

## Scenario

The agent must determine whether the dashboard loaded valid data, but the currently approved MCP tools cannot enumerate network requests or inspect page state directly.

## Decision Options

1. Approve `evaluate_script` and allow broader browser-context access.
2. Keep broader access blocked and accept that the MCP task cannot be completed.
3. Use a narrower independent verification path outside MCP.

## Audience Decision

Ask participants to choose an option and justify it using:

- task necessity;
- least privilege;
- sensitivity of accessible browser data;
- verification strength;
- token and operational cost;
- auditability and repeatability.

## Recommended Resolution

Keep `evaluate_script` blocked unless the browser-specific claim justifies broader access.

Choose the resolution that matches the claim:

- To verify only that the backend endpoint is available and returns data, use a narrower deterministic command such as `curl`.
- To verify that the frontend requested, bound, and rendered valid dashboard data, require browser evidence through an approved inspection capability or a human browser check.
- If neither path is available, leave the browser-rendering claim unverified.

Do not present a successful `curl` response as proof of frontend rendering or user-visible behavior.

## Teaching Point

A capable agent does not need every available tool. The advanced decision is selecting the narrowest trustworthy path that produces sufficient evidence.

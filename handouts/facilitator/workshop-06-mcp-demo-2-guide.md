# Facilitator Demo 2: Permission Tradeoff and Verification Path

**Target duration:** 10–15 minutes

## Purpose

Force a decision between broadening MCP access, accepting a bounded failure, or switching to a narrower independent verification method.

## Starting State

- Chrome DevTools MCP server configured
- `list_network_requests` disabled
- `evaluate_script` disabled
- frontend open at `http://localhost:5173/`
- backend running

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

For this bounded endpoint check, keep `evaluate_script` blocked and use a direct deterministic command such as `curl`. Broader browser execution should require a task that cannot reasonably be completed with a narrower capability.

## Teaching Point

A capable agent does not need every available tool. The advanced decision is selecting the narrowest trustworthy path that produces sufficient evidence.

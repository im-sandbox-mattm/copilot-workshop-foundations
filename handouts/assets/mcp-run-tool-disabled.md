# MCP Run Evidence: Required Tool Disabled

## Task

Inspect browser network activity for the workshop application at `http://localhost:5173/` and determine whether the frontend request to `/api/dashboard` succeeds.

## Restricted capability

`list_network_requests` was disabled for the MCP workflow.

## Observed sequence

1. The same task was submitted with the same application state.
2. The workflow could not use `list_network_requests`.
3. It attempted to rely on `get_network_request`, which required a specific request identifier and was not an equivalent replacement.
4. The workflow could not complete the requested inspection from the remaining approved tools.
5. No additional capability was approved to bypass the restriction.

## What this proves

- The same prompt produced a different result when the allowed tool set changed.
- A configured MCP server did not guarantee that every server capability was available to the task.
- Tool-level restriction changed the workflow’s effective capability.

## What this does not prove

- The server had no other way to obtain similar information if additional tools were approved.
- The task was impossible outside MCP.
- Disabling one tool provided complete isolation from browser or application data.

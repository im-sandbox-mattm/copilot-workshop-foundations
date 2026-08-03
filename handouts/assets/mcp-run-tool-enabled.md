# MCP Run Evidence: Required Tool Enabled

## Task

Inspect browser network activity for the workshop application at `http://localhost:5173/` and determine whether the frontend request to `/api/dashboard` succeeds.

## Allowed capability

`list_network_requests` was available to the MCP workflow.

## Observed sequence

1. The frontend application was opened at `http://localhost:5173/`.
2. Browser network activity was inspected using `list_network_requests`.
3. With the backend unavailable, the request to `/api/dashboard` returned `ERR_CONNECTION_REFUSED`.
4. The backend was then started.
5. An independent terminal check confirmed that `http://localhost:8080/api/dashboard` returned HTTP 200 with JSON.
6. After the frontend was refreshed, `list_network_requests` showed the `/api/dashboard` request returning HTTP 200.

## What this proves

- The MCP workflow could inspect browser network activity when the required tool was available.
- It could distinguish a failed frontend-to-backend request from a successful one.
- The observed browser request changed after the backend became available.

## What this does not prove

- The response body was correct in every respect.
- The backend implementation was secure or functionally complete.
- The MCP output independently verified itself.
- The workflow had access beyond the approved browser tools.

# MCP Demo Prompts

## Facilitator Preflight: Required State

Before each prompt, make sure the servers and tools match the intended state:

| Prompt | Servers | Chrome DevTools MCP tools |
|---|---|---|
| Prompt 1 | Frontend running at `http://localhost:5173/`; backend stopped | `list_network_requests` enabled; `evaluate_script` blocked |
| Prompt 2 | Frontend running; backend running at `http://localhost:8080/` | `list_network_requests` enabled; `evaluate_script` blocked |
| Prompt 3 | Frontend and backend running | `list_network_requests` disabled; `evaluate_script` blocked |

## Prompt 1: Establish available capability

> Using only the currently approved Chrome DevTools MCP tools, inspect the browser network activity for `http://localhost:5173/`. Determine whether the frontend request to `/api/dashboard` succeeds. Report the tool used, the observed result, and anything you cannot establish from the available evidence. Do not modify the application or approve additional capabilities.

## Prompt 2: Repeat after the backend starts

> Repeat the same network inspection for `http://localhost:5173/`. Determine whether the frontend request to `/api/dashboard` now succeeds. Report only observed evidence, the tool used, and what still requires independent verification. Do not modify the application.

## Prompt 3: Repeat with the required tool disabled

> Using only the currently approved Chrome DevTools MCP tools, inspect the browser network activity for `http://localhost:5173/` and determine whether the frontend request to `/api/dashboard` succeeds. Do not approve broader tools or use code execution merely to force completion. State whether the task can be completed with the current tool set and explain the specific capability boundary.

## Independent verification

Run outside the MCP workflow:

    curl -i http://localhost:8080/api/dashboard

# MCP Tool Inventory

## Server

Chrome DevTools MCP server connected through GitHub Copilot in VS Code.

## Observed available tools

- `list_pages`
- `select_page`
- `new_page`
- `click`
- `fill`
- `fill_form`
- `press_key`
- `evaluate_script`
- `handle_dialog`
- `resize_page`
- `list_network_requests`
- `get_network_request`
- `get_console_message`
- `emulate`
- `lighthouse_audit`
- performance trace tools

## Observed limitations

- The tested tool set did not expose a general DOM snapshot or read-page-text tool.
- Opening a page did not by itself prove that its visible content could be inspected.
- `evaluate_script` would have expanded the server’s effective access and was intentionally not approved for the read-only test.
- Installed or configured server status did not guarantee that every tool was running, approved, or available to the current request.

## Verification context

The server opened `http://localhost:5173/` and inspected browser network activity for the workshop application. The workflow was tested once with `list_network_requests` available and once with that tool disabled.

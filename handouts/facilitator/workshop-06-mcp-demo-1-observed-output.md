# MCP Demo 1: Observed Output

## State 1: Backend unavailable

### Observed result

- The frontend issued two requests to `http://localhost:8080/api/dashboard`.
- Both failed with `net::ERR_CONNECTION_REFUSED`.
- The browser-side evidence established that the frontend request did not succeed on the observed reload.

### Not established

- why the connection was refused;
- whether a backend process was expected on port 8080;
- whether the endpoint would succeed outside the browser or later;
- why the frontend issued the request twice.

## Independent verification

After starting the backend, verify outside MCP:

    curl -i http://localhost:8080/api/dashboard

Expected result: HTTP 200 with a JSON response.

## State 2: Backend available

### Observed result

- After a fresh reload, the frontend issued two requests to `http://localhost:8080/api/dashboard`.
- Both returned HTTP 200.
- The browser-side evidence established transport success for the observed page load.

### Not established

- whether the response body was semantically correct or complete;
- whether the UI rendered the returned data correctly;
- why the request was made twice;
- whether the behavior would remain stable across later sessions.

## State 3: Network-listing tool disabled

### Observed result

- Page navigation and reload remained available.
- `get_network_request` could inspect only a selected request or known request identifier.
- Without `list_network_requests`, the workflow could not enumerate requests or discover the relevant request identifier.
- The workflow therefore could not determine whether `/api/dashboard` succeeded from the approved tool evidence alone.

### Capability boundary

The MCP server remained configured, but disabling one required tool changed the workflow's effective capability.

## Teaching takeaway

The browser tools reveal what the frontend actually did. The independent command verifies the known backend endpoint. Neither source should be treated as proving more than its evidence supports.

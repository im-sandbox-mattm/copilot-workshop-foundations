# MCP Demo 2: Observed Output

## Result

The task could not be completed conclusively with the currently approved Chrome DevTools MCP tools alone.

## Missing capability

The workflow needed either:

- a way to enumerate or select network requests; or
- `evaluate_script` to inspect page state directly.

## Trust-boundary impact

Approving `evaluate_script` would allow JavaScript execution in the live page context, expanding access to DOM state, runtime data, storage, and potentially mutable application state.

## Least-privilege recommendation

Keep `evaluate_script` blocked for this bounded task.

Use one of these narrower paths:

1. verify the backend directly with `curl`;
2. have a human select the relevant request in DevTools, then use the existing request-detail tool;
3. approve broader browser execution only when in-page evidence is genuinely required.

## Remaining limitations

- `curl` does not prove frontend rendering or state binding.
- A selected request proves transport evidence, not business correctness.
- Even in-page inspection may not establish semantic validity without domain expectations.

## Teaching takeaway

The strongest answer was not “approve more access.” It was to identify the precise missing capability, compare alternatives, and choose the narrowest trustworthy verification path.

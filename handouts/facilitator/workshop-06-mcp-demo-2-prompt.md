# MCP Demo 2 Prompt

> Determine whether the dashboard at `http://localhost:5173/` loaded valid data using only the currently approved Chrome DevTools MCP tools.
>
> If the current tool set is insufficient, do not automatically request or invoke broader capabilities. Instead:
>
> 1. identify the specific missing capability;
> 2. explain what approving `evaluate_script` would add to the trust boundary;
> 3. compare broadening MCP access with using a narrower independent verification method;
> 4. recommend the least-privilege path that produces sufficient evidence;
> 5. state what would still remain unverified.
>
> Do not modify the application.

## Independent verification option

    curl -i http://localhost:8080/api/dashboard

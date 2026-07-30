# Lab 2 Baseline Benchmark

This is the benchmark your restructured review will be measured against.

Do not regenerate the baseline. Inspect the starter configuration, redesign how its surrounding context is supplied, and perform one live review using the same task and execution settings.

## Benchmark

| Measure | Baseline |
|---|---:|
| Copilot Usage (AIC) | 6.62 |
| Input tokens | 427,209 |
| Cached input tokens | 325,248 |
| Fresh input tokens | 101,961 |
| Cache-hit rate | 76.13% |
| Output tokens | 16,311 |
| Total tokens | 443,520 |
| Model turns | 19 |
| Tool calls | 21 |
| Errors | 1 |

```text
Fresh input = Input tokens − Cached input tokens

Cache-hit rate = Cached input tokens ÷ Input tokens
```

The complete, unedited baseline response is available in:

```text
handouts/assets/lab2-baseline-response.md
```

## Required Controls

Keep these settings unchanged for your live run:

| Setting | Required value |
|---|---|
| Model | GPT-5 mini |
| Thinking effort | Default |
| Tool capabilities | `read` and `search` only |
| Additional MCP tools | None |
| Review prompt | Exact text from `handouts/assets/lab2-review-prompt.md` |
| Chat session | Start a fresh chat |

The baseline used the supplied custom agent:

```text
.github/agents/lab2-review.agent.md
```

You may retain or redesign the agent and surrounding customization files, but the available capabilities must remain limited to `read` and `search`.

## What Must Stay Fixed

Do not change:

- the application code or repository starting point;
- the four primary files under review;
- the permitted supporting-file scope;
- the exact review prompt;
- the selected model or thinking effort;
- the available tool capabilities.

You may change how the surrounding context is selected, organized, scoped, and supplied.

## Quality Threshold

A run does not beat the baseline merely because it uses fewer tokens or AIC.

The result must remain decision-useful by:

- retaining supported, high-value findings or giving a defensible reason for rejecting them;
- avoiding additional unsupported findings;
- providing exact code evidence;
- explaining the consequence;
- stating confidence or status;
- proposing the smallest remediation;
- providing deterministic verification.

A result may also improve on the baseline by correcting an overstated finding, identifying a valid issue the baseline missed, or reducing the human effort needed to validate the review.

## Challenge

> Produce an equally—or more—decision-useful review with less model or reviewer effort.

Record your telemetry from Agent Debug Logs and be ready to discuss.
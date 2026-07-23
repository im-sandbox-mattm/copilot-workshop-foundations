#!/usr/bin/env bash
# Intercepts every bash tool call and only acts on ones that actually invoke
# git to make a commit. A scoped instructions file can ask for test coverage
# before a refactor proceeds, but an instruction is guidance the model can
# still miss or deprioritize under a vague request. This hook is the
# difference between asking and requiring: committing production code
# without a matching test change is blocked at the moment it would happen,
# not flagged after the fact in review.

set -euo pipefail

INPUT=$(cat)

# VS Code's PreToolUse hook input nests the terminal command at
# `.tool_input.command` (NOT `.toolArgs.command` -- that field doesn't exist
# and jq will silently fall through to empty, making every check below a
# no-op). Confirm the field path against the current hooks reference if this
# ever stops firing: https://code.visualstudio.com/docs/agents/reference/hooks-reference
COMMAND=$(echo "$INPUT" | jq -r '.tool_input.command // empty')

# Only act when the command invokes both git and a commit -- every other
# bash call passes through untouched. Checking the two words separately
# (instead of one fixed phrase) avoids a false positive on commands whose
# text merely discusses or writes out that phrase, such as editing this
# very script.
if [[ "$COMMAND" != *"git"* || "$COMMAND" != *"commit"* ]]; then
  echo '{}'
  exit 0
fi

# Only enforce test coverage when the commit actually touches backend
# production Java code. Commits that only touch repo tooling (this hook's
# own files, docs, config, etc.) have nothing for a Test.java to cover.
STAGED_PROD_JAVA=$(git diff --cached --name-only | grep -E "^backend/src/main/.*\.java$" || true)

if [[ -z "$STAGED_PROD_JAVA" ]]; then
  echo '{"hookSpecificOutput": {"hookEventName": "PreToolUse", "permissionDecision": "allow"}}'
  exit 0
fi

# Check whether the staged diff includes a test file. No test file, no commit.
STAGED_TEST_FILES=$(git diff --cached --name-only | grep -E "Test\.java$" || true)

# The deny/allow decision MUST be nested under `hookSpecificOutput`. A flat
# top-level `permissionDecision` is silently ignored by VS Code (the tool call
# still runs) rather than rejected -- there's no error to signal the mistake,
# so this is easy to get wrong and not notice until a commit slips through.
if [[ -z "$STAGED_TEST_FILES" ]]; then
  echo '{"hookSpecificOutput": {"hookEventName": "PreToolUse", "permissionDecision": "deny", "permissionDecisionReason": "This commit changes production code with no accompanying test file. Add or update a test before committing, or explain why none is needed."}}'
  exit 0
fi

echo '{"hookSpecificOutput": {"hookEventName": "PreToolUse", "permissionDecision": "allow"}}'

# Note: this file does not need to be committed for VS Code to discover and
# run it -- hooks are read from disk on every call regardless of git tracking
# status. It should still be committed (and was, after being caught
# reverting silently between sessions while untracked): an untracked prompt
# or skill file reverting just means it silently isn't used, but an
# untracked enforcement hook reverting fails open -- the exact commit it's
# meant to block sails through with no error. Commit hooks early because the
# blast radius of losing them is worse, not because they wouldn't run
# otherwise.

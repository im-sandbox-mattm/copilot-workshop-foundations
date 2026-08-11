# Workshop 8 Lab: Copilot Across the SDLC

**Time:** approximately 30–40 minutes

In this lab, you will use GitHub Copilot across a small development workflow:

**Read → Do → Verify**

You will complete the workflow using either:

- **Path A:** a real work item and your approved MCP-connected systems; or
- **Path B:** a provided feature request in the PetCareOps application.

---

## How to Use This Lab

1. **Choose Path A or Path B.** Follow that path's instructions throughout the lab.
2. Complete the stages in order: **READ → DO → VERIFY**.
3. Use Copilot with the actual work item, repository, code, and verification results available in your selected path.
4. Complete each stage's checkpoint before continuing to the next stage.

---

# Choose One Path

## Path A — Your Work + Approved MCP

Use a **real work item from your team's work-management system** — such as Jira or GitLab — and take it through a small portion of the SDLC with Copilot.

You will use an approved MCP connection to retrieve the work item and any supporting context you need. You will then use Copilot to understand the request, make a small local code or test change, verify it, and review the result.

### Choose this path if you have:

- access to an approved Jira or GitLab MCP connection;
- a real work item you are permitted to use during the workshop; and
- the relevant codebase available locally, where you can safely make a small code or test change.

Choose a **code-oriented work item with a reasonably small implementation scope for this lab**.

If you do not have all three, use **Path B**.

### MCP safety

During the core lab, use connected Jira, GitLab, Confluence, or other shared systems for **read-only context**.

Do not create or update tickets, branches, merge requests, pipelines, or other shared resources unless you are working in an explicitly approved sandbox.

---

## Path B — PetCareOps

Use the provided **PetCareOps** application and a feature-request ticket supplied in this lab.

You will use Copilot to explore the existing application, understand the request, decide on an implementation approach, build the feature locally, verify it, and review the result.

Your feature request begins in the **READ** section.

### Setup

If you already have the repository:

```bash
git fetch origin
git switch -c sdlc-lab-local origin/workshop-08-sdlc-prep
```

If you're cloning it for the first time:

```bash
git clone https://github.com/im-sandbox-mattm/copilot-workshop-foundations.git
cd copilot-workshop-foundations
git switch -c sdlc-lab-local origin/workshop-08-sdlc-prep
```

Work on your local `sdlc-lab-local` branch. You do not need to push your changes.

> If you already have a local branch named `sdlc-lab-local`, use another local branch name.

### Requirements

- Java 21
- Node.js `^20.19.0 || >=22.13.0`

Useful backend commands:

```bash
cd backend
./mvnw test
./mvnw spring-boot:run
```

Useful frontend commands:

```bash
cd frontend
npm ci
npm test
npm run build
npm run lint
npm run dev
```

Frontend: `http://localhost:5173`  
Backend: `http://localhost:8080`

You do not need to run every command at every stage. Use the checks that are relevant to the work you changed.

If a command fails or you are unsure what its output means, use Copilot to help interpret the evidence and decide what to check next.

> **Sample data:** PetCareOps uses sample workshop data. The appointment, customer, pet, and clinician records shown on the dashboard are hard-coded for this exercise. You do not need to configure or populate a production database.

---

# READ — Understand Before Editing

**Goal:** Understand the requested change, relevant context, and proposed implementation approach before changing code.

Follow the instructions for the path you selected.

---

## Path A — Your Work + Approved MCP

Use Copilot and your approved MCP connection to:

- retrieve the work item you selected;
- retrieve only the supporting context needed to understand the request;
- identify the problem and acceptance criteria;
- identify the smallest useful implementation slice;
- identify the local files likely to be involved; and
- surface unresolved questions, assumptions, or risks.

Ground your analysis in the **actual work item and local repository**.

Do not change code during this stage.

### Checkpoint

Before continuing, be able to explain:

- what the work item is asking for;
- your proposed approach;
- the scope of the change; and
- any important assumptions or unresolved questions.

---

## Path B — PetCareOps

### Feature Request Ticket

**Add CSV export to Today's Appointments.**

#### Acceptance criteria

- A user can download the appointments currently shown in **Today's Appointments** as a CSV file.
- Nested customer, pet, and clinician information is represented as useful CSV columns.
- Commas, quotation marks, and line breaks in values do not corrupt the CSV.
- The existing dashboard continues to work normally.

Some implementation details are intentionally unspecified. You will need to make and justify reasonable engineering decisions, including:

- which appointment fields should become CSV columns;
- how nested values should be represented; and
- how `startsAt` should be represented.

Multiple reasonable solutions are possible.

### Explore the request

Use Copilot to inspect the repository and determine:

- where Today's Appointments is rendered;
- where its data comes from;
- what appointment data is currently available;
- the smallest reasonable approach to the requested export;
- which files are likely to change; and
- what decisions you need to make about columns, nested data, and time representation.

Ground your analysis in the **actual PetCareOps repository**.

Do not change code during this stage.

### Checkpoint

Before continuing, be able to explain:

- what the feature request is asking for;
- your proposed approach;
- the scope of the change; and
- any important assumptions or unresolved questions.

---

# DO — Implement the Change

**Goal:** Use Copilot to implement and locally exercise the change you scoped during READ.

Follow the instructions for the path you selected.

---

## Path A — Your Work + Approved MCP

Use Copilot to implement or improve the small local code or test change you identified during READ.

- Keep the change bounded to the selected work item.
- Use the evidence and assumptions you identified during READ.
- Review Copilot's proposed edits before accepting them.
- Adjust the implementation when Copilot's first approach does not fit the codebase or requirements.
- Do not push changes, open a merge request, or update shared work items unless explicitly authorized.

### Checkpoint

Before continuing, confirm that the change works locally well enough to move into verification.

---

## Path B — PetCareOps

Use Copilot to implement the CSV-export feature for **Today's Appointments**.

- Use what you discovered about the existing application during READ.
- Make the engineering decisions you identified about CSV columns, nested data, and time representation.
- Keep the implementation focused on the requested feature.
- Review Copilot's proposed edits before accepting them.
- Adjust the implementation when Copilot's first approach does not fit the codebase or acceptance criteria.

There is more than one reasonable implementation approach. Base your decisions on the existing application and the feature request.

### Checkpoint

Confirm that the feature works locally before moving into verification.

---

# VERIFY — Test and Review the Change

**Goal:** Run relevant verification checks and use Copilot to review the changes you made.

Follow the instructions for the path you selected.

---

## 1. Test the Behavior

### Path A

Use Copilot to identify whether the existing tests adequately verify the behavior you changed.

Add or improve meaningful tests where needed.

Focus on behavior relevant to the selected work item rather than expanding into unrelated test coverage.

### Path B

Use Copilot to add or improve meaningful tests for the CSV behavior.

Your verification should cover at least:

- normal appointment data;
- an empty appointment list; and
- CSV-sensitive values such as commas, quotation marks, or line breaks.

The goal is to verify the behavior, not simply increase the number of tests.

---

## 2. Run Relevant Checks

### Path A

Use your repository's existing test, build, lint, or other relevant verification commands.

If you are unsure what those commands are, use Copilot to inspect the repository configuration and identify the existing commands before running them.

Use the actual command output when deciding whether the change is ready.

### Path B

For frontend changes, available checks include:

```bash
cd frontend
npm test
npm run build
npm run lint
```

If your implementation changes backend behavior, also run:

```bash
cd backend
./mvnw test
```

Run the checks that are relevant to the implementation you chose.

If a check fails, use Copilot to help interpret the failure, then run the relevant check again after making any correction.

---

## 3. Review the Actual Changes

Ask Copilot to review the **actual SCM changes you made**.

Have it evaluate the changes for:

- correctness;
- error handling;
- security or unintended data exposure;
- maintainability; and
- missing or weak tests.

Ask for concrete `file:line` findings where applicable.

Treat this as **review only**.

Evaluate the findings yourself before deciding whether any additional changes are needed.

---

# Done

You are done when:

- you used Copilot to understand and scope the work;
- you produced a working local change;
- relevant tests and checks have run; and
- Copilot reviewed the changes you made.

You do **not** need to push your implementation or create or update shared tickets, branches, merge requests, or pipelines to complete the core lab.

---

# Optional Stretch

If you finish early, choose one activity appropriate to your path.

### Path A

- Investigate an additional edge case related to your work item.
- Identify and improve a weak test.
- Draft a backlog-ready follow-up item based on something you discovered during implementation or review.

### Path B

- Add another CSV edge case and verify it.
- Identify and improve a weak test.
- Apply the same export concept to the clinician-load data.
- Draft a backlog-ready work item for a follow-up improvement.

If you have an explicitly approved Jira or GitLab sandbox, you may optionally use MCP to create the follow-up work item.

Otherwise, keep the draft local.

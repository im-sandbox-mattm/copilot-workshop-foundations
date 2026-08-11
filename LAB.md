# Workshop 8 Lab: Copilot Across the SDLC

**Time:** approximately 30–40 minutes

In this lab, you will use Copilot across a small development workflow:

**Read → Do → Verify**

Choose one of the two paths below.

---

## Before You Start

### If you're using PetCareOps

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

### PetCareOps requirements

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

The frontend runs at:

```text
http://localhost:5173
```

The backend runs at:

```text
http://localhost:8080
```

You do not need to run every command at every stage. Use the checks that are relevant to the work you changed.

If a command fails or you are unsure what its output means, use Copilot to help interpret the evidence and decide what to check next.

> **Sample data:** PetCareOps uses sample workshop data. The appointment, customer, pet, and clinician records shown by the dashboard are intentionally provided for the exercise. You do not need to configure or populate a production database.

### MCP safety

During the core lab, use connected Jira, GitLab, Confluence, or other shared systems for **read-only context**.

Do not create or update tickets, branches, merge requests, pipelines, or other shared resources unless you are working in an explicitly approved sandbox.

---

# Choose One Path

## Path A — Your Work + Approved MCP

Choose this path if you have:

- access to an approved Jira or GitLab MCP connection; and
- a small real work item that is safe to work on locally.

Choose a code-oriented item that can result in a small local code or test change.

If those conditions are not true, use **Path B**.

---

## Path B — PetCareOps

Use the provided PetCareOps repository.

### Your work item

**Add CSV export to Today's Appointments.**

### Acceptance criteria

- A user can download the appointments currently shown in **Today's Appointments** as a CSV file.
- Nested customer, pet, and clinician information is represented as useful CSV columns.
- Commas, quotation marks, and line breaks in values do not corrupt the CSV.
- The existing dashboard continues to work normally.

Some implementation details are intentionally unspecified. You will need to make and justify reasonable engineering decisions, including:

- which appointment fields should become CSV columns;
- how nested values should be represented; and
- how `startsAt` should be represented.

Multiple reasonable solutions are possible.

---

# READ

Use Copilot to understand and bound the work **before changing code**.

### Path A

Use Copilot and your approved MCP connection to:

- retrieve the selected Jira or GitLab work item;
- retrieve only the supporting context needed to understand the work;
- identify the problem and acceptance criteria;
- identify the smallest useful implementation slice;
- identify the local files likely to be involved; and
- surface unresolved questions, assumptions, or risks.

Do not change code during this stage.

### Path B

Use Copilot to inspect the repository and determine:

- where Today's Appointments is rendered;
- where its data comes from;
- what appointment data is currently available;
- the smallest reasonable approach to the requested export;
- which files are likely to change; and
- what decisions you need to make about columns, nested data, and time representation.

Do not change code during this stage.

### Checkpoint

Before continuing, be able to explain:

- your proposed approach;
- the scope of the change; and
- any important assumptions or unresolved questions.

---

# DO

Use Copilot to implement the **smallest useful change**.

### Path A

- Implement or improve the selected local code slice.
- Keep the change bounded to the work item.
- Review Copilot's proposed edits before accepting them.
- Do not push, open a merge request, or update shared work items unless explicitly authorized.

### Path B

- Implement CSV export for Today's Appointments.
- Make the engineering decisions you identified during READ.
- Keep the change focused on the requested feature.
- Review and adjust Copilot's edits rather than accepting them blindly.

You may use the Copilot surface or workflow that makes sense for the task.

### Checkpoint

Confirm that the feature works locally before moving on.

---

# VERIFY

Use deterministic checks **and** Copilot review.

## 1. Test the behavior

Add or improve meaningful tests for the behavior you changed.

For the PetCareOps CSV exercise, verification should cover at least:

- normal appointment data;
- an empty appointment list; and
- CSV-sensitive values such as commas, quotation marks, or line breaks.

## 2. Run relevant checks

For PetCareOps frontend changes, available checks include:

```bash
npm test
npm run build
npm run lint
```

If your implementation changes backend behavior, also run:

```bash
./mvnw test
```

For Path A, use your repository's existing test, build, or lint commands. If you are unsure what they are, use Copilot to inspect the repository configuration and identify the existing commands.

## 3. Review the actual changes

Ask Copilot to review the SCM changes you made.

Have it evaluate the changes for:

- correctness;
- error handling;
- security or unintended data exposure;
- maintainability; and
- missing or weak tests.

Ask for concrete `file:line` findings where applicable.

Treat this as **review only**. Do not automatically apply every suggested change.

---

# Done

You are done when:

- you used Copilot to understand and scope the work;
- you produced a working local change;
- relevant tests and checks have run; and
- Copilot reviewed the actual changes you made.

---

# Optional Stretch

If you finish early, choose one:

- Add another CSV edge case and verify it.
- Identify and improve a weak test.
- Apply the same export concept to the clinician-load data.
- Draft a backlog-ready work item for a follow-up improvement.

If you have an explicitly approved Jira or GitLab sandbox, you may optionally use MCP to create that follow-up work item.

Otherwise, keep the draft local.

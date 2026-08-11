# Workshop 8 Lab: SDLC Preparation

## Objective

Practice the early stages of the software development lifecycle (SDLC) by
turning a stakeholder feature request into clear, reviewable requirements
artifacts. In this lab you will **not** write implementation code. You will
produce the planning artifacts a team would use before any code is written.

## Setup

1. Fetch the workshop starting branch:

   ```bash
   git fetch origin workshop-08-sdlc-prep
   ```

2. Create your own local working branch from it:

   ```bash
   git checkout -b sdlc-lab-local origin/workshop-08-sdlc-prep
   ```

All of your work for this lab should be committed to your local
`sdlc-lab-local` branch.

## Verify Your Environment

Confirm the baseline application runs before you start planning.

Backend:

```bash
cd backend
java -version
./mvnw test
./mvnw spring-boot:run
```

Confirm the backend responds:

```
GET http://localhost:8080/api/dashboard
```

Frontend (in a separate terminal):

```bash
cd frontend
npm install
npm test
npm run build
npm run lint
npm run dev
```

The frontend expects the backend at `http://localhost:8080` and runs on
`http://localhost:5173` by default.

## The Feature Request

Clinic reception staff have asked for a way to export the current day's
dashboard appointment data so it can be shared with staff who do not have
access to the application (for example, a covering receptionist or an
off-site scheduling coordinator).

Stakeholders have described the need only in business terms:

- "I want to hand someone a file with today's appointments in it."
- "It should be easy to open in a spreadsheet."
- "It shouldn't slow down the dashboard people are already using."

No technical approach has been decided yet. That is your job in this lab.

## Your Task

Working from the feature request above, produce the following planning
artifacts:

1. **Feature summary** — a short, plain-language restatement of the problem
   and the value it delivers to clinic staff.
2. **User stories** — written in standard "As a ___, I want ___, so that
   ___" form, covering the primary use case and at least one edge case.
3. **Acceptance criteria** — specific, testable conditions that must be true
   for each user story to be considered done.
4. **Non-functional considerations** — note any relevant concerns such as
   data privacy, performance, and error handling expectations.
5. **Open questions** — a list of questions you would bring back to the
   stakeholder or the team before implementation begins.

## Constraints

- Do not write or modify any application code, tests, or configuration.
- Do not describe or decide *where* in the codebase this feature would be
  implemented, or *how* it would be implemented technically. This lab is
  about requirements and planning only.
- Keep your artifacts in your own notes or a new document on your
  `sdlc-lab-local` branch. Do not modify existing project files.

## Deliverables

By the end of the lab you should have:

- A feature summary
- A set of user stories
- Acceptance criteria for each user story
- A short list of non-functional considerations
- A list of open questions

## Wrap-Up

Commit your deliverables to your local `sdlc-lab-local` branch. Be ready to
share your user stories and acceptance criteria with the group for
discussion.

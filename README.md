# Copilot Workshop Foundations

This repo is the exercise baseline for workshop modules 1-6.

Attendee-safe handouts for the drafted modules live under `handouts/` so attendees can use one repo for both instructions and exercise assets.

## Purpose

This is a compact, production-style training application designed to support:

- prompting and context exercises
- test generation
- refactoring
- secure coding
- PR review
- token optimization and instruction design

## Stack

- Java 21
- Spring Boot 3.5
- React 19
- TypeScript
- Maven for backend builds
- npm and Vite for frontend builds

## Business Story

The application models a small pet-care operations platform used by reception staff and clinicians.

Core workflow:

1. staff manage customers and their pets
2. appointments are scheduled with clinicians
3. clinicians capture visit notes and treatment recommendations
4. the frontend shows appointment history, current visit state, and follow-up actions

The code should feel like a trimmed internal product, not a toy demo.

## Domain Model

Planned entities for the initial baseline:

- `Customer`: primary contact details and preferred communication channel
- `Pet`: species, breed, age, owner relationship, care flags
- `Appointment`: scheduled time, status, clinician, reason for visit
- `Clinician`: name, specialty, availability snapshot
- `VisitNote`: summary, treatment plan, medication or follow-up instructions

## Design Principles

- realistic enough to feel like normal product code
- small enough to explain quickly in a live workshop
- prepared with reset points per module
- seeded with intentionally weak Copilot context that improves across modules
- balanced backend and frontend surfaces so Chat and Agent demos can cross the stack

## Repo Shape

Current baseline structure:

```text
copilot-workshop-foundations/
	backend/
		src/main/java/... 
		src/test/java/...
		pom.xml
	frontend/
		src/
		package.json
	handouts/
		module-01/
		...
	.github/
		copilot-instructions.md
		instructions/
		prompts/
	workshop-assets/
		starter/
		solution/
	README.md
```

## Current Executable Slice

The first real workflow is a clinic dashboard view:

- backend endpoint: `GET /api/dashboard`
- seeded in-memory data for customers, pets, clinicians, and appointments
- frontend dashboard that fetches and renders the daily appointment board
- starter `.github/copilot-instructions.md` plus a stronger module-2 variant in `workshop-assets/solution/`

This is deliberately small, but it is real code and a real API/UI flow.

## Module Slices

The same repo should support different exercise slices without forcing one long dependency chain.

| Module | Primary surface | Planned exercise shape |
| --- | --- | --- |
| 1. Beyond Autocomplete | backend + frontend + minimal `.github` context | show Ask vs Agent, `@workspace`, and a before/after instructions demo |
| 2. Context Engineering | `.github/copilot-instructions.md`, prompt assets, a few representative files | improve prompts, open-tab context, and instruction quality |
| 3. Legacy Modernization | backend modernization lab plus optional React starter asset | narrow migration task with test-first validation and clear reset |
| 4.1 Testing | service layer plus one frontend component | generate missing tests and improve coverage |
| 4.2 Refactoring | intentionally messy reminder drafting service | plan-first refactoring with checkpoints |
| 5. Secure Coding | one backend flaw and one frontend flaw | use `/fix`, review prompts, and secure comment patterns |
| 6.1 PR Review | prepared diff or review branch plus reusable review prompt | review AI-generated and human-generated changes |
| 6.2 Token Optimization | verbose starter instructions plus optimized solution assets | reduce noise, tighten scope, and compare results |

## Staged Copilot Assets

Start intentionally thin:

- minimal `copilot-instructions.md`
- no path-specific instructions yet
- generic prompt examples only

Then improve across modules:

- stronger repository instructions in module 2
- legacy modernization lab in module 3 plus an optional React starter asset in `workshop-assets/starter/module-03/`
- testing-specific prompt patterns in module 4.1
- security-focused constraints in module 5
- review prompt asset plus prepared review branch in module 6.1
- token-optimization starter and solution assets in module 6.2

## Delivery Milestones

### Milestone 1

Repo definition and workshop mapping:

- business story
- domain entities
- repo layout
- module-to-surface mapping

### Milestone 2

Executable baseline:

- Spring Boot backend scaffold
- React frontend scaffold
- one complete dashboard and appointment flow
- one intentionally weak instructions file

### Milestone 3

Workshop-ready slices:

- missing-test slice
- messy-service slice
- seeded security flaws
- reset tags or branches per module

## Attendee Setup

For most modules, attendees do not need to run the application locally.

Minimum recommended setup for everyone:

- Git
- a Copilot-enabled IDE such as VS Code or IntelliJ
- access to GitHub Copilot Chat
- the ability to clone the repo and run `git fetch --all --tags`

Optional local runtime setup for attendees who want to run checks themselves:

- JDK 21
- Node 20+ with npm

The repo already includes the Maven wrapper and frontend package scripts, so attendees do not need a global Maven install or extra React tooling.

Modules 03, 04.1, and 04.2 benefit the most from local backend test execution. Modules 01, 02, 05, 06.1, and 06.2 can still be followed effectively if the trainer runs commands live.

## Running The Baseline

Backend:

```bash
cd backend
# macOS/Linux: point JAVA_HOME at a JDK 21 install if it is not already set
# Windows PowerShell: use .\mvnw.cmd instead of ./mvnw
./mvnw spring-boot:run
```

Frontend:

```bash
cd frontend
npm install
npm run dev
```

The frontend expects the backend at `http://localhost:8080` and runs on `http://localhost:5173` by default.

## Immediate Next Build Step

1. replace the in-memory dashboard service with a slightly messier service layer for testing and refactoring modules
2. seed reset points and stronger `.github` assets for module 2 and later

## Status

Executable project scaffolding is in place. The next milestone is adding module-specific slices on top of the current dashboard flow.
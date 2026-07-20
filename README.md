# Copilot Workshop Foundations

This repo is the exercise baseline for workshop modules 1-6.

Module handouts live under `handouts/` so you can use one repo for both instructions and exercise assets.

## What This Repo Is For

This is a compact, production-style training application used throughout the workshop to practice:

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

The code is small enough to explore quickly, but it is still meant to feel like a realistic application rather than a toy example.

## Domain Model

Main entities in the baseline:

- `Customer`: primary contact details and preferred communication channel
- `Pet`: species, breed, age, owner relationship, care flags
- `Appointment`: scheduled time, status, clinician, reason for visit
- `Clinician`: name, specialty, availability snapshot
- `VisitNote`: summary, treatment plan, medication or follow-up instructions

## How To Use This Repo

- use the handout for the module you are currently in
- create a fresh branch from the recommended reset tag for each code-changing module
- keep the repo focused on the current exercise instead of trying to explore every asset at once
- use the backend and frontend files named in the handout as your primary working set

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
- in-memory data for customers, pets, clinicians, and appointments
- frontend dashboard that fetches and renders the daily appointment board
- starter `.github/copilot-instructions.md` plus additional comparison assets used in later modules

This is a small but complete API and UI flow, which makes it a good baseline for the later exercises.

## Module Overview

Each module uses a different slice of the same repo. You do not need to understand the whole codebase before you start.

| Module | Primary surface | Core focus |
| --- | --- | --- |
| 1. Beyond Autocomplete | backend + frontend + minimal `.github` context | explore Ask vs Agent, `@workspace`, and basic instruction impact |
| 2. Context Engineering | `.github/copilot-instructions.md`, prompt assets, and representative files | improve prompts, file context, and instruction quality |
| 3. Legacy Modernization | backend modernization lab plus optional React asset | modernize a focused slice with validation |
| 4.1 Testing | service layer plus existing tests | generate missing tests and improve coverage |
| 4.2 Refactoring | follow-up reminder service | use analysis-first, narrow refactoring steps |
| 5. Secure Coding | frontend preview flow and backend template preview flow | identify and fix common security issues |
| 6.1 PR Review | review branch plus reusable review prompt | review changes, apply fixes, and add regression tests |
| 6.2 Token Optimization | verbose starter instructions plus optimized assets | reduce noise, tighten scope, and compare results |

## What You Will Find In The Repo

- a small starter `.github/copilot-instructions.md`
- module handouts under `handouts/`
- starter and comparison assets under `workshop-assets/`
- backend and frontend code slices used by different modules

## Attendee Setup

For most modules, you do not need to run the application locally.

Recommended setup for everyone:

- Git
- a Copilot-enabled IDE such as VS Code or IntelliJ
- access to GitHub Copilot Chat
- the ability to clone the repo and run `git fetch --all --tags`

Optional local runtime setup if you want to run checks yourself:

- JDK 21
- Node 20+ with npm

The repo already includes the Maven wrapper and frontend package scripts, so you do not need a global Maven install or extra React tooling.

Modules 03, 04.1, and 04.2 benefit the most from local backend test execution. The other modules can still be followed effectively even if you do not run the code yourself.

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

## Working Through The Modules

1. open the handout for your current module under `handouts/`
2. create the branch or reset point named in that handout
3. open the files listed in the handout before starting the prompts
4. stay focused on the current module rather than trying to work ahead across multiple slices

## Status

This repo is ready to use for the workshop modules covered in the handouts.
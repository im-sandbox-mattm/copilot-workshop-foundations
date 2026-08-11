# Copilot Workshop Foundations

PetCareOps is a compact full-stack application used for hands-on GitHub Copilot workshop exercises.

The application models a small pet-care operations platform used by reception staff and clinicians. The current dashboard displays clinic information, clinician load, and today's appointments using sample workshop data.

## Workshop Lab

If you are participating in **Workshop 8: Copilot Across the SDLC**, start here:

**[LAB.md](./LAB.md)**

The lab contains the participant setup instructions, exercise paths, acceptance criteria, and verification guidance.

## Stack

- Java 21
- Spring Boot 3.5
- React 19
- TypeScript
- Maven
- npm
- Vite
- Vitest

## Repository Structure

```text
copilot-workshop-foundations/
├── .github/
│   ├── copilot-instructions.md
│   ├── instructions/
│   └── prompts/
├── .vscode/
├── backend/
│   ├── src/main/java/
│   ├── src/test/java/
│   ├── mvnw
│   └── pom.xml
├── frontend/
│   ├── src/
│   ├── package.json
│   └── package-lock.json
├── LAB.md
└── README.md
```

## Requirements

### Backend

Java 21 is required.

Confirm your version:

```bash
java -version
```

### Frontend

Use Node.js:

```text
^20.19.0 || >=22.13.0
```

with npm.

## Run the Application

### Backend

From the repository root:

```bash
cd backend
./mvnw spring-boot:run
```

The backend runs at:

```text
http://localhost:8080
```

The dashboard API is available at:

```text
GET http://localhost:8080/api/dashboard
```

### Frontend

In a separate terminal:

```bash
cd frontend
npm ci
npm run dev
```

The frontend runs at:

```text
http://localhost:5173
```

The frontend expects the backend to be running at `http://localhost:8080`.

## Verify the Baseline

### Backend

```bash
cd backend
./mvnw test
```

### Frontend

```bash
cd frontend
npm test
npm run build
npm run lint
```

You do not need to run every command after every change. Use the checks relevant to the work you are performing.

## Sample Data

PetCareOps uses sample workshop data for the dashboard.

Appointment, customer, pet, clinician, and related dashboard information is provided by the application for the exercises. You do not need to configure or populate a production database to use the workshop application.

## Copilot Configuration

The repository includes reusable GitHub Copilot configuration under `.github/`, including repository instructions, path-specific instructions, and prompt assets.

Treat these files as part of the repository context when using Copilot, just as you would other project-level engineering guidance.

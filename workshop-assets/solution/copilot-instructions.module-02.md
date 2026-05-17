# Copilot Instructions

- This repo is a workshop foundations app for a pet-care operations domain.
- Backend conventions: Java 21, Spring Boot 3.5, constructor injection, records for response DTOs, no field injection, and no unnecessary frameworks.
- Frontend conventions: React 19 with TypeScript, explicit interfaces for API responses, fetch-based data access unless a task requires a library, and accessible UI labels.
- Keep edits scoped to the requested workflow. Do not rewrite unrelated files.
- Prefer small service and controller methods with clear names over deeply nested logic.
- Preserve realistic internal-product code style rather than toy examples.
- For generated backend APIs, keep response shapes stable and easy to inspect in Chat.
- For frontend work, handle loading and error states explicitly.
- Do not add comments unless they clarify non-obvious behavior.
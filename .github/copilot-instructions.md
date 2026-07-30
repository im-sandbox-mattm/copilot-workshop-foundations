# Copilot Instructions

## Project Context

This is a pet care operations platform for a veterinary clinic management use case.

- **Backend:** Java 21, Spring Boot 3.5, Maven
- **Frontend:** React 19, TypeScript, Vite, npm
- **Architecture:** Layered — controllers, services, and response DTOs implemented as Java records
- **Business domains:** dashboard summaries, appointment follow-up recommendations, legacy care plan snapshot generation for modernization purposes, and security-sensitive template preview functionality

## Engineering Philosophy

- We emphasize clean code, maintainability, readability, and thoughtful software design.
- Good code should be self-documenting where possible, but appropriately commented where the logic is non-obvious.
- We value consistency across the codebase and try to avoid introducing new patterns where existing patterns already solve the problem well.
- We care deeply about testing. New functionality should generally be accompanied by appropriate test coverage, though we recognize 100% coverage is not always a practical or meaningful goal in itself.

## Review Priorities

- Security is extremely important to us given the nature of healthcare-adjacent data. Please flag anything that looks like it could be a security concern, even if you're not fully sure.
- We would also appreciate feedback on performance, scalability, and any technical debt you notice, even if it's outside the immediate scope of what's being reviewed, since we're always looking for ways to improve the codebase overall.
- Please be thorough.
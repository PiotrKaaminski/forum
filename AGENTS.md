# AGENTS.md

# Global guidelines

## 1. Prompt guidelines
* When the prompt is not clear, ask for clarification

## 2. Infrastructure and Build Tools
* The project uses **Gradle** as the build tool.
* Database: PostgreSQL running in **Docker** (`docker-compose.yml`).
* Static resources and configurations are located in `src/main/resources`.
* Database migrations (Liquibase) are located in `src/main/resources/db/changelog/`.

# Front end guidelines

## 1. Project structure
* Frontend project is in the folder frontend/
* frontend/src/ contains the source code for the application
* You can scan other folders for reference relating to data validation, structure, or api schema
* Prefer using popular and well-known libraries
* Do not create a subfolder frontend in the frontend folder

## 2. Tech stack
* Use React
* Use TypeScript
* Use Material UI for UI components
* Use REST API for backend communication
* Use `.env` file for environment variables (prefixed with `REACT_APP_`, e.g., `REACT_APP_API_URL`)

## 3. TypeScript best practices
* Use strict type checking
* Prefer type inference when the type is clear

## 4. Application specification
* Frontend is divided into two sections: Employee and Client
* Employee section is for client management and viewing data
* Client section is for performing the verification process requested by Employee
* Application has to handle translation in English and Polish languages
* Application has to handle two themes: light and dark
* Application has to be responsive

## 5. Styling
* Use Material UI for styling
* Prefer using CSS-in-JS solutions like styled-components or emotion
* Keep styling consistent across components
* Use responsive design principles
* Use consistent typography and spacing
* Follow Material UI guidelines for component usage
* Use consistent spacing and layout
* Use Material UI spacing and layout utilities

## 6. React best practices
* Use functional components
* Use hooks for state management
* Use React Router for navigation
* Use TypeScript for type safety
* Use React Query for data fetching
* Use React Query for loading states
* Use React Query for pagination
* Extract validation logic (e.g., PESEL, document numbers) to `src/utils/`
* Handle API errors (especially HTTP 404 in context of `Result` type) in API services (`src/api/`)
* Divide the file structure based on Employee and Client sections
* Insert Employee pages into the src/pages/employee folder or subfolders
* Insert Client pages into the src/pages/client folder or subfolders

## 7. Tables
* Use Material UI data table for displaying data
* Use Material UI table pagination for pagination
* Use Material UI table sorting for sorting
* Use Material UI table filtering for filtering
* Use Material UI table cell padding for consistent spacing
* Integrate pagination, sorting, and filtering with backend capabilities when possible


# Backend guidelines

## 1. Architecture and Project Structure
* The project follows a Modular Monolith approach with elements of Domain-Driven Design (DDD) and Command Query Separation (CQS).
* Each module (e.g., `verification`, `client`) is divided into layers:
    - `domain`: Contains entities, Value Objects (VO), domain services, and repository interfaces.
    - `application`: Handles business logic and use cases. Communicates with the domain layer. Contains `contract` subpackage for DTOs and service interfaces.
    - `query`: Handles read operations. Contains its own `contract` (DTOs) and a `Facade` to expose data.
    - `infrastructure`: Contains implementations of repositories (JPA), configurations, and other external integrations.
* The `web` package contains REST controllers and security configurations. Controllers should delegate work to application services or query facades.
* **Common logic:** Use `pl.kaminski.powerscore.commons` for shared components like `Result`, `EntityId`, and `DateTimeProvider`.

## 2. Error Handling and API Responses
* Use the `Result<S, E>` or `EmptyResult<E>` wrapper for all application service and query facade returns.
* `ResultResponseBodyAdvice` automatically handles these wrappers:
    - On success: Unwraps and returns the success object with HTTP 200 (or as configured).
    - On error: Returns the error object (implementing `ResultError`) with a designated HTTP status code.
* To specify an HTTP status code for an error, annotate the error class/record with `@ResponseStatus`. The default is 400 (Bad Request).
* Logic should avoid throwing checked exceptions for business errors; use `Result.error()` instead.
* Responses from Entity or ValueObject methods should use their own, as simple as possible, Error type in Result or EmptyResult, preferably enum type.
* Responses from Factory classes should use a Result type with success type as data that the given factory creates and Error type as Error type that is returned from the service calling the given factory method.
* Responses from Service of Facade classes should use helper types that extend Result (if any success data is expected) or EmptyResult (if no success data is expected).
* Helper types extending Result or EmptyResult should define helper methods for creating success or error results to keep the code clean.
* Only error types that are returned from Service of Facade classes should be properly annotated with '@ResponseStatus' to ensure proper HTTP status codes are returned.
* If many error types need to exist in the given Result type, consider using a sealed interface or sealed abstract with concrete final implementations, but prefer sealed interfaces.

## 3. Technologies and Coding Standards
* **Java 21**: Utilize modern features like records and pattern matching.
* **Lombok**: Use for boilerplate reduction (`@Getter`, `@RequiredArgsConstructor`, `@Builder`, etc.).
* **Spring Boot 3.5**: Standard framework for DI, Web, and Data JPA.
* **Database**: PostgreSQL with Liquibase for schema migrations.
    - Add a new `.xml` file to `db/changelog/` for any database schema change (e.g., `powerscore.v2.xml`).
* **Naming Conventions**:
    - Repository interfaces: `IVerificationRepository`.
    - JPA implementations: `VerificationJpaRepository`.
    - DTOs/Contract results: `CreateVerificationResult`, `VerificationInfo`.
* **Value Objects**: Use VOs in the domain layer for validation and type safety (e.g., `DocumentNumberVO`).
    - Preferred pattern: Static `create` method returning `Result`.
* **Factories**: Use Domain Factories (e.g., `VerificationFactory`) for complex object creation.
* **Services** **Facade**: Use Factories for complex object creation or Entity.create() for simple object creation, use **Repositories** interfaces for operation on the database, all methods should be annotated as **@Transactional**.
* **Repositories**: Distinguish between Domain interfaces (`IVerificationRepository`) and Infrastructure implementations (e.g., `VerificationRepository` wrapping `VerificationJpaRepository`).

## 4. Minimal class visibility
* Use `package-private` visibility for classes that are only used within the module, especially in infrastructure package.
* Use `public` visibility for classes that are exposed to the outside world or interfaces from domain, application, and query that need to be implemented in infrastructure

## 5. Spring bean creation standards
* Each module should have its own Configuration class in the infrastructure package.
* Do not annotate Configuration classes with `@Configuration`, import them in the main 'PowerscoreApplication' class.
* Classes from application, domain, and query should not be produced as spring beans if they are not used in other modules, construct them in Configuration class constructor, store in field, and use them as needed.
* Use `@Bean` annotation for classes that need to be produced as spring beans.

## 6. Query package standards
* Use **I{module}QueryFacade** for query facade interface.
* Implement **Q{entity}** entities used specifically for the query, keep them simple, do not use ValueObjects, only primitives, and join to other Query Entities
* Use separate **I{module}QueryRepository** for repository interfaces with implementation in the infrastructure package.
* Use **Q{entity}JpaRepository** for JPA implementations.
* Use **@Transactional(readOnly = true)** for query facade methods.
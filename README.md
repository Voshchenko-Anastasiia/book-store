GENERAL REQUIREMENTS FOR ALL PROJECTS
PRECONDITION: Mandatory passing of tests in Autocode according to the project
description.
Core Requirements
It is necessary to develop a Spring Boot application with a Front-End part, in which it
is necessary:
➢ to use:
1. Spring Data JPA
- repositories
- implementation of custom queries
2. Spring Security
- authentication strategies (In-Memory Authentication, Database-backed Authentication)
- authorization & Access Control (Role-based access control, Method-level security, URL-based
  security)
- security implementation (Enforce strong password policies)
- security implementation (Enable/Disable CSRF protection, Disable exposing stack traces & detailed
  error messages)
3. DTOs (Data Transfer Objects)
- completeness depends on domain
4. SQL Scripts for Initial Data
- script, without using ConsoleRunner
  ➢ to implement:
5. UI internalization (English and local language)
- interface coverage in different languages
- error messages display considering the interface language
6. Data Validation
- implementation of validation in Controllers
- implementation of DTO (validation rules, correctness and data constraints)
- implementation of custom validation rules
- highlighting of validation errors by form fields
7. Exception Handling
- implementation of custom exceptions (if there is a need for specific handling or detailing of system
  exceptions)
- implementation of a global exception handler
- error page setup
8. Logging (business logic events, errors, security events)
- implementation of messages of various levels, including Debug
9. Database Configuration (Embedded H2 or similar)
- implementation for testing stage - in memory
- implementation for production - any not in memory
10. Unit Testing (Cover Services)
- maximum code coverage with tests
11. Order Management
- implementation of functional requirements
  Nice to Have (to implement):
1. Searching, Pagination & Sorting
2. Stateless authentication (JWT-based) to avoid session management issues
3. Use BCrypt for password hashing (BCryptPasswordEncoder)
4. Unit Testing (Cover Controllers)

TOOLS & RECOMMENDATIONS (ALL PROJECTS)
We suggest you use the following tools or frameworks to implement projects:
1. Lombok
2. ModelMapper
3. AOP (Aspect-Oriented Programming) for implementing logging
4. Thymeleaf or another template engine for the front-end part
   ➢ Recommended Spring Security Implementations
1. Authentication Strategies
   • Use In-Memory Authentication (For quick setup/testing – Already suggested in
   Appliance Store task.)
   • Support Database-backed Authentication (Store users in DB via Spring Security &
   JPA.)
   • (optional) Use OAuth2 for third-party authentication (Google, Facebook, etc.).
2. Authorization & Access Control
   • Role-based access control (RBAC) (ADMIN, EMPLOYEE, CUSTOMER, etc.)
   • Method-level security (@PreAuthorize, @PostAuthorize, @Secured annotations.)
   • URL-based security (Define access rules in SecurityFilterChain.)
   • (optional) Custom security expressions (e.g., Checking ownership of an entity.)
3. Password Security
   • Enforce strong password policies (Min length, special characters, etc.)
   • (optional) Allow password reset functionality (e.g., Email-based recovery.)
4. Session & Token Management (optional)
   • Set session timeouts and limits (For non-JWT implementations.)
   • Implement refresh tokens (For long-lived authentication sessions.)
5. Security Best Practices
   • Enable/Disable CSRF protection
   • Disable exposing stack traces & detailed error messages.
   • (optional) Use HTTPS for secure communication.
   • (optional) Limit failed login attempts (Prevent brute-force attacks.)
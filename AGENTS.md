# AGENTS.md - AI Agent Guidance for saas-construction

## Project Context
This is an early-stage SaaS construction project using Java. The repository is bootstrapping with minimal initial structure. Current state: single README.md placeholder and standard Java .gitignore. No significant codebase exists yet.

## Key Principles for This Project

### 1. Project Maturity Expectations
- **Current Stage**: Pre-architecture phase
- Agents should expect significant flexibility in architectural decisions
- When proposing features, clarify architectural implications (monolith vs. microservices, deployment strategy, database approach)
- No established patterns yet—propose and document patterns as they're introduced

### 2. Java SaaS Standards to Follow
- **Build Tool**: Likely Maven or Gradle (not yet established—clarify with user if adding build files)
- **Framework Preference**: Spring Boot is conventional for Java SaaS (unless specified otherwise)
- **Test Requirements**: JUnit 5 for new test files; establish CI/CD expectations early
- **Code Style**: Follow Maven Checkstyle or similar; establish linting rules in the first PR

### 3. Getting Started Checklist for New Features
When asked to implement functionality, verify these foundational items exist:
- [ ] `pom.xml` or `build.gradle` (project structure)
- [ ] `.github/workflows/` (CI/CD pipeline)
- [ ] `src/main/java/` directory structure
- [ ] `src/test/java/` for unit tests
- [ ] `application.properties` or `application.yml` (configuration)
- [ ] Database schema definitions (if needed)

If any are missing, propose them before implementing features.

### 4. Critical SaaS Considerations
**Before implementing any core feature**, discuss with the user:
- **Authentication/Authorization**: Plan identity strategy (JWT, OAuth2, SSO) early
- **Multi-tenancy**: Is this system tenant-aware? Impacts data isolation, routing, query logic
- **Database Design**: Schema decisions affect growth; propose migrations framework (Flyway/Liquibase)
- **API Design**: REST conventions, versioning strategy, error codes
- **Observability**: Logging (SLF4J + Logback), metrics (Micrometer), tracing (Spring Cloud Sleuth)

### 5. Workflow Commands (to be documented once project structure exists)
- Build: `mvn clean package` or `gradle build`
- Test: `mvn test` or `gradle test`
- Run: `mvn spring-boot:run` or `gradle bootRun`
- Code Analysis: SonarQube integration TBD

### 6. Documentation Requirements
As the project grows, maintain:
- **Architecture Decision Records (ADRs)** in `docs/adr/` for major choices
- **API Documentation**: OpenAPI/Swagger specs in `docs/api/`
- **Setup Instructions**: Update README.md with local dev setup steps
- **Configuration Guide**: Document all properties and environment variables

## When Starting New Tasks

### Architecture Phase Questions
Ask the user:
1. What is the primary domain/business logic?
2. Is this single-tenant or multi-tenant SaaS?
3. What is the deployment target? (Cloud platform, on-premise, hybrid)
4. What are the scale expectations? (users, requests/sec, data volume)
5. What are compliance requirements? (GDPR, HIPAA, SOC2, etc.)

### Implementation Approach
- Prefer **feature flags** for gradual rollout in SaaS
- Build **configuration-driven** behavior where possible (12-factor app principles)
- Isolate **cross-cutting concerns** (security, logging, validation) into shared infrastructure
- Use **async patterns** (message queues, event streams) for inter-service communication if architecture splits services later
- Implement **graceful degradation** for external dependencies

### Code Structure (Propose if not established)
```
src/
  main/
    java/com/saas/
      config/          # Configuration beans, properties
      domain/          # Business logic, entities, value objects
      service/         # Business services, use cases
      api/             # REST controllers, DTOs
      persistence/     # JPA repositories, queries
      security/        # Auth, authorization filters
      infra/           # External integrations, utilities
      exception/       # Custom exceptions, global exception handler
    resources/
      application.yml  # Configuration
      db/migration/    # Database migrations (Flyway)
  test/
    java/com/saas/     # Mirror main structure
    resources/         # Test configurations, fixtures
```

## Red Flags to Avoid
- ❌ Adding features without considering authentication/authorization implications
- ❌ Hardcoding configuration values (use environment variables or property files)
- ❌ Implementing without corresponding tests
- ❌ Creating tightly-coupled components; SaaS systems evolve rapidly
- ❌ Skipping API versioning strategy; breaking changes hurt customers
- ❌ Ignoring data privacy/security from day one (technical debt compounds)

## References
- [Agents.md Standard](https://agents.md/) - Format reference
- [Spring Boot Best Practices](https://spring.io/guides)
- [SaaS Architecture Patterns](https://microservices.io/) - For future scaling decisions
- [12-Factor App](https://12factor.net/) - Configuration and environment management

---
**Last Updated**: April 2026 | **Project Status**: Bootstrap Phase


## code practice
- modularity
- re-use
- comment and git-messages | documnet (mkdocs)
- peer and slew review
- wix scan and fix | junit pytest UT | sonar qude - code quality and coverage
- no hardcoding  | clear text pwd
- OOPS - Solid , functional , script
- update to date new feature java 8,11,21,23
- use co-pilot to save time, plug-in
- observability
- use library and framework

| **#** | **Category**           | **Best Practice**                                                                     |
| ----- | ---------------------- | ------------------------------------------------------------------------------------- |
| 1     | **Code Style**         | Use meaningful names, consistent naming conventions (e.g., `camelCase`, `snake_case`) |
| 2     |                        | Follow language-specific style guides and use formatters/linters                      |
| 3     | **Modularity**         | Break large functions into smaller, focused ones                                      |
| 4     | **Reusability**        | Follow DRY (Don't Repeat Yourself); reuse code via functions/modules/classes          |
| 5     | **Version Control**    | Use Git; commit often with clear messages; follow branching strategy                  |
| 6     | **Error Handling**     | Handle errors with try-catch or similar; don’t expose sensitive info                  |
| 7     | **Testing**            | Write unit and integration tests; use `pytest`, `JUnit`, `Jest`, etc.                 |
| 8     | **Documentation**      | Comment complex logic; use docstrings; maintain README and API docs                   |
| 9     | **Code Reviews**       | Get peer reviews; use pull requests                                                   |
| 10    |                        | Use static analysis tools (e.g., SonarQube, linters)                                  |
| 11    | **Optimization**       | Don’t prematurely optimize; measure performance before tuning                         |
| 12    | **Security**           | Validate input, escape output, avoid hardcoded secrets                                |
| 13    |                        | Use secret managers like AWS Secrets Manager, Vault                                   |
| 14    | **CI/CD & Automation** | Integrate linting, testing, and security checks in pipelines                          |


## python

| **#** | **Category**              | **Best Practice**                                                                                |
| ----- | ------------------------- |--------------------------------------------------------------------------------------------------|
| 3     | **Naming**                | Use `snake_case` for variables/functions, `PascalCase` for classes, and `ALL_CAPS` for constants |
| 4     | **Imports**               | 🔸Use absolute imports;  avoid wildcard imports (`from x import *`)                                |
| 6     | **Functions & Classes**   | Prefer functions over scripts; use classes for grouping behavior                                 |
| 7     | **Typing**                | Use type hints (`def func(x: int) -> str:`) and validate with `mypy`                             |
| 9     | **Testing**               | Use `pytest` or `unittest`; write tests in a `tests/` folder                                     |
| 10    | **Virtual Environments**  | 🔸Use `venv` or `virtualenv` to isolate project dependencies                                       |
| 11    | **Dependency Management** | Use `requirements.txt` or `pip-tools`; consider `poetry` or `pipenv` for advanced management     |
| 13    | **Security**              | sanitize inputs; manage secrets with `.env` files or tools like AWS Secrets Manager              |
| 14    | **Performance**           | 🔸Use generators (`yield`) for large data; prefer list comprehensions over loops when readable   |
| 15    | **Code Reviews**          | Use pull requests and enforce lint/type/test checks in CI                                        |


## java

| **#** | **Category**           | **Best Practice**                                                                        |
| ----- | ---------------------- |------------------------------------------------------------------------------------------|
| 2     | **Naming**             | `camelCase` for variables/methods, `PascalCase` for classes, `ALL_CAPS` for constants    |
| 3     | **Package Structure**  | Organize by functionality (e.g., `controller`, `service`, `repository`)                  |
| 4     | **Class Design**       | 🔸Follow SOLID principles; keep classes single-responsibility                            |
| 5     | **Method Design**      | Keep methods short and do one thing; prefer composition over inheritance                 |
| 6     | **Access Modifiers**   | Minimize visibility (`private` > `protected` > `public`)                                 |
| 7     | **Exception Handling** | Use custom exceptions; avoid swallowing exceptions; always log them                      |
| 8     | **Logging**            | Use a logging framework (`SLF4J` with `Logback` or `Log4j2`); avoid `System.out.println` |
| 9     | **Null Handling**      | Use `Optional` to avoid `NullPointerException`; avoid returning `null`                   |
| 10    | **Immutable Objects**  | Favor immutability (especially in value classes and DTOs)                                |
| 12    | **Concurrency**        | Use thread-safe collections; prefer `ExecutorService` over manual thread creation        |
| 13    | **Code Quality Tools** | Use tools like `Checkstyle`, `PMD`, `SpotBugs`, and `SonarQube`                          |
| 15    | **Unit Testing**       | Use `JUnit 5`, `Mockito`, `AssertJ` for unit and integration testing                     |
| 17    | **Security**           | Sanitize inputs, validate all external data, avoid serialization of sensitive objects    |

## Solid

| **Letter** | **Principle**                             | **Explanation**                                                                                        | **Example**                                                                                       |
| ---------- | ----------------------------------------- | ------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------- |
| **S**      | **Single Responsibility Principle (SRP)** | A class should have **only one reason to change** (i.e., one job).                                     | A `UserService` should handle user logic, not file saving. Delegate that to `FileStorageService`. |
| **O**      | **Open/Closed Principle (OCP)**           | Software entities should be **open for extension**, but **closed for modification**.                   | Add new behavior via subclasses or strategies—not by changing existing code.                      |
| **L**      | **Liskov Substitution Principle (LSP)**   | Subclasses should be **substitutable** for their base classes without breaking behavior.               | If `Bird` has `fly()`, then `Penguin` shouldn't inherit `Bird` (if it can’t fly).                 |
| **I**      | **Interface Segregation Principle (ISP)** | Don’t force a class to implement **unneeded methods**. Create **small, focused interfaces**.           | Split `Animal` into `Walkable`, `Flyable`, `Swimmable` interfaces.                                |
| **D**      | **Dependency Inversion Principle (DIP)**  | High-level modules should **not depend on low-level modules**. Both should depend on **abstractions**. | `OrderService` should depend on `PaymentProcessor` interface, not `StripePayment`.                |

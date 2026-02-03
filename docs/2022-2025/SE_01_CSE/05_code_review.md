## general
- language/framework best practice
- comment and naming convention
  - helps to in quick code analysis is prod issue siyuation
  - with hard SLA
- simplicity, modularity, reusability
- No plain text password and token.

## My Style
- early PR
- daily commit check
- code review
  - early code review
  - early feedback
  - early fix
- dont touch additional file.
- comment fallback and clean up in next subsequent release.

## code quality/coverage
- **sonar cube**
  - Remove **feild injection** (discouraged)
    - Circluar dependency, voilates SOLID,  NPE, not compatible with final feilds
  - Remove unused imports
  - Remove unused methods/variables
  - change access modifier
  - Add final to variables
  - Add @Override annotation
  - Add @SuppressWarnings annotation
  - Add @Deprecated annotation
  - Add Constant for Duplicate hardcoded string
  - Cognitive Complexity Score : block , identation, nesting, etc
- **gaunlet** :WIZ synk scan result.
- **Junit**

## AI Assisted

## Stories
- copy pasted AF code for SWIFT, complex
- 415
- 401, token on single bean
- Springboot configuration
- Engine response : Big if-else
- Java -stream, Switch expression , Record, etc
# Docker notes

This section is being revised for senior software engineer (SSE) and
forward-deployed engineer (FDE) preparation. The target is not just command
recall: each topic should explain the mental model, production trade-offs,
failure modes, diagnostics, and interview discussion points.

## Reading order

1. [Architecture and container fundamentals](01_fundamental-1.md)
2. [Developer guide: CLI, Dockerfile, and image builds](06_developer-guide-2.md)
3. [Docker Compose](05_docker-compose.md)
4. [Storage](./03_Storage.md)
5. [Networking](./04_network.md)

## Revision status

| Note | Status | Planned focus |
| --- | --- | --- |
| Architecture | Revised | Runtime stack, isolation, OCI, security, lifecycle, senior-level prompts |
| Developer guide | To revise | Correct CLI syntax, Dockerfile semantics, BuildKit, build cache, Java/Python production patterns |
| Compose | To revise | Compose Specification, health checks, dependency readiness, profiles, secrets/configs, production limits |
| Storage | To revise | Writable layer, volumes, bind mounts, `tmpfs`, backup/restore, permissions, performance |
| Networking | To revise | Bridge/DNS, port publishing, host/none modes, IPv6, troubleshooting, security |

## Quality checklist for each note

- Technically correct and based on current primary documentation.
- Separates conceptual knowledge from command reference.
- Explains why a design choice matters in production.
- Includes failure modes and a troubleshooting path.
- Includes security and operational considerations.
- Uses small, valid examples rather than unverified snippets.
- Ends with senior-level questions or scenarios and authoritative references.

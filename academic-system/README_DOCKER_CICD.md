# Docker & CI/CD — Academic System

## Docker (TUS-2381)

### Prerequisites

- [Docker](https://docs.docker.com/get-docker/) installed on your machine.
- No Java or Maven installation required on the host.

### Build the image

```bash
docker build -t academic-system .
```

### Run the application

The container runs the CLI interface and requires interactive keyboard
input (`-it` flags are mandatory):

```bash
docker run -it academic-system
```

To persist data files (`.txt`, `.xml`, `.json`) outside the container:

```bash
docker run -it -v "$(pwd)/data:/app/data" academic-system
```

### Notes

- The application is built with Maven **inside** the Docker image
  (multi-stage build) — no local Maven installation needed.
- The Docker setup does not change any business logic of the system.
- The CLI entry point is `org.example.academic.system.Main`.

---

## CI/CD Workflows (TUS-2415 to TUS-2420)

### CI pipeline (TUS-2415, TUS-2416)

File: `.github/workflows/ci.yml`

Runs automatically on every **push** and **pull request**:
1. Sets up Java 23.
2. Compiles the project with Maven.
3. Runs all JUnit 5 tests.
4. Generates a JaCoCo coverage report (HTML + XML) and uploads it as a
   build artifact — available in the **Actions** tab of the repository.

### PR Validation (TUS-2418)

File: `.github/workflows/pr-validation.yml`

Runs automatically when a pull request is **opened, updated, or
reopened**. The build and test steps must pass before the PR can be
merged (enforced by branch protection rules — see TUS-2420).

### Docker image publishing (TUS-2417)

File: `.github/workflows/docker-publish.yml`

Runs automatically on:
- Push to `main` → publishes image tagged with the short commit SHA and `latest`.
- Push of a version tag (`v*.*.*`) → publishes image tagged with the
  semantic version (e.g., `1.2.3`).

Images are published to the **GitHub Container Registry (GHCR)**:

```bash
docker pull ghcr.io/<owner>/<repo>:latest
docker run -it ghcr.io/<owner>/<repo>:latest
```

### Release workflow (TUS-2419)

File: `.github/workflows/release.yml`

Triggered by pushing a version tag:

```bash
git tag v1.0.0
git push origin v1.0.0
```

The workflow builds and tests the project, then creates a **GitHub
Release** with the executable fat-JAR attached automatically.

### Branch protection (TUS-2420)

See [BRANCH_PROTECTION.md](BRANCH_PROTECTION.md) for the step-by-step
configuration of branch protection rules on the `main` branch.

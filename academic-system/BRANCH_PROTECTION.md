# TUS-2420 — Branch Protection Configuration

## Overview

Branch protection rules enforce that no change can be merged into `main`
without passing the project's quality gates. All settings are managed
through the GitHub repository interface (AC7).

---

## How to configure (GitHub UI)

Go to: **Settings → Branches → Add branch ruleset**

> Alternatively: **Settings → Branches → Add classic branch protection rule**
> and type `main` as the branch name pattern.

### Required settings

| Setting | Value | Acceptance Criteria |
|---|---|---|
| Branch name pattern | `main` | AC1 |
| Restrict pushes that create matching branches | ✅ Enabled | AC2 |
| Require a pull request before merging | ✅ Enabled | AC3 |
| Require status checks to pass before merging | ✅ Enabled | AC4 |
| Required status check — `validate` (PR Validation workflow) | ✅ Selected | AC5, AC6 |
| Require branches to be up to date before merging | ✅ Enabled | AC4 |
| Do not allow bypassing the above settings | ✅ Enabled (recommended) | AC2, AC5, AC6 |

### Step-by-step

1. Navigate to your repository on GitHub.
2. Click **Settings** (top menu).
3. In the left sidebar, click **Branches**.
4. Click **Add branch ruleset** (or **Add classic branch protection rule**).
5. Set **Branch name pattern** to `main`.
6. Enable **Require a pull request before merging**.
7. Enable **Require status checks to pass before merging**.
8. In the status checks search box, type `validate` and select the
   check from the **PR Validation** workflow (it appears after the
   workflow has run at least once).
9. Enable **Require branches to be up to date before merging**.
10. Enable **Restrict who can push to matching branches** and leave the
    allowlist empty to block all direct pushes, including admins.
11. Click **Create** (or **Save changes**).

---

## How protection works in practice

```
Developer pushes to feature branch
         │
         ▼
PR Validation workflow runs automatically (TUS-2418)
   ├─ mvn compile   → fails? PR cannot be merged (AC4, AC5)
   └─ mvn test      → fails? PR cannot be merged (AC5, AC6)
         │
         ▼ (only if all checks pass)
Pull request can be merged into main (AC3, AC4)
         │
         ▼
main remains deployable after every successful merge (AC9)
```

---

## Existing workflows that remain unchanged (AC8)

- `ci.yml` — CI pipeline continues running on push and PR as before.
- `docker-publish.yml` — Docker image publishing on tags/main as before.
- `release.yml` — Release workflow on version tags as before.

Branch protection only adds a merge gate; it does not modify any
workflow file or application logic.

---

## Documentation requirement (AC10)

This file serves as the documentation for the branch protection rules
adopted by the project. It must be kept up to date whenever protection
rules are changed in the GitHub repository settings.

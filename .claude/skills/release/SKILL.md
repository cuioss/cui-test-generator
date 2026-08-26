---
name: release
description: Cut a cui-test-generator release — bump .github/project.yml version, open and merge the release PR, wait for the automated Release workflow, verify the release landed, then reformat the generated GitHub release notes
user-invocable: true
allowed-tools: Bash, Read, Edit, AskUserQuestion
---

# Release Skill

Cuts a new cui-test-generator release end-to-end: determine the version, open the version-bump PR
that triggers the release, merge it, wait for the automated Release workflow, verify the
release landed, and reformat the auto-generated GitHub release notes.

## How the release is wired (read first)

The release is **fully automated by GitHub Actions**. `.github/workflows/release.yml`
triggers on a **merged pull request that changes `.github/project.yml`**:

```yaml
on:
  pull_request:
    types: [closed]
    paths:
      - '.github/project.yml'
```

So this skill never runs Maven release goals by hand. Its job is to produce and merge the
correct `project.yml` change; the reusable `cuioss-organization` release workflow does
the tagging, Maven Central deploy, GitHub release creation, and — because `pages.deploy-at-release: true` — the documentation pages deploy.

Allow **up to ~30 min** after the merge before treating the release as stuck: the workflow
itself is quick, but Maven Central propagation and the GitHub release publish lag behind it.

### This library is consumed by other repos

`project.yml` declares:

```yaml
consumers:
  - cuioss-parent-pom:version.cui.test.generator
  - nifi-extensions:version.cui.test.generator
```

After the release lands, the org automation opens bump PRs in those repos. That is
follow-on automation, **not** part of this skill — but say so in the final report so the
operator expects them. A `cuioss-parent-pom` bump additionally needs **its own release**
before the wider org picks the change up.

## Workflow

### Step 1 — Determine the version number

Read the `release` block in `.github/project.yml`:
- `release.current-version` (currently `3.0.2`) — the **last released** version.
- `release.next-version` (currently `3.1-SNAPSHOT`) — what `pom.xml` carries between releases.

**The choice is genuinely open here** and the skill must not guess:
`current-version` is `3.0.2` but the pom floats on `3.1-SNAPSHOT`, so `current-version` does
**not** sit on the `next-version` line.

- **`3.1`** — strip `-SNAPSHOT`, starting the new minor line; `next-version` then
  moves to the following minor.
- **`3.0.3`** — continue the patch line; `next-version` stays `3.1-SNAPSHOT`.

Both are defensible. **Ask the user** (AskUserQuestion) rather than picking one.

### Step 2 — Check for open PRs

```bash
gh pr list --repo cuioss/cui-test-generator --state open --json number,title,isDraft
```
- **No open PRs** → proceed.
- **Open PRs exist** → these would normally be merged first. Surface the list and **ask the
  user** whether to proceed or wait. Do not silently ignore them.

Confirm the working tree is clean (`git status --porcelain`) before branching.

### Step 3 — Pull current main

```bash
git checkout main && git pull --ff-only origin main
```

### Step 4 — Create the release branch

The Maven CI workflow only triggers on `main, "feature/*", "fix/*", "chore/*", "release/*", "dependabot/**"`. Any other prefix skips the `build`
check and blocks the merge:

```bash
git checkout -b chore/release_3.1
```

### Step 5 — Update `.github/project.yml`

- `current-version:` → the version determined in Step 1
- `next-version:` → for a new minor line, the following minor + `-SNAPSHOT`; for a patch
  release, **leave unchanged** (`3.1-SNAPSHOT`)

Leave everything else untouched. The README badges are dynamic endpoints — there is **no**
per-release badge to hand-edit.

### Step 6 — Commit, push, open PR

```bash
git add .github/project.yml
git commit -m "chore(release): prepare release <version>"
git push -u origin chore/release_<version>
# Create the label only when missing, and let a real failure (permissions, outage) surface -
# swallowing it would make `gh pr create --label` fail with a confusing error instead.
if ! gh label list --repo cuioss/cui-test-generator --search skip-bot-review --json name --jq '.[].name' \
     | grep -qx skip-bot-review; then
  gh label create skip-bot-review --repo cuioss/cui-test-generator \
    --description "Skip automated bot review" --color ededed
fi
gh pr create --repo cuioss/cui-test-generator --base main \
  --title "chore(release): prepare release <version>" \
  --label "skip-bot-review" \
  --body "Bump current-version to <version>. Triggers the automated Release workflow on merge."
```

Commit trailer: `Co-Authored-By: Claude <noreply@anthropic.com>` — no model name, no
"Generated with Claude Code" footer.

### Step 7 — Wait for PR checks

```bash
gh pr checks <pr#> --repo cuioss/cui-test-generator --watch
```

### Step 8 — Handle review comments / failures (if any)

- If a check fails, read the failing run (`gh run view <id> --log-failed`), fix it on the
  branch, push, re-wait. **Never** merge a red PR.
- Every review comment MUST get a reply and MUST be resolved — fix it and say so, or explain
  why not. Enumerate **all** comments, not just the first page:
  ```bash
  gh api --paginate repos/cuioss/cui-test-generator/pulls/<pr#>/comments
  ```
- **Unresolved review threads block the merge.** A PR can show every check green and still
  report `BLOCKED` purely because a bot thread is open. Do not misread that as a
  branch-protection or approval problem.
  ```bash
  gh api graphql --paginate -f query='query($endCursor:String){repository(owner:"cuioss",name:"cui-test-generator"){pullRequest(number:<pr#>){reviewThreads(first:100,after:$endCursor){pageInfo{hasNextPage endCursor} nodes{id isResolved}}}}}'
  gh api graphql -f query='mutation{resolveReviewThread(input:{threadId:"<id>"}){thread{isResolved}}}'
  ```
  Assert the unresolved count is **zero** before merging rather than eyeballing the list.
- Re-run Step 7 after any push.

### Step 9 — Merge → the release starts automatically

```bash
gh pr merge <pr#> --repo cuioss/cui-test-generator --squash --delete-branch
```

If the repo has a merge queue, `--delete-branch` is rejected and the queue picks the
strategy — drop the flag and re-run. Merging the `project.yml` change fires `release.yml`;
do **not** dispatch the release by hand unless the auto-trigger demonstrably did not fire.

### Step 10 — Wait for the Release workflow

```bash
gh run list --repo cuioss/cui-test-generator --workflow "Release" --limit 3 \
  --json status,conclusion,displayTitle,databaseId
gh run watch <databaseId> --repo cuioss/cui-test-generator
```

### Step 11 — Verify the release landed

```bash
gh release view <version> --repo cuioss/cui-test-generator --json tagName,name,isDraft,createdAt
git fetch --tags && git tag --list <version>
curl -sfI https://repo1.maven.org/maven2/de/cuioss/test/cui-test-generator/<version>/cui-test-generator-<version>.pom \
  -o /dev/null -w '%{http_code}\n'
```

Confirm the tag exists, the release is **not** a draft, and the artifact resolves from Maven
Central (`200`). A draft release is invisible to consumers and means the workflow did not
finish publishing — publish it (`gh release edit <version> --draft=false`) and investigate
the run before reporting success.

Verify the **published artifact**, not just the workflow's exit status.

### Step 12 — Reformat the generated release notes

The workflow creates the release with auto-generated notes (a flat `## What's Changed`
list). Rewrite them in place:

```bash
mkdir -p .plan/temp
gh release view <version> --repo cuioss/cui-test-generator --json body --jq .body \
  > .plan/temp/release-<version>-orig.md
# ...build the reformatted body in .plan/temp/release-<version>.md...
gh release edit <version> --repo cuioss/cui-test-generator --notes-file .plan/temp/release-<version>.md
```

#### House format rules

1. **Two top-level groups:** `## Features & Enhancements` and `## Dependency Updates`.
2. **Features & Enhancements** — group functional PRs by theme with `###` subheadings
   adapted to this project's domain; omit empty sections.
3. **Dependency Updates** — `### Java` for libraries, `### Infra` for build plugins,
   `cuioss-organization` workflow bumps and parent-POM updates.
4. **Collapse version chains** — `A → B → C` becomes a single entry spanning `A → C`.
5. **Drop OpenRewrite bumps** — `rewrite-maven-plugin`, `rewrite-migrate-java`,
   `rewrite-testing-frameworks` and friends.
6. **Drop internal tooling churn** — `marshal.json`/plan-marshall config, dev-skill changes,
   and the mechanical version-bump PR itself.
7. Keep each surviving PR line verbatim (`* <title> by @author in <url>`); merge duplicates
   onto one line with both URLs.
8. Keep the trailing `**Full Changelog**: ...compare/<prev>...<version>` line.

### Step 13 — Done

Report: released version, release URL, PR number, how many dependency PRs were
collapsed or dropped, and a reminder that consumer-bump PRs will appear in `cuioss-parent-pom`, `nifi-extensions`.

## Critical rules

- The release is triggered by **merging a `.github/project.yml` change** — never hand-run
  Maven release goals.
- Branch prefix **must** be one CI accepts, or the build check skips and the merge blocks.
- Never merge a red PR; fix and re-wait.
- Every review comment gets a reply **and** gets resolved — unresolved threads block the merge.
- Verify the published release is **not a draft** and actually resolves from Maven Central.
- Temporary files go under `.plan/temp/`.

# openflexo-docx — Known defects

Bugs of openflexo-docx that are known and not fixed yet. Each entry says what is verified, how to reproduce it, and what is fixed or
not. Features and evolutions go to `BACKLOG.md`, not here.

Identifiers are stable and never reused: `DOCX-D-<n>`. Reference them from commits and from other entries.

Status legend: `TODO` · `IN PROGRESS` · `BLOCKED` · `DONE` · `DEFERRED`.

---

## User-interface tests

### DOCX-D-1 — The 42 UI tests of `docx-ta-ui` have never run with a display  ·  `TODO`

**Symptom.** On Jenkins the 42 tests of the UI category of `docx-ta-ui` (for example `TestDocXFragmentRoleInspector`,
`TestDocXImageRoleInspector`, `TestFlexoDocumentViewer`, `TestImageDocumentEditor`, `TestSimpleDocumentEditor`) all fail with
`java.awt.HeadlessException`, in the daily job `openflexo-docx-2.99` as well as in the release job: the agent has no display. The daily
job stays blue because the build plugin ignores test failures.

**What is verified.** `Results: FAILURE (42 tests, 0 successes, 42 failures)` in the console of `openflexo-docx-2.99` and of
`openflexo-docx-release` build #1 (2026-10-16). Nothing else: whether these tests pass with a display is unknown, since they were never
run with one on Jenkins.

**To reproduce.** With a display, from the `openflexo-dev` composite:
`./gradlew :openflexo-docx:docx-ta-ui:cleanUiTest :openflexo-docx:docx-ta-ui:uiTest`.

**What to do.** Run them with a display (locally or add an Xvnc wrapper to the daily job), then fix or triage what fails.

**Consequence for the 2.99 release.** The release job `openflexo-docx-release` runs the `test` task: the UI tests were not executed for
the release (tracked as `JENKINS-D-6` in the Jenkins backlog).

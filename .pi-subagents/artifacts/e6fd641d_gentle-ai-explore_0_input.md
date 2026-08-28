# Task for gentle-ai-explore

Understand the current structure of the project. Specifically:
1. Examine the core domain models in src/main/java/com/emersondev/agendahunter/domain/model/ (Practicante, Tarea, CalendarioSiembra, Complejidad, EstadoTarea).
2. Examine the use cases in src/main/java/com/emersondev/agendahunter/application/usecase/.
3. Highlight any specific business/validation rules (e.g., anti-burnout limits) and how they are currently implemented.
4. Provide a compact summary of how the domain models and use cases interact.

## Acceptance Contract
Acceptance level: attested
Completion is not accepted from prose alone. End with a structured acceptance report.

Criteria:
- criterion-1: Return a concise result and residual risks when applicable

Required evidence: manual-notes, residual-risks

Finish with a fenced JSON block tagged `acceptance-report` in this shape:
Use empty arrays when no items apply; array fields contain strings unless object entries are shown.
```acceptance-report
{
  "criteriaSatisfied": [
    {
      "id": "criterion-1",
      "status": "satisfied",
      "evidence": "specific proof"
    }
  ],
  "changedFiles": [
    "src/file.ts"
  ],
  "testsAddedOrUpdated": [
    "test/file.test.ts"
  ],
  "commandsRun": [
    {
      "command": "command",
      "result": "passed",
      "summary": "short result"
    }
  ],
  "validationOutput": [
    "validation output or concise summary"
  ],
  "residualRisks": [
    "none"
  ],
  "noStagedFiles": true,
  "diffSummary": "short description of the diff",
  "reviewFindings": [
    "blocker: file.ts:12 - issue found, or no blockers"
  ],
  "manualNotes": "anything else the parent should know"
}
```
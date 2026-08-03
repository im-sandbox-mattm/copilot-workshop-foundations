---
applyTo: "backend/src/main/java/com/workshop/petcareops/dashboard/**"
---

- New dashboard services should reuse existing shared components
  (e.g. ReminderChannelAdvisor) rather than reimplementing logic.
- Urgency/escalation criteria must match the definition already used
  in FollowUpRecommendationService, OwnerReminderDraftService, and
  LegacyCarePlanSnapshotService.
- Avoid static mutable state in application code.
- Method names should follow the package convention: getX / buildX.
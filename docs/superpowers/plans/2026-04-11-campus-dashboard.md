# Campus Dashboard Implementation Plan

> **For agentic workers:** REQUIRED: Use superpowers:subagent-driven-development (if subagents available) or superpowers:executing-plans to implement this plan. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a campus second-hand trading admin dashboard with real summary metrics, quick links, and menu access.

**Architecture:** Add a lightweight backend summary endpoint in the existing `system` module that aggregates counts from existing second-hand mappers. Then add a `campus/dashboard` frontend page in `yudao-ui-admin-vue3` that consumes the endpoint and presents summary cards plus quick entry links. Finally, extend the campus menu SQL so the dashboard becomes the admin landing page for this domain.

**Tech Stack:** Spring Boot, MyBatis-Plus, JUnit 5, Mockito, Vue 3, Element Plus, TypeScript

---

### Task 1: Backend Summary API

**Files:**
- Create: `yudao-module-system/src/main/java/cn/iocoder/yudao/module/system/controller/admin/second/dashboard/CampusDashboardController.java`
- Create: `yudao-module-system/src/main/java/cn/iocoder/yudao/module/system/controller/admin/second/dashboard/vo/CampusDashboardSummaryRespVO.java`
- Create: `yudao-module-system/src/main/java/cn/iocoder/yudao/module/system/service/second/CampusDashboardService.java`
- Create: `yudao-module-system/src/main/java/cn/iocoder/yudao/module/system/service/second/CampusDashboardServiceImpl.java`
- Test: `yudao-module-system/src/test/java/cn/iocoder/yudao/module/system/service/second/CampusDashboardServiceImplTest.java`

- [ ] Write failing service test for summary aggregation
- [ ] Run targeted test to verify failure
- [ ] Implement minimal service and controller
- [ ] Run targeted test to verify pass

### Task 2: Frontend Dashboard Page

**Files:**
- Create: `yudao-ui-admin-vue3/src/api/campus/dashboard/index.ts`
- Create: `yudao-ui-admin-vue3/src/views/campus/dashboard/index.vue`

- [ ] Add typed API for dashboard summary
- [ ] Create dashboard page using existing admin page patterns
- [ ] Run `pnpm lint:campus`

### Task 3: Menu SQL And Sync

**Files:**
- Modify: `sql/mysql/campus-second-trade-menu.sql`
- Modify: `文档/进度文档/项目当前进度总览.md`
- Modify: `文档/进度文档/待办清单.md`
- Modify: `文档/进度文档/当前开发顺序说明.md`

- [ ] Add dashboard menu and permission SQL
- [ ] Update progress docs after implementation
- [ ] Verify backend test and frontend lint outputs before reporting

# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

芋道 (yudao-boot-mini) 是一个基于 Spring Boot 3 + JDK 17 的企业级后台管理快速开发平台精简版。完整版见 [ruoyi-vue-pro](https://github.com/YunaiV/ruoyi-vue-pro)。

文档: <https://doc.iocoder.cn/quick-start/> | 演示: <http://dashboard-vue3.yudao.iocoder.cn>

## 常用命令

```bash
# 构建（跳过测试）
mvn clean install -DskipTests

# 只构建可部署的 server 模块
mvn clean package -pl yudao-server -am

# 运行测试
mvn test

# 运行单个测试类
mvn test -Dtest=YourTestClass -pl <module>

# Maven 刷新依赖（用于添加新模块后）
mvn -N ru:万达
```

## 技术栈

| 类别 | 技术 |
|------|------|
| 框架 | Spring Boot 3.5.9, Spring Security + OAuth2 |
| 数据库 | MyBatis-Plus 3.5.15, Druid, dynamic-datasource |
| 缓存 | Redis + Redisson 3.52.0 |
| API 文档 | Knife4j 4.5.0 + SpringDoc 2.8.14 |
| 对象映射 | MapStruct 1.6.3 + Lombok |
| 定时任务 | Quartz (JDBC 存储) |
| 工具库 | Hutool 5.8.42, Easy-Trans |

## 模块架构

```
yudao-server/              # 可部署应用，聚合各模块
yudao-dependencies/        # BOM，统一管理依赖版本
yudao-framework/           # 14 个技术框架 starter（公共基础设施）
  yudao-common/           # 基础工具：CommonResult, PageResult, 异常处理
  yudao-spring-boot-starter-mybatis/     # MyBatis-Plus 集成
  yudao-spring-boot-starter-redis/       # Redis/Redisson
  yudao-spring-boot-starter-web/         # Web/Swagger/XSS/加密
  yudao-spring-boot-starter-security/    # Spring Security + Token
  yudao-spring-boot-starter-biz-tenant/ # 多租户
  yudao-spring-boot-starter-biz-data-permission/ # 行级数据权限
  + 其他 starter...
yudao-module-system/      # 业务模块：用户、部门、权限、字典、通知、短信、邮件、OAuth2
yudao-module-infra/       # 基础设施：代码生成、文件管理、配置、任务、监控、WebSocket
```

精简版只激活了 system 和 infra 模块。其他模块（member, bpm, report, mp, pay, mall, crm, erp, ai, iot）可通过取消注释 root pom.xml 中的 `<module>` 引入。

## 代码组织（业务模块标准结构）

每个业务模块位于 `cn.iocoder.yudao.module.{name}` 下：

```
api/           -- 跨模块调用的 API 接口和 DTO
controller/admin/{feature}/  -- REST 控制器（/admin-api 前缀）
  vo/          -- 请求/响应 VO（SaveReqVO, PageReqVO, RespVO）
convert/       -- MapStruct 转换器（DO <-> VO）
dal/
  dataobject/  -- 数据对象（继承 BaseDO，含 createTime/updateTime/creator/updater/deleted）
  mysql/       -- MyBatis Mapper（继承 BaseMapperX）
service/       -- Service 接口和实现
enums/         -- 枚举类
job/           -- 定时任务
mq/{consumer,producer}/  -- 消息队列
```

## 核心模式

**三层架构**: Controller（权限校验 + 参数校验）-> Service（业务逻辑）-> DAL（数据访问）
**统一响应**: `CommonResult<T>` 封装所有 API 返回
**逻辑删除**: 实体 `deleted` 字段配 `@TableLogic`
**自动填充**: `createTime/updateTime/creator/updater` 通过 MyBatis-Plus 自动填充
**权限控制**: `@PreAuthorize("@ss.hasPermission('...')")`
**API 文档**: 类/方法上使用 `@Tag/@Operation/@Parameter`

## 配置

默认激活 `local` profile，配置文件位于 `yudao-server/src/main/resources/`:
- `application.yaml` -- 主配置
- `application-local.yaml` -- 本地开发（MySQL: localhost:3306/ruoyi-vue-pro, Redis: localhost:6379, 端口: 48080）
- `application-dev.yaml` -- 开发/测试环境

多数据库支持：MySQL/PostgreSQL/Oracle/SQL Server/DM/KingbaseES/OpenGauss/DB2，ID 生成策略由 `IdTypeEnvironmentPostProcessor` 自动适配。

## 代码生成

infra 模块内置代码生成器（基于 Velocity 模板），生成前后端完整 CRUD 代码：
- 后端：Controller, VO, DO, Mapper, Service, ErrorCode, 单元测试
- 前端：支持 Vue 2/3, Vben, UniApp 等多种模板
- SQL：迁移脚本

运行方式：在管理后台「基础设施 → 代码生成」菜单中操作，或配置 `yudao.codegen.*` 后执行生成逻辑。

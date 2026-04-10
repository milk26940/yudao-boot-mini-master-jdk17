-- campus second trade admin menu bootstrap
-- safe to re-run

SET NAMES utf8mb4;

START TRANSACTION;

DELETE FROM system_role_menu
WHERE menu_id IN (
  6100,
  6101, 6102, 6103, 6104, 6105, 6106,
  6111, 6112, 6113, 6114,
  6121, 6122, 6123, 6124,
  6131, 6132, 6133, 6134, 6135,
  6141, 6142, 6143, 6144,
  6151, 6152, 6153, 6154,
  6161, 6162, 6163, 6164
);

DELETE FROM system_menu
WHERE id IN (
  6100,
  6101, 6102, 6103, 6104, 6105, 6106,
  6111, 6112, 6113, 6114,
  6121, 6122, 6123, 6124,
  6131, 6132, 6133, 6134, 6135,
  6141, 6142, 6143, 6144,
  6151, 6152, 6153, 6154,
  6161, 6162, 6163, 6164
);

INSERT INTO system_menu
  (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, updater, deleted)
VALUES
  (6100, CONVERT(0xE6A0A1E59BADE4BA8CE6898BE4BAA4E69893 USING utf8mb4), '', 1, 90, 0, '/campus', 'ep:shopping-bag', NULL, NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0');

INSERT INTO system_menu
  (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, updater, deleted)
VALUES
  (6101, CONVERT(0xE794A8E688B7E5AEA1E6A0B8E4B88EE7AEA1E79086 USING utf8mb4), 'system:campus-user:query', 2, 1, 6100, 'user', 'ep:user', 'campus/user/index', 'CampusUser', 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6102, CONVERT(0xE59586E59381E58886E7B1BBE7AEA1E79086 USING utf8mb4), 'system:second-category:query', 2, 2, 6100, 'category', 'ep:collection', 'campus/category/index', 'CampusCategory', 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6103, CONVERT(0xE4BA8CE6898BE59586E59381E7AEA1E79086 USING utf8mb4), 'system:second-goods:query', 2, 3, 6100, 'goods', 'ep:goods', 'campus/goods/index', 'CampusGoods', 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6104, CONVERT(0xE6B182E8B4ADE4BFA1E681AFE7AEA1E79086 USING utf8mb4), 'system:second-wanted:query', 2, 4, 6100, 'wanted', 'ep:shopping-cart-full', 'campus/wanted/index', 'CampusWanted', 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6105, CONVERT(0xE8AF84E8AEBAE7AEA1E79086 USING utf8mb4), 'system:second-comment:query', 2, 5, 6100, 'comment', 'ep:chat-line-square', 'campus/comment/index', 'CampusComment', 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6106, CONVERT(0xE8AEA2E58D95E7AEA1E79086 USING utf8mb4), 'system:second-order:query', 2, 6, 6100, 'order', 'ep:tickets', 'campus/order/index', 'CampusOrder', 0, b'1', b'1', b'1', 'admin', 'admin', b'0');

INSERT INTO system_menu
  (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, updater, deleted)
VALUES
  (6111, CONVERT(0xE794A8E688B7E69FA5E8AFA2 USING utf8mb4), 'system:campus-user:query', 3, 1, 6101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6112, CONVERT(0xE794A8E688B7E696B0E5A29E USING utf8mb4), 'system:campus-user:create', 3, 2, 6101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6113, CONVERT(0xE794A8E688B7E4BFAEE694B9 USING utf8mb4), 'system:campus-user:update', 3, 3, 6101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6114, CONVERT(0xE794A8E688B7E5AEA1E6A0B8 USING utf8mb4), 'system:campus-user:audit', 3, 4, 6101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0');

INSERT INTO system_menu
  (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, updater, deleted)
VALUES
  (6121, CONVERT(0xE58886E7B1BBE69FA5E8AFA2 USING utf8mb4), 'system:second-category:query', 3, 1, 6102, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6122, CONVERT(0xE58886E7B1BBE696B0E5A29E USING utf8mb4), 'system:second-category:create', 3, 2, 6102, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6123, CONVERT(0xE58886E7B1BBE4BFAEE694B9 USING utf8mb4), 'system:second-category:update', 3, 3, 6102, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6124, CONVERT(0xE58886E7B1BBE588A0E999A4 USING utf8mb4), 'system:second-category:delete', 3, 4, 6102, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0');

INSERT INTO system_menu
  (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, updater, deleted)
VALUES
  (6131, CONVERT(0xE59586E59381E69FA5E8AFA2 USING utf8mb4), 'system:second-goods:query', 3, 1, 6103, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6132, CONVERT(0xE59586E59381E696B0E5A29E USING utf8mb4), 'system:second-goods:create', 3, 2, 6103, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6133, CONVERT(0xE59586E59381E4BFAEE694B9 USING utf8mb4), 'system:second-goods:update', 3, 3, 6103, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6134, CONVERT(0xE59586E59381E588A0E999A4 USING utf8mb4), 'system:second-goods:delete', 3, 4, 6103, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6135, CONVERT(0xE59586E59381E59BBEE78987E69FA5E8AFA2 USING utf8mb4), 'system:second-goods-image:query', 3, 5, 6103, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0');

INSERT INTO system_menu
  (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, updater, deleted)
VALUES
  (6141, CONVERT(0xE6B182E8B4ADE69FA5E8AFA2 USING utf8mb4), 'system:second-wanted:query', 3, 1, 6104, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6142, CONVERT(0xE6B182E8B4ADE696B0E5A29E USING utf8mb4), 'system:second-wanted:create', 3, 2, 6104, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6143, CONVERT(0xE6B182E8B4ADE4BFAEE694B9 USING utf8mb4), 'system:second-wanted:update', 3, 3, 6104, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6144, CONVERT(0xE6B182E8B4ADE588A0E999A4 USING utf8mb4), 'system:second-wanted:delete', 3, 4, 6104, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0');

INSERT INTO system_menu
  (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, updater, deleted)
VALUES
  (6151, CONVERT(0xE8AF84E8AEBAE69FA5E8AFA2 USING utf8mb4), 'system:second-comment:query', 3, 1, 6105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6152, CONVERT(0xE8AF84E8AEBAE696B0E5A29E USING utf8mb4), 'system:second-comment:create', 3, 2, 6105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6153, CONVERT(0xE8AF84E8AEBAE4BFAEE694B9 USING utf8mb4), 'system:second-comment:update', 3, 3, 6105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6154, CONVERT(0xE8AF84E8AEBAE588A0E999A4 USING utf8mb4), 'system:second-comment:delete', 3, 4, 6105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0');

INSERT INTO system_menu
  (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, updater, deleted)
VALUES
  (6161, CONVERT(0xE8AEA2E58D95E69FA5E8AFA2 USING utf8mb4), 'system:second-order:query', 3, 1, 6106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6162, CONVERT(0xE8AEA2E58D95E696B0E5A29E USING utf8mb4), 'system:second-order:create', 3, 2, 6106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6163, CONVERT(0xE8AEA2E58D95E4BFAEE694B9 USING utf8mb4), 'system:second-order:update', 3, 3, 6106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0'),
  (6164, CONVERT(0xE8AEA2E58D95E588A0E999A4 USING utf8mb4), 'system:second-order:delete', 3, 4, 6106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', 'admin', b'0');

INSERT INTO system_role_menu
  (role_id, menu_id, creator, updater, deleted, tenant_id)
VALUES
  (1, 6100, 'admin', 'admin', b'0', 0),
  (1, 6101, 'admin', 'admin', b'0', 0),
  (1, 6102, 'admin', 'admin', b'0', 0),
  (1, 6103, 'admin', 'admin', b'0', 0),
  (1, 6104, 'admin', 'admin', b'0', 0),
  (1, 6105, 'admin', 'admin', b'0', 0),
  (1, 6106, 'admin', 'admin', b'0', 0),
  (1, 6111, 'admin', 'admin', b'0', 0),
  (1, 6112, 'admin', 'admin', b'0', 0),
  (1, 6113, 'admin', 'admin', b'0', 0),
  (1, 6114, 'admin', 'admin', b'0', 0),
  (1, 6121, 'admin', 'admin', b'0', 0),
  (1, 6122, 'admin', 'admin', b'0', 0),
  (1, 6123, 'admin', 'admin', b'0', 0),
  (1, 6124, 'admin', 'admin', b'0', 0),
  (1, 6131, 'admin', 'admin', b'0', 0),
  (1, 6132, 'admin', 'admin', b'0', 0),
  (1, 6133, 'admin', 'admin', b'0', 0),
  (1, 6134, 'admin', 'admin', b'0', 0),
  (1, 6135, 'admin', 'admin', b'0', 0),
  (1, 6141, 'admin', 'admin', b'0', 0),
  (1, 6142, 'admin', 'admin', b'0', 0),
  (1, 6143, 'admin', 'admin', b'0', 0),
  (1, 6144, 'admin', 'admin', b'0', 0),
  (1, 6151, 'admin', 'admin', b'0', 0),
  (1, 6152, 'admin', 'admin', b'0', 0),
  (1, 6153, 'admin', 'admin', b'0', 0),
  (1, 6154, 'admin', 'admin', b'0', 0),
  (1, 6161, 'admin', 'admin', b'0', 0),
  (1, 6162, 'admin', 'admin', b'0', 0),
  (1, 6163, 'admin', 'admin', b'0', 0),
  (1, 6164, 'admin', 'admin', b'0', 0);

COMMIT;

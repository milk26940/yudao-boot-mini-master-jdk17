/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1 MySQL
 Source Server Type    : MySQL
 Target Server Type    : MySQL
 File Encoding         : 65001

 Description           : 校园二手物品交易管理系统业务表
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for campus_user
-- ----------------------------
DROP TABLE IF EXISTS `campus_user`;
CREATE TABLE `campus_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `student_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `gender` tinyint NULL DEFAULT 0 COMMENT '性别',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '手机号',
  `qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'QQ号',
  `wechat` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '微信号',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '头像',
  `student_card_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '学生证图片地址',
  `audit_status` tinyint NOT NULL DEFAULT 0 COMMENT '审核状态',
  `audit_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '审核备注',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '账号状态',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_no`(`student_no` ASC) USING BTREE,
  INDEX `idx_email`(`email` ASC) USING BTREE,
  INDEX `idx_audit_status`(`audit_status` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '校园用户表';

-- ----------------------------
-- Table structure for second_category
-- ----------------------------
DROP TABLE IF EXISTS `second_category`;
CREATE TABLE `second_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '二手商品分类表';

-- ----------------------------
-- Table structure for second_goods
-- ----------------------------
DROP TABLE IF EXISTS `second_goods`;
CREATE TABLE `second_goods`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品编号',
  `seller_id` bigint NOT NULL COMMENT '卖家用户编号',
  `category_id` bigint NOT NULL COMMENT '分类编号',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '商品标题',
  `brand` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '品牌',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品描述',
  `price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '售价',
  `original_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '原价',
  `condition_level` tinyint NOT NULL DEFAULT 1 COMMENT '新旧程度',
  `contact_mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '联系电话',
  `contact_qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '联系QQ',
  `contact_wechat` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '联系微信',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '商品状态',
  `view_count` int NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `publish_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_seller_id`(`seller_id` ASC) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_publish_time`(`publish_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '二手商品表';

-- ----------------------------
-- Table structure for second_goods_image
-- ----------------------------
DROP TABLE IF EXISTS `second_goods_image`;
CREATE TABLE `second_goods_image`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片编号',
  `goods_id` bigint NOT NULL COMMENT '商品编号',
  `image_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '图片地址',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_goods_id`(`goods_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '二手商品图片表';

-- ----------------------------
-- Table structure for second_wanted
-- ----------------------------
DROP TABLE IF EXISTS `second_wanted`;
CREATE TABLE `second_wanted`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '求购编号',
  `user_id` bigint NOT NULL COMMENT '发布用户编号',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '求购标题',
  `brand` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '品牌',
  `model` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '型号',
  `expected_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '期望价格',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '需求描述',
  `contact_mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '联系电话',
  `contact_qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '联系QQ',
  `contact_wechat` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '联系微信',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '求购信息表';

-- ----------------------------
-- Table structure for second_comment
-- ----------------------------
DROP TABLE IF EXISTS `second_comment`;
CREATE TABLE `second_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论编号',
  `goods_id` bigint NOT NULL COMMENT '商品编号',
  `user_id` bigint NOT NULL COMMENT '评论用户编号',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父评论编号',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '评论内容',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '评论状态',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_goods_id`(`goods_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品评论表';

-- ----------------------------
-- Table structure for second_order
-- ----------------------------
DROP TABLE IF EXISTS `second_order`;
CREATE TABLE `second_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `goods_id` bigint NOT NULL COMMENT '商品编号',
  `seller_id` bigint NOT NULL COMMENT '卖家编号',
  `buyer_id` bigint NOT NULL COMMENT '买家编号',
  `order_price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '成交价格',
  `delivery_type` tinyint NOT NULL DEFAULT 1 COMMENT '交易方式',
  `delivery_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '交易地点或宿舍地址',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '订单状态',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_goods_id`(`goods_id` ASC) USING BTREE,
  INDEX `idx_seller_id`(`seller_id` ASC) USING BTREE,
  INDEX `idx_buyer_id`(`buyer_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '二手交易订单表';

-- ----------------------------
-- Table structure for second_order_log
-- ----------------------------
DROP TABLE IF EXISTS `second_order_log`;
CREATE TABLE `second_order_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单日志编号',
  `order_id` bigint NOT NULL COMMENT '订单编号',
  `operate_user_id` bigint NOT NULL COMMENT '操作用户编号',
  `operate_role` tinyint NOT NULL DEFAULT 1 COMMENT '操作角色',
  `before_status` tinyint NULL DEFAULT NULL COMMENT '变更前状态',
  `after_status` tinyint NOT NULL COMMENT '变更后状态',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单状态日志表';

-- ----------------------------
-- Table structure for second_favorite
-- ----------------------------
DROP TABLE IF EXISTS `second_favorite`;
CREATE TABLE `second_favorite`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `goods_id` bigint NOT NULL COMMENT '商品编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_goods`(`user_id` ASC, `goods_id` ASC) USING BTREE,
  INDEX `idx_goods_id`(`goods_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品收藏表';

-- ----------------------------
-- Records of second_category
-- ----------------------------
BEGIN;
INSERT INTO `second_category` (`id`, `name`, `sort`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, '教材书籍', 1, 1, 'admin', NOW(), 'admin', NOW(), b'0', 1),
(2, '数码电子', 2, 1, 'admin', NOW(), 'admin', NOW(), b'0', 1),
(3, '宿舍生活', 3, 1, 'admin', NOW(), 'admin', NOW(), b'0', 1),
(4, '服饰鞋包', 4, 1, 'admin', NOW(), 'admin', NOW(), b'0', 1),
(5, '体育器材', 5, 1, 'admin', NOW(), 'admin', NOW(), b'0', 1),
(6, '交通工具', 6, 1, 'admin', NOW(), 'admin', NOW(), b'0', 1),
(7, '乐器设备', 7, 1, 'admin', NOW(), 'admin', NOW(), b'0', 1),
(8, '其他闲置', 8, 1, 'admin', NOW(), 'admin', NOW(), b'0', 1);
COMMIT;

-- ----------------------------
-- Records of campus_user
-- password demo: 123456
-- ----------------------------
BEGIN;
INSERT INTO `campus_user` (`id`, `student_no`, `password`, `name`, `gender`, `email`, `mobile`, `qq`, `wechat`, `avatar`, `student_card_url`, `audit_status`, `audit_remark`, `status`, `login_ip`, `login_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, '20230001', '$2a$04$KljJDa/LK7QfDm0lF5OhuePhlPfjRH3tB2Wu351Uidz.oQGJXevPi', '张三', 1, 'zhangsan@campus.edu.cn', '13800000001', '10001', 'zhangsan_wechat', '', 'http://127.0.0.1:48080/admin-api/infra/file/1/get/student_card_zhangsan.jpg', 1, '审核通过', 1, '127.0.0.1', NOW(), 'admin', NOW(), 'admin', NOW(), b'0', 1),
(2, '20230002', '$2a$04$KljJDa/LK7QfDm0lF5OhuePhlPfjRH3tB2Wu351Uidz.oQGJXevPi', '李四', 2, 'lisi@campus.edu.cn', '13800000002', '10002', 'lisi_wechat', '', 'http://127.0.0.1:48080/admin-api/infra/file/1/get/student_card_lisi.jpg', 1, '审核通过', 1, '127.0.0.1', NOW(), 'admin', NOW(), 'admin', NOW(), b'0', 1),
(3, '20230003', '$2a$04$KljJDa/LK7QfDm0lF5OhuePhlPfjRH3tB2Wu351Uidz.oQGJXevPi', '王五', 1, 'wangwu@campus.edu.cn', '13800000003', '10003', 'wangwu_wechat', '', 'http://127.0.0.1:48080/admin-api/infra/file/1/get/student_card_wangwu.jpg', 0, '待审核', 1, '', NULL, 'admin', NOW(), 'admin', NOW(), b'0', 1),
(4, '20230004', '$2a$04$KljJDa/LK7QfDm0lF5OhuePhlPfjRH3tB2Wu351Uidz.oQGJXevPi', '赵六', 2, 'zhaoliu@campus.edu.cn', '13800000004', '10004', 'zhaoliu_wechat', '', 'http://127.0.0.1:48080/admin-api/infra/file/1/get/student_card_zhaoliu.jpg', 2, '学生证信息不清晰', 1, '', NULL, 'admin', NOW(), 'admin', NOW(), b'0', 1);
COMMIT;

-- ----------------------------
-- Records of second_goods
-- ----------------------------
BEGIN;
INSERT INTO `second_goods` (`id`, `seller_id`, `category_id`, `title`, `brand`, `description`, `price`, `original_price`, `condition_level`, `contact_mobile`, `contact_qq`, `contact_wechat`, `status`, `view_count`, `publish_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 1, 1, '高等数学教材同济版', '同济大学出版社', '教材保存较好，只有少量笔记，适合大一新生使用。', 25.00, 58.00, 2, '13800000001', '10001', 'zhangsan_wechat', 1, 36, NOW(), '1', NOW(), '1', NOW(), b'0', 1),
(2, 1, 2, '二手小米平板 5', '小米', '功能正常，屏幕无裂痕，带保护壳，适合上课记笔记。', 780.00, 1999.00, 2, '13800000001', '10001', 'zhangsan_wechat', 1, 82, NOW(), '1', NOW(), '1', NOW(), b'0', 1),
(3, 2, 3, '宿舍小风扇', '美的', '九成新，风力正常，支持 USB 供电。', 18.00, 39.00, 1, '13800000002', '10002', 'lisi_wechat', 1, 25, NOW(), '2', NOW(), '2', NOW(), b'0', 1),
(4, 2, 6, '校园二手自行车', '永久', '可正常骑行，适合校内代步，需线下自提。', 120.00, 320.00, 3, '13800000002', '10002', 'lisi_wechat', 3, 91, NOW(), '2', NOW(), '2', NOW(), b'0', 1);
COMMIT;

-- ----------------------------
-- Records of second_goods_image
-- ----------------------------
BEGIN;
INSERT INTO `second_goods_image` (`id`, `goods_id`, `image_url`, `sort`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 1, 'http://127.0.0.1:48080/admin-api/infra/file/1/get/goods_math_book_1.jpg', 1, '1', NOW(), '1', NOW(), b'0', 1),
(2, 1, 'http://127.0.0.1:48080/admin-api/infra/file/1/get/goods_math_book_2.jpg', 2, '1', NOW(), '1', NOW(), b'0', 1),
(3, 2, 'http://127.0.0.1:48080/admin-api/infra/file/1/get/goods_pad_1.jpg', 1, '1', NOW(), '1', NOW(), b'0', 1),
(4, 2, 'http://127.0.0.1:48080/admin-api/infra/file/1/get/goods_pad_2.jpg', 2, '1', NOW(), '1', NOW(), b'0', 1),
(5, 3, 'http://127.0.0.1:48080/admin-api/infra/file/1/get/goods_fan_1.jpg', 1, '2', NOW(), '2', NOW(), b'0', 1),
(6, 4, 'http://127.0.0.1:48080/admin-api/infra/file/1/get/goods_bike_1.jpg', 1, '2', NOW(), '2', NOW(), b'0', 1);
COMMIT;

-- ----------------------------
-- Records of second_wanted
-- ----------------------------
BEGIN;
INSERT INTO `second_wanted` (`id`, `user_id`, `title`, `brand`, `model`, `expected_price`, `description`, `contact_mobile`, `contact_qq`, `contact_wechat`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 1, '求购英语四级词汇书', '新东方', '', 20.00, '希望书籍较新，可以有少量笔记，价格合适即可。', '13800000001', '10001', 'zhangsan_wechat', 1, '1', NOW(), '1', NOW(), b'0', 1),
(2, 2, '求购二手台灯', '不限', '', 35.00, '宿舍学习使用，要求正常照明，无明显损坏。', '13800000002', '10002', 'lisi_wechat', 1, '2', NOW(), '2', NOW(), b'0', 1),
(3, 1, '求购羽毛球拍', '李宁', '', 80.00, '接受轻微使用痕迹，适合日常锻炼。', '13800000001', '10001', 'zhangsan_wechat', 2, '1', NOW(), '1', NOW(), b'0', 1);
COMMIT;

-- ----------------------------
-- Records of second_comment
-- ----------------------------
BEGIN;
INSERT INTO `second_comment` (`id`, `goods_id`, `user_id`, `parent_id`, `content`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 2, 2, NULL, '请问平板电池健康怎么样？续航还可以吗？', 1, '2', NOW(), '2', NOW(), b'0', 1),
(2, 2, 1, 1, '续航正常，上课记笔记和看网课没问题，一天一充。', 1, '1', NOW(), '1', NOW(), b'0', 1),
(3, 1, 2, NULL, '教材有很多笔记吗？会影响使用吗？', 1, '2', NOW(), '2', NOW(), b'0', 1),
(4, 1, 1, 3, '只有少量重点标注，不影响正常学习。', 1, '1', NOW(), '1', NOW(), b'0', 1);
COMMIT;

-- ----------------------------
-- Records of second_order
-- ----------------------------
BEGIN;
INSERT INTO `second_order` (`id`, `order_no`, `goods_id`, `seller_id`, `buyer_id`, `order_price`, `delivery_type`, `delivery_address`, `remark`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 'ST202604100001', 4, 2, 1, 120.00, 1, '图书馆门口', '今晚六点以后可以交易', 2, '1', NOW(), '2', NOW(), b'0', 1),
(2, 'ST202604100002', 3, 2, 1, 18.00, 2, '3号宿舍楼 302', '送到宿舍门口即可', 1, '1', NOW(), '2', NOW(), b'0', 1);
COMMIT;

-- ----------------------------
-- Records of second_order_log
-- ----------------------------
BEGIN;
INSERT INTO `second_order_log` (`id`, `order_id`, `operate_user_id`, `operate_role`, `before_status`, `after_status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 1, 1, 1, NULL, 0, '买家提交订单', '1', NOW(), '1', NOW(), b'0', 1),
(2, 1, 2, 2, 0, 1, '卖家确认订单', '2', NOW(), '2', NOW(), b'0', 1),
(3, 1, 1, 1, 1, 2, '买家确认收货，订单完成', '1', NOW(), '1', NOW(), b'0', 1),
(4, 2, 1, 1, NULL, 0, '买家提交订单', '1', NOW(), '1', NOW(), b'0', 1),
(5, 2, 2, 2, 0, 1, '卖家已确认，等待线下交易', '2', NOW(), '2', NOW(), b'0', 1);
COMMIT;

-- ----------------------------
-- Records of second_favorite
-- ----------------------------
BEGIN;
INSERT INTO `second_favorite` (`id`, `user_id`, `goods_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 1, 3, '1', NOW(), '1', NOW(), b'0', 1),
(2, 2, 2, '2', NOW(), '2', NOW(), b'0', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

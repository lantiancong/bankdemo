/*
 Navicat Premium Dump SQL

 Source Server         : xiaopi
 Source Server Type    : MySQL
 Source Server Version : 80012 (8.0.12)
 Source Host           : localhost:3306
 Source Schema         : studentdb202341054113

 Target Server Type    : MySQL
 Target Server Version : 80012 (8.0.12)
 File Encoding         : 65001

 Date: 04/11/2025 02:11:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bank_card
-- ----------------------------
DROP TABLE IF EXISTS `bank_card`;
CREATE TABLE `bank_card`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `bank_info` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `balance` decimal(15, 2) NULL DEFAULT 0.00,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_card_number`(`card_number` ASC) USING BTREE,
  INDEX `fk_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bank_card
-- ----------------------------
INSERT INTO `bank_card` VALUES (1, '6222021234567890001', '中国工商银行', 1, '2025-11-04 01:50:03', 3000.00);
INSERT INTO `bank_card` VALUES (2, '6222021234567890002', '中国建设银行', 2, '2025-11-04 01:50:03', 5000.00);
INSERT INTO `bank_card` VALUES (3, '6222021234567890003', '中国农业银行', 3, '2025-11-04 01:50:03', 2000.00);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '202341054113', '兰天从', '软件2394', '男');

-- ----------------------------
-- Table structure for transaction_flow
-- ----------------------------
DROP TABLE IF EXISTS `transaction_flow`;
CREATE TABLE `transaction_flow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_card_id` int(11) NULL DEFAULT NULL,
  `to_card_id` int(11) NULL DEFAULT NULL,
  `flow_type` int(11) NOT NULL COMMENT '0-收入, 1-支出',
  `amount` decimal(15, 2) NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_from_card`(`from_card_id` ASC) USING BTREE,
  INDEX `idx_to_card`(`to_card_id` ASC) USING BTREE,
  CONSTRAINT `fk_from_card` FOREIGN KEY (`from_card_id`) REFERENCES `bank_card` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_to_card` FOREIGN KEY (`to_card_id`) REFERENCES `bank_card` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of transaction_flow
-- ----------------------------
INSERT INTO `transaction_flow` VALUES (1, 1, 2, 1, 1000.00, '2025-11-04 01:50:53');
INSERT INTO `transaction_flow` VALUES (2, 1, 2, 0, 1000.00, '2025-11-04 01:50:53');
INSERT INTO `transaction_flow` VALUES (3, 1, 2, 1, 1000.00, '2025-11-04 01:51:46');
INSERT INTO `transaction_flow` VALUES (4, 1, 2, 0, 1000.00, '2025-11-04 01:51:46');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `uk_id_card`(`id_card` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'lantiancong', '110101202341054113', '13800138000', '兰天从');
INSERT INTO `user` VALUES (2, 'zhangsan', '110101202341054114', '13800138001', '张三');
INSERT INTO `user` VALUES (3, 'lisi', '110101202341054115', '13800138002', '李四');

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : video_info

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 20/02/2020 09:52:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for video_info
-- ----------------------------
DROP TABLE IF EXISTS `video_info`;
CREATE TABLE `video_info`  (
  `id` int(20) NOT NULL COMMENT 'id',
  `state` int(255) NULL DEFAULT NULL COMMENT '1-播放，2-暂停',
  `video_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频资源',
  `play_duration` int(255) NULL DEFAULT NULL COMMENT '播放时长',
  `switch_effect` int(2) NULL DEFAULT NULL COMMENT '1-横向飞入，2-纵向飞入',
  `is_loop` int(2) NULL DEFAULT NULL COMMENT '1-循环，2-不循环',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_info
-- ----------------------------
INSERT INTO `video_info` VALUES (1, 2, 'https://storage.googleapis.com/exoplayer-test-media-1/mkv/android-screens-lavf-56.36.100-aac-avc-main-1280x720.mkv', 2, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;

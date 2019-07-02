/*
Navicat MySQL Data Transfer

Source Server         : 47.93.184.40
Source Server Version : 50621
Source Host           : 47.93.184.40:3306
Source Database       : x-energy-db

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2019-04-28 22:19:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for data_basic
-- ----------------------------
DROP TABLE IF EXISTS `data_basic`;
CREATE TABLE `data_basic` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_ID` int(11) NOT NULL DEFAULT '0',
  `STAND_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '机组ID',
  `STAND_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '机组名称',
  `STATION_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '换热站ID',
  `STATION_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '换站站名称',
  `GROUP_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分所ID',
  `GROUP_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分所名称',
  `OP_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `PT1` float(8,2) DEFAULT '0.00' COMMENT '一网供压',
  `PT2` float(8,2) DEFAULT '0.00' COMMENT '一网回压',
  `TE1` float(8,1) DEFAULT '0.0' COMMENT '一网供温',
  `TE2` float(8,2) DEFAULT '0.00' COMMENT '一网回温',
  `CVI1` float(8,0) DEFAULT '0' COMMENT '调节阀开度',
  `QI` float(8,1) DEFAULT '0.0' COMMENT '一网瞬时热量',
  `FT1` float(8,1) DEFAULT '0.0' COMMENT '一网瞬时流量',
  `FT1Q` float(8,0) DEFAULT '0' COMMENT '一网累积流量',
  `PT3` float(8,2) DEFAULT '0.00' COMMENT '二网供压',
  `PT4` float(8,2) DEFAULT '0.00' COMMENT '二网回压',
  `TE3` float(8,1) DEFAULT '0.0' COMMENT '二网供水温度',
  `TE4` float(8,1) DEFAULT '0.0' COMMENT '二网回水温度',
  `FT2` float(8,1) DEFAULT '0.0' COMMENT '二供流量',
  `FT3` float(8,1) DEFAULT '0.0' COMMENT '补水流量',
  `FC1V1` float(8,0) DEFAULT '0' COMMENT '循环泵频率',
  `LT1` float(8,0) DEFAULT '0' COMMENT '水箱液位',
  `QQI` float(8,0) DEFAULT '0' COMMENT '累积热量',
  `JQI` float(8,0) DEFAULT '0' COMMENT '累积电量',
  `FT3Q` float(8,0) DEFAULT '0' COMMENT '累积补水量',
  `FT2Q` float(8,0) DEFAULT '0' COMMENT '二网供水累积流量',
  `processed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `OP_TIME_INDEX` (`OP_TIME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=417114 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_collect
-- ----------------------------
DROP TABLE IF EXISTS `data_collect`;
CREATE TABLE `data_collect` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_ID` int(11) NOT NULL,
  `STAND_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '机组ID',
  `STATION_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '换热站ID',
  `GROUP_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分所ID,这里放一个分所ID，是因为换热站有可能会变到不同的分所里，方便分所统计原始数据',
  `OP_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `PT1` float(8,2) DEFAULT '0.00' COMMENT '一网供压',
  `PT2` float(8,2) DEFAULT '0.00' COMMENT '一网回压',
  `TE1` float(8,1) DEFAULT '0.0' COMMENT '一网供温',
  `TE2` float(8,2) DEFAULT '0.00' COMMENT '一网回温',
  `CVI1` float(8,0) DEFAULT '0' COMMENT '调节阀开度',
  `QI` float(8,1) DEFAULT '0.0' COMMENT '一网瞬时热量',
  `FT1` float(8,1) DEFAULT '0.0' COMMENT '一网瞬时流量',
  `FT1Q` float(8,0) DEFAULT '0' COMMENT '一网累积流量',
  `PT3` float(8,2) DEFAULT '0.00' COMMENT '二网供压',
  `PT4` float(8,2) DEFAULT '0.00' COMMENT '二网回压',
  `TE3` float(8,1) DEFAULT '0.0' COMMENT '二网供水温度',
  `TE4` float(8,1) DEFAULT '0.0' COMMENT '二网回水温度',
  `FT2` float(8,1) DEFAULT '0.0' COMMENT '二供流量',
  `FT3` float(8,1) DEFAULT '0.0' COMMENT '补水流量',
  `FC1V1` float(8,0) DEFAULT '0' COMMENT '循环泵频率',
  `LT1` float(8,0) DEFAULT '0' COMMENT '水箱液位',
  `QQI` float(8,0) DEFAULT '0' COMMENT '累积热量',
  `JQI` float(8,0) DEFAULT '0' COMMENT '累积电量',
  `FT3Q` float(8,0) DEFAULT '0' COMMENT '累积补水量',
  `FT2Q` float(8,0) DEFAULT '0' COMMENT '二网供水累积流量',
  `FT3Q_PRICE` float(8,2) DEFAULT '0.00' COMMENT '水耗单价',
  `JQI_PRICE` float(8,3) DEFAULT '0.000' COMMENT '电耗单价',
  `QQI_PRICE` float(8,2) DEFAULT '0.00' COMMENT '热耗单价',
  `BATCH_NUMBER` int(11) DEFAULT '0' COMMENT '批次号，一批插入这个号相同。最大的为最新',
  PRIMARY KEY (`ID`),
  KEY `OP_TIME_INDEX` (`STAND_ID`,`OP_TIME`) USING BTREE,
  KEY `BATCH_NUMBER_INDEX` (`BATCH_NUMBER`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=410595 DEFAULT CHARSET=utf8 COMMENT='基础数据表';

-- ----------------------------
-- Table structure for data_collect_day
-- ----------------------------
DROP TABLE IF EXISTS `data_collect_day`;
CREATE TABLE `data_collect_day` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_ID` int(11) NOT NULL,
  `STAND_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '机组ID',
  `STATION_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '换热站ID',
  `GROUP_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分所ID,这里放一个分所ID，是因为换热站有可能会变到不同的分所里，方便分所统计原始数据',
  `OP_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '系统时间',
  `PT1` float(8,2) DEFAULT '0.00' COMMENT '一网供压',
  `PT2` float(8,2) DEFAULT '0.00' COMMENT '一网回压',
  `TE1` float(8,1) DEFAULT '0.0' COMMENT '一网供温',
  `TE2` float(8,2) DEFAULT '0.00' COMMENT '一网回温',
  `CVI1` float(8,0) DEFAULT '0' COMMENT '调节阀开度',
  `QI` float(8,1) DEFAULT '0.0' COMMENT '一网瞬时热量',
  `FT1` float(8,1) DEFAULT '0.0' COMMENT '一网瞬时流量',
  `FT1Q` float(8,0) DEFAULT '0' COMMENT '一网累积流量',
  `PT3` float(8,2) DEFAULT '0.00' COMMENT '二网供压',
  `PT4` float(8,2) DEFAULT '0.00' COMMENT '二网回压',
  `TE3` float(8,1) DEFAULT '0.0' COMMENT '二网供水温度',
  `TE4` float(8,1) DEFAULT '0.0' COMMENT '二网回水温度',
  `FT2` float(8,1) DEFAULT '0.0' COMMENT '二供流量',
  `FT3` float(8,1) DEFAULT '0.0' COMMENT '补水流量',
  `FC1V1` float(8,0) DEFAULT '0' COMMENT '循环泵频率',
  `LT1` float(8,0) DEFAULT '0' COMMENT '水箱液位',
  `QQI` float(8,0) DEFAULT '0' COMMENT '累积热量',
  `JQI` float(8,0) DEFAULT '0' COMMENT '累积电量',
  `FT3Q` float(8,0) DEFAULT '0' COMMENT '累积补水量',
  `FT2Q` float(8,0) DEFAULT '0' COMMENT '二网供水累积流量',
  `FT3Q_PRICE` float(8,2) DEFAULT '0.00' COMMENT '水耗单价',
  `JQI_PRICE` float(8,2) DEFAULT '0.00' COMMENT '电耗单价',
  `QQI_PRICE` float(8,2) DEFAULT '0.00' COMMENT '热耗单价',
  `QQI_TOTAL` float(8,2) DEFAULT '0.00' COMMENT '当天最后一条记录的QQI减去第一天记录的QQI',
  `JQI_TOTAL` float(8,2) DEFAULT '0.00' COMMENT '当天最后一条记录的JQI减去第一天记录的JQI',
  `FT3Q_TOTAL` float(8,2) DEFAULT '0.00' COMMENT '当天最后一条记录的FT3Q减去第一天记录的FT3Q',
  `DT` date DEFAULT NULL COMMENT '报表分析查询使用',
  `type` int(11) DEFAULT NULL COMMENT '0：日、1：月、2、季度、3、年',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `type_dt_stand_key` (`type`,`DT`,`STAND_ID`,`COMPANY_ID`) USING BTREE,
  KEY `station_id_index` (`STATION_ID`) USING BTREE,
  KEY `stand_id_index` (`STAND_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1433 DEFAULT CHARSET=utf8 COMMENT='基础数据表历史表，每天汇总data_collect表的机组数量条数的最后记录';

-- ----------------------------
-- Table structure for data_group
-- ----------------------------
DROP TABLE IF EXISTS `data_group`;
CREATE TABLE `data_group` (
  `COMPANY_ID` int(11) NOT NULL,
  `GROUP_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '组ID',
  `GROUP_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '组名称',
  PRIMARY KEY (`COMPANY_ID`,`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_para
-- ----------------------------
DROP TABLE IF EXISTS `data_para`;
CREATE TABLE `data_para` (
  `city_name` varchar(20) NOT NULL COMMENT '城市名称',
  `Tw1` float(8,2) DEFAULT NULL COMMENT '供暖室外计算温度',
  `Tpj1` float(8,2) DEFAULT NULL COMMENT '供暖期平均温度',
  `N` int(8) DEFAULT NULL COMMENT '供暖天数',
  `F` float(8,2) DEFAULT NULL COMMENT '供热面积',
  `q` float(8,2) DEFAULT NULL COMMENT '设计热指标',
  `Tn` float(8,2) DEFAULT NULL COMMENT '室内计算温度',
  `Q1` float(8,2) DEFAULT NULL COMMENT '全年日平均耗热量',
  `L` float(8,3) DEFAULT '0.864',
  PRIMARY KEY (`city_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础参数';

-- ----------------------------
-- Table structure for data_para_cul
-- ----------------------------
DROP TABLE IF EXISTS `data_para_cul`;
CREATE TABLE `data_para_cul` (
  `OP_TIME` date NOT NULL,
  `k` float(8,3) DEFAULT NULL COMMENT '设计热指标',
  `k1` float(8,3) DEFAULT NULL COMMENT '修正系数k1',
  `k2` float(8,3) DEFAULT NULL COMMENT '计算热指标k2',
  `Q2` float(8,2) DEFAULT NULL COMMENT '昨天耗热量Q2',
  `Q3` float(8,2) DEFAULT NULL COMMENT '今天预测耗热量',
  `Q4` float(8,2) DEFAULT NULL COMMENT '明天预测耗热量',
  `Q5` float(8,2) DEFAULT NULL COMMENT '后天预测耗热量',
  `Q6` float(8,2) DEFAULT NULL COMMENT '大后天预测耗热量',
  `Tpj2` float(8,1) DEFAULT NULL COMMENT '当日的日平均温度',
  `Tpj3` float(8,1) DEFAULT NULL COMMENT '明天的日平均温度',
  `Tpj4` float(8,1) DEFAULT NULL COMMENT '后天的日平均温度',
  `Tpj5` float(8,1) DEFAULT NULL COMMENT '大后天的日平均温度',
  PRIMARY KEY (`OP_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预测数据';

-- ----------------------------
-- Table structure for data_predict_history
-- ----------------------------
DROP TABLE IF EXISTS `data_predict_history`;
CREATE TABLE `data_predict_history` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '本条数据的创建时间',
  `PREDICT_TIME` datetime DEFAULT NULL COMMENT '本条数据预测的目标时间',
  `PREDICT_TEM` float(8,2) DEFAULT NULL COMMENT '预测的的目标时间的温度',
  `PREDICT_HEAT` float(8,2) DEFAULT NULL COMMENT '预测的目标日期的耗热量',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_price
-- ----------------------------
DROP TABLE IF EXISTS `data_price`;
CREATE TABLE `data_price` (
  `COMPANY_ID` int(11) NOT NULL,
  `FT3Q_PRICE` float(8,2) DEFAULT '0.00' COMMENT '水耗单价',
  `JQI_PRICE` float(8,3) DEFAULT '0.000' COMMENT '电耗单价',
  `QQI_PRICE` float(8,2) DEFAULT '0.00' COMMENT '热耗单价',
  PRIMARY KEY (`COMPANY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_stand
-- ----------------------------
DROP TABLE IF EXISTS `data_stand`;
CREATE TABLE `data_stand` (
  `COMPANY_ID` int(11) NOT NULL,
  `STAND_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '机组ID',
  `STAND_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '机组名称',
  `STATION_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '换热站ID，对应data_station里的STATION_ID',
  `DESIGN_AREA` int(11) DEFAULT '0' COMMENT '设计面积',
  `REAL_AREA` int(11) DEFAULT '0' COMMENT '实际面积',
  PRIMARY KEY (`COMPANY_ID`,`STAND_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_stand_basic
-- ----------------------------
DROP TABLE IF EXISTS `data_stand_basic`;
CREATE TABLE `data_stand_basic` (
  `COMPANY_ID` int(11) NOT NULL,
  `STAND_ID` varchar(10) NOT NULL,
  `stand_name` varchar(20) NOT NULL,
  `station_id` varchar(255) DEFAULT NULL,
  `total_area` float(255,0) DEFAULT NULL COMMENT '供热面积',
  `stand_fh` varchar(255) DEFAULT NULL COMMENT '热换负荷',
  `stand_area` varchar(255) DEFAULT NULL COMMENT '热换面积',
  `stand_bf_num` varchar(255) DEFAULT NULL COMMENT '板换数量',
  `xhb_ll` varchar(255) DEFAULT NULL COMMENT '循环泵流量',
  `xhb_yc` varchar(255) DEFAULT NULL COMMENT '循环泵扬程',
  `xhb_gl` varchar(255) DEFAULT NULL COMMENT '循环泵功率',
  `xhb_num` varchar(255) DEFAULT NULL COMMENT '循环泵数量',
  `bsb_ll` varchar(255) DEFAULT NULL COMMENT '补水泵',
  `bsb_yc` varchar(255) DEFAULT NULL COMMENT '补水泵',
  `bsb_gl` varchar(255) DEFAULT NULL COMMENT '补水泵',
  `bsb_num` varchar(255) DEFAULT NULL COMMENT '补水泵',
  `hsjyb_ll` varchar(255) DEFAULT NULL COMMENT '回水加压泵',
  `hsjyb_yc` varchar(255) DEFAULT NULL COMMENT '回水加压泵',
  `hsjyb_gl` varchar(255) DEFAULT NULL COMMENT '回水加压泵',
  `hsjyb_num` varchar(255) DEFAULT NULL COMMENT '回水加压泵',
  PRIMARY KEY (`COMPANY_ID`,`STAND_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_station
-- ----------------------------
DROP TABLE IF EXISTS `data_station`;
CREATE TABLE `data_station` (
  `COMPANY_ID` int(11) NOT NULL,
  `STATION_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '换热站ID',
  `STATION_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '换热站名称',
  `GROUP_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分所ID',
  `PERSONNEL` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '负责人',
  `TELEPHONE` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '电话',
  `ADDRESS` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '换热站地址',
  `COORDINATE` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '坐标',
  PRIMARY KEY (`COMPANY_ID`,`STATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='换热站表';

-- ----------------------------
-- Table structure for data_tecol_curve
-- ----------------------------
DROP TABLE IF EXISTS `data_tecol_curve`;
CREATE TABLE `data_tecol_curve` (
  `temperature` smallint(6) NOT NULL,
  `TE1` float(8,1) DEFAULT '0.0' COMMENT '一网供温',
  `TE2` float(8,2) DEFAULT '0.00' COMMENT '一网回温',
  `TE3` float(8,1) DEFAULT '0.0' COMMENT '二网供水温度',
  `TE4` float(8,1) DEFAULT '0.0' COMMENT '二网回水温度',
  `TAG` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`temperature`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_weather
-- ----------------------------
DROP TABLE IF EXISTS `data_weather`;
CREATE TABLE `data_weather` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CITY_NO` varchar(45) DEFAULT NULL COMMENT '城市ID',
  `DAY` varchar(8) DEFAULT NULL COMMENT '年月日',
  `TE_L` float(8,1) DEFAULT '0.0' COMMENT '夜晚最低温度',
  `TE_H` float(8,1) DEFAULT '0.0' COMMENT '白天最高温度',
  `TE_A` float(8,1) DEFAULT NULL COMMENT '平均温度  (TE_H + TE_L)/2',
  `TE_WD1` varchar(128) DEFAULT NULL COMMENT '白天风向',
  `TE_WD2` varchar(128) DEFAULT NULL COMMENT '夜晚风向',
  `TE_WE1` varchar(128) DEFAULT NULL COMMENT '白天风力',
  `TE_WE2` varchar(128) DEFAULT NULL COMMENT '夜晚风力',
  `TE_C1` varchar(128) DEFAULT NULL COMMENT '白天天气情况',
  `TE_C2` varchar(45) DEFAULT NULL COMMENT '夜晚天气情况',
  `TE_I1` varchar(45) DEFAULT NULL COMMENT '图片1',
  `TE_I2` varchar(45) DEFAULT NULL COMMENT '图片2',
  `HOUR3_DATA` varchar(3096) DEFAULT NULL COMMENT '3小时温度变化josn串',
  `CRT_DT` varchar(8) DEFAULT NULL COMMENT '添加日期',
  `CRT_TM` varchar(6) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=554 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_weather_stal
-- ----------------------------
DROP TABLE IF EXISTS `data_weather_stal`;
CREATE TABLE `data_weather_stal` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `STA_ID` varchar(12) DEFAULT NULL COMMENT '气象站号54433   齐齐哈尔',
  `DAY` varchar(8) DEFAULT NULL COMMENT '年月日',
  `Hour` int(11) DEFAULT NULL COMMENT '时次',
  `PRS` decimal(8,1) DEFAULT NULL COMMENT '气压',
  `PRS_Sea` decimal(8,1) DEFAULT NULL COMMENT '海平面气压',
  `PRS_Max` decimal(8,1) DEFAULT NULL COMMENT '最高气压',
  `PRS_Min` decimal(8,1) DEFAULT NULL COMMENT '最低气压',
  `TEM` decimal(8,1) DEFAULT NULL COMMENT '温度/气温',
  `TEM_Max` decimal(8,1) DEFAULT NULL COMMENT '最高气温',
  `TEM_Min` decimal(8,1) DEFAULT NULL COMMENT '最低气温',
  `RHU` decimal(8,1) DEFAULT NULL COMMENT '相对湿度',
  `RHU_Min` decimal(8,1) DEFAULT NULL COMMENT '最小相对湿度',
  `VAP` decimal(8,1) DEFAULT NULL COMMENT '水汽压',
  `PRE_1h` decimal(8,1) DEFAULT NULL COMMENT '降水量',
  `WIN_D_INST_Max` decimal(8,1) DEFAULT NULL COMMENT '极大风速的风向(角度) 字符\n',
  `WIN_S_Max` decimal(8,1) DEFAULT NULL COMMENT '最大风速 米/秒\n',
  `WIN_D_S_Max` decimal(8,1) DEFAULT NULL COMMENT '最大风速的风向(角度)  度\n\n',
  `WIN_S_Inst_Max` decimal(8,1) DEFAULT NULL COMMENT '极大风速 米/秒\n\n',
  `WIN_S_Avg_10mi` decimal(8,1) DEFAULT NULL COMMENT '10分钟平均风速\n 米/秒\n',
  `WIN_D_Avg_10mi` decimal(8,1) DEFAULT NULL COMMENT '10分钟平均风向(角度)	度\n',
  `CRT_DT` varchar(8) DEFAULT NULL,
  `CRT_TM` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9455 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for illegal_data_id
-- ----------------------------
DROP TABLE IF EXISTS `illegal_data_id`;
CREATE TABLE `illegal_data_id` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_company
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_company`;
CREATE TABLE `t_sys_company` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(64) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `ICON` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_department
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_department`;
CREATE TABLE `t_sys_department` (
  `DEP_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `P_DEP_ID` int(10) DEFAULT NULL COMMENT '上级部门标识',
  `DEP_NAME` varchar(50) DEFAULT NULL,
  `DEP_RMK` varchar(100) DEFAULT NULL,
  `CRT_ID` varchar(10) DEFAULT NULL,
  `CRT_DT` varchar(8) DEFAULT NULL,
  `CRT_TM` varchar(8) DEFAULT NULL,
  `UPT_ID` varchar(10) DEFAULT NULL,
  `UPT_DT` varchar(8) DEFAULT NULL,
  `UPT_TM` varchar(8) DEFAULT NULL,
  `DEP_STS` int(2) DEFAULT NULL,
  PRIMARY KEY (`DEP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1011 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `MENU_ID` int(4) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `MENU_CODE` varchar(50) NOT NULL COMMENT '菜单代码',
  `MENU_NAME` varchar(200) DEFAULT NULL COMMENT '菜单名称',
  `MENU_PID` varchar(4) DEFAULT NULL COMMENT '菜单上级id',
  `MENU_URL` varchar(200) DEFAULT NULL COMMENT '菜单url',
  `ICON` varchar(50) DEFAULT NULL COMMENT '菜单图片',
  `MENU_ORDER` int(11) DEFAULT NULL COMMENT '显示顺序',
  `RMK` varchar(200) DEFAULT NULL,
  `CRT_ID` varchar(10) DEFAULT NULL,
  `CRT_DT` varchar(8) DEFAULT NULL,
  `CRT_TM` varchar(6) DEFAULT NULL,
  `UPT_ID` varchar(10) DEFAULT NULL,
  `UPT_DT` varchar(8) DEFAULT NULL,
  `UPT_TM` varchar(6) DEFAULT NULL,
  `TM_SMP` varchar(26) DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5016 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- ----------------------------
-- Table structure for t_sys_menu_item
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu_item`;
CREATE TABLE `t_sys_menu_item` (
  `ITEM_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单项ID',
  `MENU_ID` int(11) DEFAULT NULL COMMENT '归属菜单ID',
  `ITEM_NAME` varchar(50) DEFAULT NULL COMMENT '菜单项名称',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `item_sts` varchar(2) DEFAULT '01' COMMENT '菜单项状态 00:失效\r\n            01:有效',
  `rmk` varchar(200) DEFAULT NULL,
  `CRT_ID` varchar(10) DEFAULT NULL,
  `CRT_DT` varchar(8) DEFAULT NULL,
  `CRT_TM` varchar(6) DEFAULT NULL,
  `UPT_ID` varchar(10) DEFAULT NULL,
  `UPT_DT` varchar(8) DEFAULT NULL,
  `UPT_TM` varchar(6) DEFAULT NULL,
  `TM_SMP` varchar(26) DEFAULT NULL,
  PRIMARY KEY (`ITEM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=501206 DEFAULT CHARSET=utf8 COMMENT='系统菜单按钮表';

-- ----------------------------
-- Table structure for t_sys_opr_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_opr_info`;
CREATE TABLE `t_sys_opr_info` (
  `OPR_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `OPR_NAME` varchar(100) NOT NULL,
  `ORG_ID` int(11) DEFAULT '1000',
  `LOGIN_ID` varchar(20) DEFAULT NULL,
  `LOGIN_PWD` varchar(128) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  `DEP_ID` int(11) DEFAULT NULL COMMENT '部门编号',
  `HEAD` varchar(100) DEFAULT NULL,
  `CRT_ID` varchar(10) DEFAULT NULL,
  `CRT_DT` varchar(8) DEFAULT NULL,
  `CRT_TM` varchar(6) DEFAULT NULL,
  `UPT_ID` varchar(10) DEFAULT NULL,
  `UPT_DT` varchar(8) DEFAULT NULL,
  `UPT_TM` varchar(6) DEFAULT NULL,
  `OPR_STS` varchar(2) DEFAULT '01' COMMENT '00:注销,01:正常',
  `TM_SMP` varchar(26) DEFAULT NULL,
  `PWD_ERR_NUM` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`OPR_ID`),
  UNIQUE KEY `LOGIN_ID_UQ` (`LOGIN_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8 COMMENT='系统操作员信息表';

-- ----------------------------
-- Table structure for t_sys_opr_info_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_opr_info_login_log`;
CREATE TABLE `t_sys_opr_info_login_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SESSION_ID` varchar(50) NOT NULL DEFAULT '',
  `LOGIN_ID` varchar(32) DEFAULT NULL,
  `LOGIN_STS` varchar(2) DEFAULT '01' COMMENT '登录状态 01：登录中 00：已退出',
  `IP` varchar(30) DEFAULT NULL,
  `LOGIN_ADDRESS` varchar(128) DEFAULT NULL COMMENT '登录地址',
  `CRT_DT` varchar(8) DEFAULT NULL,
  `CRT_TM` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `INDEX_SESSION_ID_OPR_ID` (`SESSION_ID`,`LOGIN_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2069 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_opr_rol_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_opr_rol_rel`;
CREATE TABLE `t_sys_opr_rol_rel` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OPR_ID` int(10) NOT NULL COMMENT '操作员ID',
  `REL_TYPE` varchar(2) NOT NULL DEFAULT '' COMMENT '关系类型 01:拥有 00:可向下授权',
  `ROLE_ID` int(10) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `OPR_ID` (`OPR_ID`,`REL_TYPE`,`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1012 DEFAULT CHARSET=utf8 COMMENT='系统操作员角色关系';

-- ----------------------------
-- Table structure for t_sys_org_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_org_info`;
CREATE TABLE `t_sys_org_info` (
  `ORG_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '机构ID6位序列',
  `ORG_NAME` varchar(100) DEFAULT NULL COMMENT '机构简称',
  `BUSINESS_ADDRESS` varchar(1000) DEFAULT NULL COMMENT '注册经营地址',
  `CONTRACT_NO` varchar(64) DEFAULT NULL COMMENT '营业执照注册号',
  `ORG_EMAIL` varchar(100) DEFAULT NULL COMMENT '机构联系人邮箱',
  `ORG_CONTACT` varchar(200) DEFAULT NULL COMMENT '机构联系人',
  `ORG_STS` varchar(2) DEFAULT '01' COMMENT '机构状态 00:注销,01:正常',
  `ORG_TYPE` varchar(2) DEFAULT '01' COMMENT '机构类型 01：普通机构',
  `ORG_LEVEL` char(1) DEFAULT '1' COMMENT '机构等级',
  `ORG_PID` varchar(12) DEFAULT NULL COMMENT '上级机构',
  `ORG_RELATIONS` varchar(128) DEFAULT NULL COMMENT '机构关系大字段',
  PRIMARY KEY (`ORG_ID`),
  KEY `org_sts_index` (`ORG_STS`) USING BTREE,
  KEY `org_type_index` (`ORG_TYPE`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8 COMMENT='机构信息表';

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `ROLE_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `ROLE_NAME` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `ROLE_TYPE` varchar(2) DEFAULT '01' COMMENT '角色类型 01: 管理',
  `ROLE_STS` varchar(2) NOT NULL DEFAULT '01' COMMENT '状态 00:失效\r\n            01:有效',
  `ROLE_DES` varchar(50) DEFAULT NULL COMMENT '角色描述',
  `RMK` varchar(200) DEFAULT NULL COMMENT '备注',
  `CRT_ID` varchar(10) DEFAULT NULL,
  `CRT_DT` varchar(8) DEFAULT NULL,
  `CRT_TM` varchar(6) DEFAULT NULL,
  `UPT_ID` varchar(10) DEFAULT NULL,
  `UPT_DT` varchar(8) DEFAULT NULL,
  `UPT_TM` varchar(6) DEFAULT NULL,
  `TM_SMP` varchar(26) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1012 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Table structure for t_sys_role_item_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_item_rel`;
CREATE TABLE `t_sys_role_item_rel` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(10) NOT NULL COMMENT '角色ID',
  `ITEM_ID` int(6) NOT NULL COMMENT '菜单项ID',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ROLE_ID` (`ROLE_ID`,`ITEM_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='系统菜单按钮角色关系表';

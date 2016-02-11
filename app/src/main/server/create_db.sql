CREATE DATABASE what_to_eat CHARACTER SET utf8 COLLATE utf8_bin;
USE what_to_eat;

CREATE TABLE dishes (
    _id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT,
    file_name TEXT
) CHARACTER SET=utf8;

INSERT INTO `dishes` VALUES (1, 'ข้าวผัด', 'kao_pad.jpg');
INSERT INTO `dishes` VALUES (2, 'ข้าวไข่เจียว', 'kao_kai_jeaw.jpg');
INSERT INTO `dishes` VALUES (3, 'ข้าวหน้าเป็ด', 'kao_na_ped.jpg');
INSERT INTO `dishes` VALUES (4, 'ข้าวมันไก่', 'kao_mun_kai.jpg');
INSERT INTO `dishes` VALUES (5, 'ข้าวหมูแดง', 'kao_moo_dang.jpg');
INSERT INTO `dishes` VALUES (6, 'ราดหน้า', 'rad_na.jpg');
INSERT INTO `dishes` VALUES (7, 'ผัดซีอิ๊ว', 'pad_sie_eew.jpg');
INSERT INTO `dishes` VALUES (8, 'ผัดไทย', 'pad_thai.jpg');
INSERT INTO `dishes` VALUES (9, 'ส้มตำไก่ย่าง', 'som_tum_kai_yang.jpg');

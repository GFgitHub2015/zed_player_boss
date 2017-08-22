/**
 * 2017-03-17
 * 添加国家所在的时区Id
 * Chris
 * ****************/
ALTER TABLE `zed_player_country` ADD COLUMN `zone_time_id`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '时区Id' AFTER `country_id`;

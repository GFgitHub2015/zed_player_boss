/**添加本地化相关列表的相关字段*/
/**
 * 2017-02-22
 * 添加网址导航国家码字段
 * Chris
 * ****************/
ALTER TABLE `zed_player_site_navigate` ADD COLUMN `country_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家码' AFTER `status`;
/**
 * 2017-02-22
 * 添加活动国家码字段
 * Chris
 * ****************/
ALTER TABLE `zed_player_promotion_info` ADD COLUMN `country_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家码' AFTER `top_flag`;
/**
 * 2017-02-22
 * 添加热门电影国家码字段
 * Chris
 * ****************/
ALTER TABLE `zed_player_hot_video` ADD COLUMN `country_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家码' AFTER `tag_type`;

/**
 * 2017-03-06
 * 添加资源访问国家表.
 * Chris
 * ****************/
ALTER  TABLE `zed_player_country_visit` modify COLUMN `country_id`  varchar(32);

/**
 * 2017-02-22
 * 添加热门用户排序表
 * Chris
 * ****************/
CREATE TABLE `zed_player_user_hot_sort` (
  `USER_SORT_ID` varchar(32) NOT NULL COMMENT '主键编号',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户编号',
  `SORT` int(11) NOT NULL COMMENT '用户排序序号',
  `COUNTRY_CODE` varchar(32) DEFAULT NULL COMMENT '用户注册时的国家码',
  PRIMARY KEY (`USER_SORT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='热门用户排序表';

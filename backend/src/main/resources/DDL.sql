create database monki;
use monki;

/* user information DDL */
create table `monki`.`t_user` (
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `email`      VARCHAR(200) NOT NULL COMMENT '用户邮箱',
    PRIMARY KEY (`id`)
) COMMENT='user information';

/* user search condition */
create table `monki`.`t_search_action` (
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `uid`        BIGINT       NOT NULL COMMENT '用户表主键',
    `content`    VARCHAR(200) NOT NULL DEFAULT "" COMMENT '用户搜索内容',
    `type`       int          NOT NULL DEFAULT 0  COMMENT '用户搜索类型',
    `time`       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
) COMMENT='user search condition';

/* user click condition */
create table `monki`.`t_click_action` (
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `uid`        BIGINT       NOT NULL COMMENT '用户表主键',
    `mid`        VARCHAR(200) NOT NULL DEFAULT "" COMMENT '用户选择的电影的主键',
    `genre`      VARCHAR(200) NOT NULL DEFAULT "" COMMENT '电影分类',
    `name`       VARCHAR(200) DEFAULT "" COMMENT '电影名字',
    `time`       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
) COMMENT='user click condition';

/* recommend preparation */
create table `monki`.`t_recommend` (
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `uid`        BIGINT       NOT NULL COMMENT '用户表主键',
    `movies`     VARCHAR(200) NOT NULL DEFAULT "" COMMENT "召回推荐给用户的电影",
    PRIMARY KEY (`id`)
) COMMENT='recommend movies for each user';
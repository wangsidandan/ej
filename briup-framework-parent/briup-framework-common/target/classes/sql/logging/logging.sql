CREATE TABLE `logging` (
  `id` varchar(50) NOT NULL,
  `client_ip` varchar(50) DEFAULT NULL,
  `original_ip` varchar(50) DEFAULT NULL,
  `request_uri` varchar(255) DEFAULT NULL,
  `request_time` datetime DEFAULT NULL,
  `response_time` datetime DEFAULT NULL,
  `using_time` varchar(64) DEFAULT NULL,
  `request_method` varchar(8) DEFAULT NULL,
  `declared_method_name` varchar(255) DEFAULT NULL,
  `declared_class_name` varchar(255) DEFAULT NULL,
  `referer` varchar(255) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `modular` varchar(50) NOT NULL,
  `operation` varchar(50) DEFAULT NULL,
  `logging` varchar(255) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `real_name` varchar(50) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL,
  `accounts` varchar(100) DEFAULT NULL,
  `successful` varchar(2) DEFAULT NULL,
  `error_status` varchar(10) DEFAULT NULL,
  `error_code` varchar(255) DEFAULT NULL,
  `error_message` varchar(255) DEFAULT NULL,
  `uid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `logging`
ADD COLUMN `type`  int(2) NOT NULL DEFAULT 1;

ALTER TABLE `logging`
ADD COLUMN `unit_id`  varchar(50) NOT NULL DEFAULT '',
ADD COLUMN `app`  varchar(50) NOT NULL DEFAULT '',
ADD COLUMN `device`  varchar(50) NOT NULL DEFAULT '';


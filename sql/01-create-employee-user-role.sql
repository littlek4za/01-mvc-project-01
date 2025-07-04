CREATE DATABASE  IF NOT EXISTS `employee_directory`;
USE `employee_directory`;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `employee`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `users_roles`;
SET foreign_key_checks = 1;

CREATE TABLE `user`(
`id` INT NOT NULL AUTO_INCREMENT,
`username` VARCHAR(50) NOT NULL,
`password` CHAR(80) NOT NULL,
`enable` tinyint NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `employee` (
`id` INT NOT NULL AUTO_INCREMENT,
`first_name` varchar(45) DEFAULT NULL,
`last_name` varchar(45) DEFAULT NULL,
`email` varchar(45) DEFAULT NULL,
`user_id` INT NOT NULL,
PRIMARY KEY (`id`),
CONSTRAINT `FK_EMPLOYEE_TO_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `role`(
`id` INT NOT NULL AUTO_INCREMENT,
`name` varchar(50) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `users_roles` (
`user_id` INT NOT NULL,
`role_id` INT NOT NULL,
PRIMARY KEY (`user_id`,`role_id`),

-- create Database Index for role_id
KEY `FK_role_idx` (`role_id`),

-- define as foreign key referring to user - id
-- when do update/delete on user - id, no action
CONSTRAINT `FK_ROLE_TO_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION,

-- define as foreign key referring to role - id
-- when do update/delete on role - id, no action
CONSTRAINT `FK_USER_TO_ROLE` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION
)Engine=InnoDB DEFAULT CHARSET=latin1;

-- create user and insert the created id into employee table while creating employee
INSERT INTO `user` (`username`,`password`,`enable`) VALUES
('admin','$2a$10$rfOzyTNYbIZVhGMNM0K/e.zs3BMadrJTCP8seL9Lh25aywB0zRow.',1);
SET @user_id = LAST_INSERT_ID();
INSERT INTO `employee` (`first_name`, `last_name`, `email`, `user_id`) VALUES
('Admin', 'Company', 'admin@testmail.com',@user_id);

INSERT INTO `user` (`username`,`password`,`enable`) VALUES
('user1','$2a$10$NyVxyjLp0EcgG6dcSWRSCes49oSX3u10qA2ZLaz/LSk6sqzBvcqb6',1);
SET @user_id = LAST_INSERT_ID();
INSERT INTO `employee` (`first_name`, `last_name`, `email`, `user_id`) VALUES
('User1','Company','user1@testmail.com', @user_id);

INSERT INTO `user` (`username`,`password`,`enable`) VALUES
('user2','$2a$10$qE2HDl0ilqugp9haU1lxceAXHl7oqFiB.DwPLS087X./N8OxLuB7m',1);
SET @user_id = LAST_INSERT_ID();
INSERT INTO `employee` (`first_name`, `last_name`, `email`, `user_id`) VALUES
('User2','Company','user2@testmail.com', @user_id);

INSERT INTO `user` (`username`,`password`,`enable`) VALUES
('user3','$2a$10$a6Z18FPY.WwzBNm28eExs.60e7Gsv7s1erzjP13mfuc0gBoKFDY6m',1);
SET @user_id = LAST_INSERT_ID();
INSERT INTO `employee` (`first_name`, `last_name`, `email`, `user_id`) VALUES
('User3','Company','user3@testmail.com', @user_id);

-- create role
INSERT INTO `role` (`name`) VALUES
('ROLE_EMPLOYEE'),
('ROLE_MANAGER'),
('ROLE_ADMIN');

INSERT INTO `users_roles` (`user_id`,`role_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(3, 1),
(4, 1);
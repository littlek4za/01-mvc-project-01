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
('Amy','$2a$10$h6qAkXp7E2CLS9GieJBw2Oasqyoc3hGScY3bNA3KN/rGJzkLHLPKu',1);
SET @user_id = LAST_INSERT_ID();
INSERT INTO `employee` (`first_name`, `last_name`, `email`, `user_id`) VALUES
('Chan', 'Amy', 'amyc@testmail.com',@user_id);

INSERT INTO `user` (`username`,`password`,`enable`) VALUES
('Zoe','$2a$10$h6qAkXp7E2CLS9GieJBw2Oasqyoc3hGScY3bNA3KN/rGJzkLHLPKu',1);
SET @user_id = LAST_INSERT_ID();
INSERT INTO `employee` (`first_name`, `last_name`, `email`, `user_id`) VALUES
('Man','Zoe','zoem@testmail.com', @user_id);

INSERT INTO `user` (`username`,`password`,`enable`) VALUES
('Ian','$2a$10$h6qAkXp7E2CLS9GieJBw2Oasqyoc3hGScY3bNA3KN/rGJzkLHLPKu',1);
SET @user_id = LAST_INSERT_ID();
INSERT INTO `employee` (`first_name`, `last_name`, `email`, `user_id`) VALUES
('Yit','Ian','iany@testmail.com', @user_id);

INSERT INTO `user` (`username`,`password`,`enable`) VALUES
('Ken','$2a$10$h6qAkXp7E2CLS9GieJBw2Oasqyoc3hGScY3bNA3KN/rGJzkLHLPKu',1);
SET @user_id = LAST_INSERT_ID();
INSERT INTO `employee` (`first_name`, `last_name`, `email`, `user_id`) VALUES
('Wong','Ken','kenw@testmail.com', @user_id);

-- create role
INSERT INTO `role` (`name`) VALUES
('ROLE_EMPLOYEE'),
('ROLE_MANAGER'),
('ROLE_ADMIN');

INSERT INTO `users_roles` (`user_id`,`role_id`) VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3),
(4, 1);
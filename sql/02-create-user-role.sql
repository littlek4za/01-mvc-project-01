USE `employee_directory`;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `user`;
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

INSERT INTO `user` (`username`,`password`,`enable`) VALUES
('Amy','$2a$10$45HY3IrjHMth3I6zCl25MujYNdzeRccRfTwbfvfNBfuxafD53rt.e',1),
('Zoe','$2a$10$45HY3IrjHMth3I6zCl25MujYNdzeRccRfTwbfvfNBfuxafD53rt.e',1),
('Ian','$2a$10$45HY3IrjHMth3I6zCl25MujYNdzeRccRfTwbfvfNBfuxafD53rt.e',1);

CREATE TABLE `role`(
`id` INT NOT NULL AUTO_INCREMENT,
`name` varchar(50) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1; 

INSERT INTO `role` (`name`) VALUES
('ROLE_EMPLOYEE'),
('ROLE_MANAGER'),
('ROLE_ADMIN');

CREATE TABLE `users_roles` (
`user_id` INT NOT NULL,
`role_id` INT NOT NULL,
PRIMARY KEY (`user_id`,`role_id`),

-- create Database Index for role_id
KEY `FK_role_idx` (`role_id`),

-- define as foreign key refereing to user - id
-- when do update/delete on user - id, no action 
CONSTRAINT `FK_user` FOREIGN KEY (`user_id`)
REFERENCES `user` (`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION,

-- define as foreign key refereing to role - id
-- when do update/delete on role - id, no action 
CONSTRAINT `FK_role` FOREIGN KEY (`role_id`)
REFERENCES `role` (`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION
)Engine=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `users_roles` (`user_id`,`role_id`) VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3);
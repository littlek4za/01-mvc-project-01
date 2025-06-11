CREATE DATABASE IF NOT EXISTS `employee_directory`;
USE `employee_directory`;
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
`id` INT NOT NULL AUTO_INCREMENT,
`first_name` varchar(45) DEFAULT NULL,
`last_name` varchar(45) DEFAULT NULL,
`email` varchar(45) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `employee` (first_name,last_name,email) VALUES 
('Chan','Amy','amyc@testmail.com'),
('Man','Zoe','zoem@testmail.com'),
('Yit','Ian','iany@testmail.com'),
('Wong','Ken','kenw@testmail.com');

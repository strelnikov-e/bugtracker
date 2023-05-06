reate table `users` 
(
`id` BIGINT NOT NULL AUTO_INCREMENT,
`username` varchar(50) NOT NULL UNIQUE,
`email` varchar(50) NOT NULL UNIQUE,
`password` char(68) NOT NULL,
`enabled` tinyint DEFAULT 1,
`first_name` varchar(50) NOT NULL,
`last_name` varchar(50) NOT NULL, 
`company_name` varchar(50),
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
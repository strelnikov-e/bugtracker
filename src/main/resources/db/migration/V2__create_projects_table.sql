create table `projects`
(
`id` bigint not null auto_increment,
`name` varchar(50) not null,
`description` text,
`keyword` char(3),
`lead_username` varchar(50),
`status` char(4) default "OPEN",
`start_date` date,
`due_date` date,
`end_date` date,
PRIMARY KEY (`id`),
CONSTRAINT `UNIQUE_key` UNIQUE(`keyword`,`lead_username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

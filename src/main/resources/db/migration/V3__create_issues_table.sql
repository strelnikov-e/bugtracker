create table `issues`
(
`id` bigint not null auto_increment,
`name` varchar(50) not null,
`description` varchar(256),
`assignee` varchar(50),
`reporter` varchar(50),
`status` char(4) default "OPEN",
`severity` char(3) default "MED",
`reproducible` bool default 0,
`start_date` date not null,
`due_date` date,
`closed_date` date,
`project_id` bigint,
PRIMARY KEY (`id`),
CONSTRAINT `FK_issues_project_id` FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

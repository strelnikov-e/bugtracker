create database if not exists `issuetracker`;
use `issuetracker`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `projects`;
DROP TABLE IF EXISTS `issues`;
DROP TABLE IF EXISTS `users-projects`;
DROP TABLE IF EXISTS `users-issues`;
DROP TABLE IF EXISTS `user-issue`;
DROP TABLE IF EXISTS `user-project`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `project`;
DROP TABLE IF EXISTS `issue`;

create table `users` (
`username` varchar(50),
`email` varchar(50) not null UNIQUE,
`password` char(68) not null,
`enabled` tinyint not null,
`first_name` varchar(50) not null,
`last_name` varchar(50) not null, 
`company_name` varchar(50),
PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


create table `authorities` (
`username` varchar(50) NOT NULL,
`authority` varchar(50) NOT NULL,
UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users`(`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


create table `projects` (
`id` bigint not null auto_increment,
`name` varchar(50) not null,
`description` text,
`key` char(3),
`lead_username` varchar(50) not null,
`status` char(4) default "OPEN",
`start_date` date not null,
`due_date` date,
`end_date` date,
PRIMARY KEY (`id`)
CONSTRAINT `UK_projects_key` UNIQUE(`key`,`lead_username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


create table `issues` (
`id` bigint not null auto_increment,
`name` varchar(50) not null,
`description` varchar(256),
`assignee` varchar(50),
`reporter` varchar(50),
`status` char(4) default "OPEN",
`severity` char(3) default "MED",
`reproducible` bool default 0,
`tag` int,
`start_date` date not null,
`due_date` date,
`closed_date` date
`project_id` bigint not null,
PRIMARY KEY (`id`),
CONSTRAINT `FK_issue_idx` FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`)
ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


create table `issues-tags` (
`issue_id` bigint,
`name` varchar(50),
UNIQUE KEY (`issue_id`, `name`)
CONSTRAINT `FK_issues_tags` FOREIGN KEY (`issue_id`) REFERENCES `issues`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `users` (`email`,`password`, `enabled`, `first_name`,`last_name`)
VALUES 
        ("john@mail.com","{bcrypt}$2a$12$nfqMxM39kfD5mS04Y5ChcujGQPZBWyenO/rbqT79pRuALP.Zrx.H.",1 ,"John","Doe"),
        ("mary@mail.com","{bcrypt}$2a$12$nfqMxM39kfD5mS04Y5ChcujGQPZBWyenO/rbqT79pRuALP.Zrx.H.",1 ,"Mary","Jane"),
        ("susan@mail.com","{bcrypt}$2a$12$nfqMxM39kfD5mS04Y5ChcujGQPZBWyenO/rbqT79pRuALP.Zrx.H.",1 ,"Susan","Park");

INSERT INTO `authorities`
VALUES
        ("john@mail.com","ROLE_EMPLOYEE"),
        ("mary@mail.com","ROLE_EMPLOYEE"),
        ("mary@mail.com","ROLE_MANAGER"),
        ("susan@mail.com","ROLE_EMPLOYEE"),
        ("susan@mail.com","ROLE_MANAGER"),
        ("susan@mail.com","ROLE_ADMIN");

INSERT INTO `projects` (`name`,`description`,`key`,`lead_username`,`start_date`,`due_date`)
VALUES
        ("Bugtracker",
        "Training project with minimal functionality to track project issues and plan development process",
        "BUG","mary","2023-04-11","2023-06-01"),
        ("Blog",
        "Demo blog project",
        "BLG","john","2023-04-22","2023-06-20");

INSERT INTO `issues` (`name`,`description`,`assignee`,`reporter`,`start_date`,`due_date`,`project_id`)
VALUES 
        ("Create issues database",
        "Develop a database with field required to account issues for a specific project",
        "john","mary","2023-04-11","2023-04-30",1),
        ("Create projects database",
        "Develop a database with field required to account projects for an user",
        "john","mary","2023-04-11","2023-04-30",1),
        ("Create users database",
        "Develop a database with user specific information",
        "mary","susan","2023-04-11","2023-04-30",1),
        ("Create users database",
        "Develop a database with user specific information for blog",
        "john","john","2023-04-22","2023-05-15",2),
        ("Design user interface",
        "Develop a database with user specific information for blog",
        "john","susan","2023-04-22","2023-05-30",2);

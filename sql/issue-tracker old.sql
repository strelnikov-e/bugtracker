create database if not exists `issuetracker`;
use `issuetracker`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `users`;

create table `users` (
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

DROP TABLE IF EXISTS `authorities`;

create table `authorities` (
`user_id` BIGINT NOT NULL,
`authority` varchar(50) NOT NULL,
UNIQUE KEY `authorities_idx_1` (`user_id`,`authority`),
CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `projects`;

create table `projects` (
`id` bigint not null auto_increment,
`name` varchar(50) not null,
`description` text,
`keyword` char(3),
`lead_username` varchar(50) not null,
`status` char(4) default "OPEN",
`start_date` date not null,
`due_date` date,
`end_date` date,
PRIMARY KEY (`id`),
CONSTRAINT `UNIQUE_key` UNIQUE(`keyword`,`lead_username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `users_projects`;

create table `users_projects` (
`user_id` BIGINT NOT NULL,
`project_id` bigint NOT NULL,
`authority` varchar(50) NOT NULL,
UNIQUE KEY `authorities_idx_1` (`user_id`,`project_id`,`authority`),
CONSTRAINT `FK_user_project_id_1` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
CONSTRAINT `FK_user_project_id_2` FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `issues`;

create table `issues` (
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
CONSTRAINT `FK_project_id` FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `tags`;

create table `tags` (
`id` bigint NOT NULL auto_increment,
`name` varchar(50) NOT NULL UNIQUE,
primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `issues_tags`;

CREATE TABLE `issues_tags` (
  `tag_id` bigint NOT NULL,
  `issue_id` bigint NOT NULL,
  PRIMARY KEY (`tag_id`,`issue_id`),
  CONSTRAINT `FK_issues_tags_TAG` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_issues_tags_ISSUE` FOREIGN KEY (`issue_id`) REFERENCES `issues` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



INSERT INTO `users` (`username`,`email`,`password`, `enabled`, `first_name`,`last_name`)
VALUES
        ("john","john@mail.com","{bcrypt}$2a$12$nfqMxM39kfD5mS04Y5ChcujGQPZBWyenO/rbqT79pRuALP.Zrx.H.",1 ,"John","Doe"),
        ("mary","mary@mail.com","{bcrypt}$2a$12$nfqMxM39kfD5mS04Y5ChcujGQPZBWyenO/rbqT79pRuALP.Zrx.H.",1 ,"Mary","Jane"),
        ("susan","susan@mail.com","{bcrypt}$2a$12$nfqMxM39kfD5mS04Y5ChcujGQPZBWyenO/rbqT79pRuALP.Zrx.H.",1 ,"Susan","Park");

INSERT INTO `authorities`
VALUES
        (1,"ROLE_EMPLOYEE"),
        (2,"ROLE_EMPLOYEE"),
        (2,"ROLE_MANAGER"),
        (3,"ROLE_EMPLOYEE"),
        (3,"ROLE_MANAGER"),
        (3,"ROLE_ADMIN");




INSERT INTO `projects` (`name`,`description`,`keyword`,`lead_username`,`start_date`,`due_date`)
VALUES
        ("Bugtracker",
        "Training project with minimal functionality to track project issues and plan development process",
        "BUG","mary","2023-04-11","2023-06-01"),
        ("Blog",
        "Demo blog project",
        "BLG","john","2023-04-22","2023-06-20");

INSERT INTO `users_projects`
VALUES
        (1,1,"ROLE_EMPLOYEE"),
        (2,1,"ROLE_EMPLOYEE"),
        (2,1,"ROLE_MANAGER"),
        (3,1,"ROLE_EMPLOYEE"),
        (3,1,"ROLE_MANAGER"),
        (3,1,"ROLE_ADMIN"),
        (2,2,"ROLE_EMPLOYEE"),
        (3,2,"ROLE_EMPLOYEE"),
        (3,2,"ROLE_MANAGER"),
        (3,2,"ROLE_ADMIN");

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


INSERT INTO `tags` (`name`)
VALUES
        ("database"),
        ("design"),
        ("issues"),
        ("projects"),
        ("users"),
        ("UI");

INSERT INTO `issues_tags`
VALUES
        (1,1),
        (1,2),
        (1,3),
        (2,1),
        (2,2),
        (3,1);

SET FOREIGN_KEY_CHECKS = 1;
use `issuetracker`;

SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO `users` (`username`,`email`,`password`, `enabled`, `first_name`,`last_name`)
VALUES
        ("john","john@mail.com","{bcrypt}$2a$12$nfqMxM39kfD5mS04Y5ChcujGQPZBWyenO/rbqT79pRuALP.Zrx.H.",1 ,"John","Doe"),
        ("mary","mary@mail.com","{bcrypt}$2a$12$nfqMxM39kfD5mS04Y5ChcujGQPZBWyenO/rbqT79pRuALP.Zrx.H.",1 ,"Mary","Jane"),
        ("susan","susan@mail.com","{bcrypt}$2a$12$nfqMxM39kfD5mS04Y5ChcujGQPZBWyenO/rbqT79pRuALP.Zrx.H.",1 ,"Susan","Park");

INSERT INTO `projects` (`name`,`description`,`keyword`,`lead_username`,`start_date`,`due_date`)
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

INSERT INTO `projects_roles` (`user_id`, `project_id`, `type`)
VALUES
		(1,1,"ROLE_ADMIN"),
        (2,2,"ROLE_ADMIN"),
        (3,1,"ROLE_MANAGER");
        
INSERT INTO `issues_roles` (`user_id`, `issue_id`, `type`)
VALUES
		(1,1,"ROLE_VIEWER"),
        (1,2,"ROLE_VIEWER"),
        (1,3,"ROLE_VIEWER"),
        (1,4,"ROLE_ASSIGNEE"),
        (1,5,"ROLE_REPORTER"),
        (2,1,"ROLE_ASSIGNEE"),
        (2,2,"ROLE_REPORTER"),
        (2,3,"ROLE_VIEWER"),
        (2,4,"ROLE_VIEWER"),
        (2,5,"ROLE_VIEWER"),
        (3,1,"ROLE_VIEWER"),
        (3,2,"ROLE_VIEWER"),
        (3,3,"ROLE_VIEWER");

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
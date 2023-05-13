SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO `users` (`username`,`email`,`password`, `enabled`, `first_name`,`last_name`)
VALUES
        ("root","root@mail.com","{bcrypt}$2a$12$nfqMxM39kfD5mS04Y5ChcujGQPZBWyenO/rbqT79pRuALP.Zrx.H.",1 ,"root","user"),
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
            "BLG","john","2023-04-22","2023-06-20"),
        ("Project #3",
            "Test project",
            "TST","susan","2023-04-11","2023-06-01");

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
        "john","susan","2023-04-22","2023-05-30",2),
        ("Issue of test project",
        "Test issue",
        "mary","susan","2023-04-22","2023-05-30",2);

INSERT INTO `users_roles` (`user_id`, `type`)
VALUES
		(1,"ROOT");

INSERT INTO `projects_roles` (`user_id`, `project_id`, `type`)
VALUES
		(2,1,"ADMIN"),
        (3,2,"ADMIN"),
        (4,1,"MANAGER"),
        (2,3,"VIEWER");

INSERT INTO `issues_roles` (`user_id`, `issue_id`, `type`)
VALUES
		(2,1,"VIEWER"),
        (2,2,"VIEWER"),
        (2,3,"VIEWER"),
        (2,4,"ASSIGNEE"),
        (2,5,"REPORTER"),
        (3,1,"ASSIGNEE"),
        (3,2,"REPORTER"),
        (3,3,"VIEWER"),
        (3,4,"VIEWER"),
        (3,5,"VIEWER"),
        (4,1,"VIEWER"),
        (4,2,"VIEWER"),
        (4,3,"VIEWER"),
        (2,6,"ASSIGNEE");

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
        (3,1),
        (6,2);

SET FOREIGN_KEY_CHECKS = 1;
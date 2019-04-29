DROP DATABASE IF EXISTS mdeto;
CREATE DATABASE mdeto;
USE mdeto;

CREATE TABLE app_user (
	user_id INT UNIQUE AUTO_INCREMENT,
    first_name VARCHAR(20),
    mid_name VARCHAR(20),
    last_name VARCHAR(20),
    active boolean, 
    title VARCHAR(40),
    project_manager boolean,
    estimator boolean,
    PRIMARY KEY (user_id)
);

DELIMITER $$
CREATE TRIGGER before_insert_user
BEFORE INSERT ON app_user
FOR EACH ROW
	IF new.project_manager && new.estimator
    THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'User cannot be both Project Manager and Estimator';
	END IF;
$$
DELIMITER ;


CREATE TABLE project (
    project_id INT UNIQUE AUTO_INCREMENT,
    project_name VARCHAR(255) NOT NULL,
    start_date DATE,												# date format is YYYY-MM-DD
    end_date DATE,													# date format is YYYY-MM-DD
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,				# datetime format is YYYY-MM-DD HH:MM:SS
    pop INT GENERATED ALWAYS AS ( datediff(end_date, start_date) ),	# period of performance in days
    project_manager INT,
    short_desc TEXT,
    mse_wbs VARCHAR(4) DEFAULT '1',
    PRIMARY KEY (project_id),
    FOREIGN KEY (project_manager) REFERENCES app_user(user_id)
);

CREATE TABLE clin (
	clin_id INT UNIQUE AUTO_INCREMENT,
    clin_num INT NOT NULL,
    start_date DATE,												# date format is YYYY-MM-DD
    end_date DATE,													# date format is YYYY-MM-DD
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,				# datetime format is YYYY-MM-DD HH:MM:SS
    pop INT GENERATED ALWAYS AS ( datediff(end_date, start_date) ),	# period of performance in days
    project_id INT,
    project_type VARCHAR(20),
    short_desc TEXT,
    mse_wbs VARCHAR(8),
    PRIMARY KEY (clin_id),
    FOREIGN KEY (project_id) REFERENCES project(project_id)
);

DELIMITER $$
CREATE TRIGGER before_insert_clin
BEFORE INSERT ON clin
FOR EACH ROW 
BEGIN
	DECLARE project_num INT;
	SELECT count(*) FROM project WHERE project_id = new.project_id INTO project_num;
	
    IF project_num <= 0 THEN
		SET NEW.mse_wbs = '1.1';
	ELSEIF project_num > 1 THEN
		SET NEW.mse_wbs = '1.2';
	END IF;
END; $$
DELIMITER ;

CREATE TABLE org (
	org_id INT UNIQUE AUTO_INCREMENT,
    org_name VARCHAR(255) NOT NULL,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,				# datetime format is YYYY-MM-DD HH:MM:SS
    clin_id INT,
    detailed_org TEXT,
    mse_wbs VARCHAR(16),
    PRIMARY KEY (org_id),
    FOREIGN KEY (clin_id) REFERENCES clin(clin_id)
);

CREATE TABLE wrk_pkg (
	wrk_pkg_id INT UNIQUE AUTO_INCREMENT,
    wrk_pkg_name VARCHAR(255) NOT NULL,
    start_date DATE,												# date format is YYYY-MM-DD
    end_date DATE,													# date format is YYYY-MM-DD
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,				# datetime format is YYYY-MM-DD HH:MM:SS
    pop INT GENERATED ALWAYS AS ( datediff(end_date, start_date) ),	# period of performance in days
	org_id INT,
    detailed_desc TEXT,
    mse_wbs VARCHAR(32),
	cust_wbs VARCHAR(32),
    author INT,
    version DOUBLE,
    scope VARCHAR(40),
    PRIMARY KEY (wrk_pkg_id),
    FOREIGN KEY (org_id) REFERENCES org(org_id),
    FOREIGN KEY (author) REFERENCES app_user(user_id)
);

CREATE TABLE task (
	task_id INT UNIQUE AUTO_INCREMENT,
    task_name VARCHAR(255) NOT NULL,
    start_date DATE,												# date format is YYYY-MM-DD
    end_date DATE,													# date format is YYYY-MM-DD
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,				# datetime format is YYYY-MM-DD HH:MM:SS
    pop INT GENERATED ALWAYS AS ( datediff(end_date, start_date) ),	# period of performance in days
    boe_formula TEXT,
    emr TEXT, 
    staff_hours DOUBLE,
    mse_wbs VARCHAR(64)
);

CREATE VIEW project_view AS 
	SELECT 
		p.project_id, 
		p.project_name, 
		p.start_date, 
		p.end_date, 
		CAST(p.created_date AS DATE) AS 'created_date', 
        p.pop,
		concat(u.first_name, ' ', u.last_name) AS 'project_manager',
		p.short_desc
	FROM 
		project p
	JOIN 
		app_user u ON 
		u.user_id = p.project_manager;

CREATE VIEW wrk_pkg_view AS
	SELECT
		w.wrk_pkg_id,
		w.wrk_pkg_name,
		w.start_date,
		w.end_date,
		CAST(w.created_date AS DATE) AS 'created_date',
		w.pop,
		o.org_name,
		w.detailed_desc,
		w.mse_wbs,
		w.cust_wbs,
		concat(w.first_name, ' ', w.last_name) AS 'author',
		w.version,
		w.scope
	FROM
	(
		SELECT 
			*
		FROM 
			wrk_pkg p
		JOIN 
			app_user u ON 
			u.user_id = p.author
	) w
	JOIN org o ON o.org_id = w.org_id;

INSERT INTO app_user ( first_name, last_name, active, project_manager )
   VALUES ('Project', 'Manager', true, true );
INSERT INTO app_user ( first_name, last_name, active, estimator )
   VALUES ( 'Estimator', '', true, true );
INSERT INTO app_user ( first_name, last_name, active, project_manager )
   VALUES ( 'Test', 'Project Manager', true, true );

INSERT INTO project ( project_name, start_date, end_date, project_manager, short_desc )
   VALUES ( 'Primary Test', '2013-05-01', '2020-05-11', 1, 'Testing the first project' );
INSERT INTO project ( project_name, start_date, end_date, project_manager, short_desc )
   VALUES ( 'Second Project', '2011-08-11', '2023-05-16', 1, 'Another Test Project' );
INSERT INTO project ( project_name, start_date, end_date, project_manager, short_desc )
   VALUES ( 'Third Project', '2019-01-06', '2020-06-20', 3, 'Testing Projects' );
INSERT INTO project ( project_name, start_date, end_date, project_manager, short_desc )
   VALUES ( 'Fourth Project', '2015-03-21', '2020-12-25', 1, 'One more project under the sun' );
INSERT INTO project ( project_name, start_date, end_date, project_manager, short_desc )
   VALUES ( 'Bee Movie', '2007-11-02', '2007-11-02', 3, "According to all known laws \nof aviation, \nthere is no way a bee \nshould be able to fly. \nIts wings are too small to get \nits fat little body off the ground. \nThe bee, of course, flies anyway \nbecause bees don't care \nwhat humans think is impossible. \nYellow, black. Yellow, black. \nYellow, black. Yellow, black. \nOoh, black and yellow! \nLet's shake it up a little. \nBarry! Breakfast is ready!");

INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 1, '2013-05-01', '2020-05-11', 1, 'Contract Line Item (CLIN) 1');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 2, '2011-08-11', '2020-05-16', 1, 'Contract Line Item (CLIN) 2');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 3, '2020-05-16', '2023-05-16', 2, 'Contract Line Item (CLIN) 3');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 4, '2020-05-16', '2023-05-16', 2, 'Contract Line Item (CLIN) 4');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 5, '2020-05-16', '2023-05-16', 3, 'Contract Line Item (CLIN) 5');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 6, '2020-05-16', '2023-05-16', 3, 'Contract Line Item (CLIN) 6');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 7, '2020-05-16', '2023-05-16', 4, 'Contract Line Item (CLIN) 7');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 8, '2020-05-16', '2023-05-16', 4, 'Contract Line Item (CLIN) 8');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 9, '2020-05-16', '2023-05-16', 5, 'Contract Line Item (CLIN) 9');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 10, '2020-05-16', '2023-05-16', 5, 'Contract Line Item (CLIN) 10');
    
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Communication', 1, 'Communication system for CLIN 1');    
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 1, 'DM for CLIN 1');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DSP', 1, 'Defense Support Program for CLIN 1');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 1, 'Radar for CLIN 1');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 2, 'DM for CLIN 2');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'ESSM', 2, 'Evolved Seasparrow Missile for CLIN 2');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 2, 'Radar for CLIN 2');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 3, 'DM for CLIN 3');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 3, 'Radar for CLIN 3');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'STSS', 3, 'Space Tracking and Surveillance System for CLIN 3');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'C2BMC', 4, 'Command and Control, Battle Management, and Communications for CLIN 4');    
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 4, 'DM for CLIN 4');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 4, 'Radar for CLIN 4');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 5, 'DM for CLIN 5');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'ESSM', 5, 'Evolved Seasparrow Missile for CLIN 5');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 5, 'Radar for CLIN 5');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'STSS', 5, 'Space Tracking and Surveillance System for CLIN 5');    
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'C2BMC', 6, 'Command and Control, Battle Management, and Communications for CLIN 6'); 
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 6, 'DM for CLIN 6');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 6, 'Radar for CLIN 6');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Communication', 7, 'Communication system for CLIN 7');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 7, 'DM for CLIN 7');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 7, 'Radar for CLIN 7');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Communication', 8, 'Communication system for CLIN 8');    
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 8, 'DM for CLIN 8');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 8, 'Radar for CLIN 8');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'STSS', 8, 'Space Tracking and Surveillance System for CLIN 8');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Communication', 9, 'Communication system for CLIN 9');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 9, 'DM for CLIN 9');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 9, 'Radar for CLIN 9');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'C2BMC', 10, 'Command and Control, Battle Management, and Communications for CLIN 10');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Communication', 10, 'Communication system for CLIN 19');    
 INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 10, 'DM for CLIN 10');   
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 10, 'Radar for CLIN 10');


INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'test package', '2020-05-16', '2023-05-16', 1, 'detailed description', 1.2, 1.2, 1, 'out of scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'test package 2', '2020-05-16', '2023-05-16', 1, 'detailed description', 1.2, 2.2, 1, 'in scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Test', '2020-05-16', '2023-05-16', 2, 'detailed description', 2.1, 2.1, 2, 'in scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Test 2', '2020-05-16', '2023-05-16', 2, 'detailed description', 2.2, 2.2, 3, 'in scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Another Test', '2020-05-16', '2023-05-16', 3, 'detailed description', 3.1, 3.1, 1, 'out of scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Test', '2020-05-16', '2023-05-16', 3, 'detailed description', 3.2, 3.2, 3, 'out of scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Test Package', '2020-05-16', '2023-05-16', 4, 'detailed description', 4.1, 4.1, 3, 'in scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'package', '2020-05-16', '2023-05-16', 4, 'detailed description', 4.2, 4.2, 3, 'out of scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Work Package', '2020-05-16', '2023-05-16', 5, 'detailed description', 5.1, 5.1, 3, 'unsure scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'package test', '2020-05-16', '2023-05-16', 5, 'detailed description', 5.2, 5.2, 3, 'out of scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Work Package', '2020-05-16', '2023-05-16', 6, 'detailed description', 6.1, 6.1, 3, 'unsure scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'package test', '2020-05-16', '2023-05-16', 6, 'detailed description', 6.2, 6.2, 3, 'out of scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Package', '2020-05-16', '2023-05-16', 7, 'detailed description', 7.1, 7.1, 3, 'in scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'test package', '2020-05-16', '2023-05-16', 7, 'detailed description', 7.2, 7.2, 3, 'in scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Package', '2020-05-16', '2023-05-16', 7, 'detailed description', 7.3, 7.3, 3, 'in scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'test package', '2020-05-16', '2023-05-16', 7, 'detailed description', 7.4, 7.4, 3, 'in scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Work Package', '2020-05-16', '2023-05-16', 8, 'detailed description', 8.1, 8.1, 3, 'in scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'package', '2020-05-16', '2023-05-16', 8, 'detailed description', 8.2, 8.2, 3, 'out of scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Test Package', '2020-05-16', '2023-05-16', 9, 'detailed description', 9.1, 9.1, 3, 'in scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Test Work Package', '2020-05-16', '2023-05-16', 9, 'detailed description', 9.2, 9.2, 3, 'out of scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'Test', '2020-05-16', '2023-05-16', 10, 'detailed description', 10.1, 10.1, 3, 'unknown scope' );
INSERT INTO wrk_pkg ( wrk_pkg_name, start_date, end_date, org_id, detailed_desc, mse_wbs, cust_wbs, author, scope )
	VALUES ( 'work package', '2020-05-16', '2023-05-16', 10, 'detailed description', 10.2, 10.2, 3, 'out of scope' );


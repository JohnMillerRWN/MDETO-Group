DROP DATABASE IF EXISTS mdeto;
CREATE DATABASE mdeto;
USE mdeto;

CREATE TABLE sys_user (
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

CREATE TABLE project (
    project_id INT UNIQUE AUTO_INCREMENT,
    project_name VARCHAR(255) NOT NULL,
    start_date DATE,												# date format is YYYY-MM-DD
    end_date DATE,													# date format is YYYY-MM-DD
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,				# datetime format is YYYY-MM-DD HH:MM:SS
    pop INT GENERATED ALWAYS AS ( datediff(end_date, start_date) ),	# period of performance in days
    project_manager INT,
    short_desc TEXT,
    PRIMARY KEY (project_id),
    FOREIGN KEY (project_manager) REFERENCES sys_user(user_id)
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
    PRIMARY KEY (clin_id),
    FOREIGN KEY (project_id) REFERENCES project(project_id)
);

CREATE TABLE org (
	org_id INT UNIQUE AUTO_INCREMENT,
    org_name VARCHAR(255) NOT NULL,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,				# datetime format is YYYY-MM-DD HH:MM:SS
    clin_id INT,
    detailed_org TEXT,
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
    mse_wbs VARCHAR(10),
	cust_wbs VARCHAR(10),
    author INT,
    version DOUBLE,
    scope VARCHAR(40),
    PRIMARY KEY (wrk_pkg_id),
    FOREIGN KEY (org_id) REFERENCES org(org_id),
    FOREIGN KEY (author) REFERENCES sys_user(user_id)
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
		sys_user u ON 
		u.user_id = p.project_manager;

INSERT INTO sys_user ( first_name, last_name, active, project_manager )
   VALUES ('Project', 'Manager', true, true );
INSERT INTO sys_user ( first_name, last_name, active, estimator )
   VALUES ( 'Estimator', '', true, true );
INSERT INTO sys_user ( first_name, last_name, active, project_manager )
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
	VALUES( 2, '2011-08-11', '2020-05-16', 2, 'Contract Line Item (CLIN) 2');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 3, '2020-05-16', '2023-05-16', 2, 'Contract Line Item (CLIN) 3');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 4, '2020-05-16', '2023-05-16', 3, 'Contract Line Item (CLIN) 4');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 5, '2020-05-16', '2023-05-16', 4, 'Contract Line Item (CLIN) 5');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 6, '2020-05-16', '2023-05-16', 4, 'Contract Line Item (CLIN) 6');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 7, '2020-05-16', '2023-05-16', 5, 'Contract Line Item (CLIN) 7');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 8, '2020-05-16', '2023-05-16', 5, 'Contract Line Item (CLIN) 8');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 9, '2020-05-16', '2023-05-16', 5, 'Contract Line Item (CLIN) 9');
INSERT INTO clin ( clin_num, start_date, end_date, project_id, short_desc )
	VALUES( 10, '2020-05-16', '2023-05-16', 5, 'Contract Line Item (CLIN) 10');

INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 1, 'Radar for CLIN 1');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 1, 'DM for CLIN 1');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 2, 'Radar for CLIN 2');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 2, 'DM for CLIN 2');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 3, 'Radar for CLIN 3');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 3, 'DM for CLIN 3');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 4, 'Radar for CLIN 4');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 4, 'DM for CLIN 4');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 5, 'Radar for CLIN 5');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 5, 'DM for CLIN 5');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 6, 'Radar for CLIN 6');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 6, 'DM for CLIN 6');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 7, 'Radar for CLIN 7');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 7, 'DM for CLIN 7');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 8, 'Radar for CLIN 8');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 8, 'DM for CLIN 8');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 9, 'Radar for CLIN 9');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 9, 'DM for CLIN 9');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'Radar', 10, 'Radar for CLIN 10');
INSERT INTO org ( org_name, clin_id, detailed_org )
	VALUES( 'DM', 10, 'DM for CLIN 10');


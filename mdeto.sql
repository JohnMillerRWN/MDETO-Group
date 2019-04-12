create database mdeto;
use mdeto;

CREATE TABLE IF NOT EXISTS sys_user (
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

CREATE TABLE IF NOT EXISTS project (
    project_id INT UNIQUE AUTO_INCREMENT,
    project_name VARCHAR(255) NOT NULL,
    start_date DATE,									# date format is YYYY-MM-DD
    end_date DATE,										# date format is YYYY-MM-DD
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,	# datetime format is YYYY-MM-DD HH:MM:SS
    project_manager INT,
    short_desc TEXT,
    PRIMARY KEY (project_id),
    FOREIGN KEY (project_manager) REFERENCES sys_user(user_id)
);

CREATE TABLE IF NOT EXISTS clin (
	clin_id INT UNIQUE AUTO_INCREMENT,
    clin_name VARCHAR(255) NOT NULL,
    start_date DATE,									# date format is YYYY-MM-DD
    end_date DATE,										# date format is YYYY-MM-DD
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,	# datetime format is YYYY-MM-DD HH:MM:SS
    project_id INT,
    project_type VARCHAR(20),
    short_desc TEXT,
    PRIMARY KEY (clin_id),
    FOREIGN KEY (project_id) REFERENCES project(project_id)
);

CREATE TABLE IF NOT EXISTS product (
	product_id INT UNIQUE AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    start_date DATE,									# date format is YYYY-MM-DD
    end_date DATE,										# date format is YYYY-MM-DD
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,	# datetime format is YYYY-MM-DD HH:MM:SS
    clin_id INT,
    detailed_desc TEXT,
    PRIMARY KEY (product_id),
    FOREIGN KEY (clin_id) REFERENCES clin(clin_id)
);

CREATE VIEW project_view AS 
	SELECT 
		p.project_id, 
		p.project_name, 
		p.start_date, 
		p.end_date, 
		p.created_date,  
		concat(u.first_name, ' ', u.last_name) AS 'project_manager',
		p.short_desc
	FROM 
		project p
	JOIN 
		sys_user u 
		ON 
		u.user_id = p.project_manager;

INSERT INTO sys_user ( first_name, last_name, active, project_manager )
   VALUES ('Project', 'Manager', true, true );
INSERT INTO sys_user ( first_name, last_name, active, estimator )
   VALUES ( 'Estimator', '', true, true );

INSERT INTO project ( project_name, start_date, end_date, project_manager, short_desc )
   VALUES ( 'Primary Test', '2013-05-01', '2020-05-11', 1, 'Testing the first project' );
INSERT INTO project ( project_name, start_date, end_date, project_manager, short_desc )
   VALUES ( 'Second Project', '2011-08-11', '2023-05-16', 1, 'Another Test Project' );
INSERT INTO project ( project_name, start_date, end_date, project_manager, short_desc )
   VALUES ( 'Third Project', '2019-01-06', '2020-06-20', 1, 'Testing Projects' );
INSERT INTO project ( project_name, start_date, end_date, project_manager, short_desc )
   VALUES ( 'Fourth Project', '2015-03-21', '2020-12-25', 1, 'One more project under the sun' );
INSERT INTO project ( project_name, start_date, end_date, project_manager, short_desc )
   VALUES ( 'Bee Movie', '2007-11-02', '2007-11-02', 1, "According to all known laws of aviation, there is no way a bee should be able to fly.  Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don't care what humans think is impossible. Yellow, black. Yellow, black. Yellow, black. Yellow, black. Ooh, black and yellow! Let's shake it up a little. Barry! Breakfast is ready!");

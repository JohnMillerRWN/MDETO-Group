create database mdeto;
use mdeto;

CREATE TABLE IF NOT EXISTS sys_user (
	user_id INT UNIQUE NOT NULL,
    first_name VARCHAR(20),
    mid_name VARCHAR(20),
    last_name VARCHAR(20),
    full_name VARCHAR(60) GENERATED ALWAYS AS (CONCAT(first_name,' ', last_name)),
    active boolean, 
    title VARCHAR(40),
    project_manager boolean,
    estimator boolean
);

CREATE TABLE IF NOT EXISTS project (
    project_id INT NOT NULL,
    project_name VARCHAR(255) NOT NULL,
    start_date DATE,							# date format is YYYY-MM-DD
    end_date DATE,
    created_date DATE,
    project_manager INT,
    short_desc TEXT,
    PRIMARY KEY (project_id),
    FOREIGN KEY (project_manager) REFERENCES sys_user(user_id)
);

INSERT INTO sys_user ( user_id, first_name, last_name, active, project_manager )
   VALUES ( 1, 'Project', 'Manager', true, true );
INSERT INTO sys_user ( user_id, first_name, last_name, active, estimator )
   VALUES ( 2, 'Estimator', '', true, true );

INSERT INTO project ( project_id, project_name, start_date, end_date, created_date, project_manager, short_desc )
   VALUES ( 1, 'Primary Test', '2019-05-01', '2020-05-01', CURDATE(), 1, 'Testing the first project' );
INSERT INTO project ( project_id, project_name, start_date, end_date, created_date, project_manager, short_desc )
   VALUES ( 2, 'Second Project', '2019-05-01', '2020-05-01', CURDATE(), 1, 'Another Test Project' );
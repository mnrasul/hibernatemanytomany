CREATE TABLE employee (
    employee_id serial,
    firstname VARCHAR(50) NULL DEFAULT NULL,
    lastname VARCHAR(50) NULL DEFAULT NULL,
    PRIMARY KEY (employee_id)
);


CREATE TABLE meeting (
    meeting_id serial,
    subject VARCHAR(50) NOT NULL,
    meeting_date DATE NOT NULL,
    PRIMARY KEY (meeting_id)
);


CREATE TABLE employee_meeting (
    employee_id BIGINT NOT NULL,
    meeting_id BIGINT NOT NULL,
    PRIMARY KEY (employee_id, meeting_id),
    CONSTRAINT FK_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (employee_id),
    CONSTRAINT FK_MEETING FOREIGN KEY (meeting_id) REFERENCES meeting (meeting_id)
);
-- Assuming the "group" table and group_sequence have already been created as per your previous script

-- Create task table with group_id included
CREATE TABLE task (
                      id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                      short_desc VARCHAR(255) NOT NULL,
                      details TEXT,
                      start_date DATE NOT NULL,
                      end_date DATE,
                      group_id BIGINT, -- No need for a comma here
                      CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES "group" (id) -- Define the foreign key constraint directly here
);

-- Create sequence for task IDs (if needed, depending on your DBMS and ID generation strategy)
CREATE SEQUENCE task_sequence START WITH 1 INCREMENT BY 1;

-- Insert a sample record into the task table
INSERT INTO task (short_desc, details, start_date, end_date, group_id)
VALUES ('Test Task', 'This is a test task', '2021-01-01', '2021-01-01', 1);
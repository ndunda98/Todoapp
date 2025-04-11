-- 1. Create the database
CREATE DATABASE IF NOT EXISTS todo_app;

-- 2. Select the database
USE todo_app;

-- 3. Create the tasks table
CREATE TABLE IF NOT EXISTS tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    is_done BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Insert sample tasks
INSERT INTO tasks (description) VALUES 
('Finish Java project'),
('Read a book'),
('Buy groceries'),
('Clean the house');

-- 5. View all tasks
SELECT * FROM tasks;

-- 6. View only pending tasks
SELECT * FROM tasks WHERE is_done = FALSE;

-- 7. View only completed tasks
SELECT * FROM tasks WHERE is_done = TRUE;

-- 8. Mark task as done (example: task with ID 1)
UPDATE tasks SET is_done = TRUE WHERE id = 1;

-- 9. Update a task description (example: ID 2)
UPDATE tasks SET description = 'Read a novel' WHERE id = 2;

-- 10. Delete a task (example: ID 4)
DELETE FROM tasks WHERE id = 4;

-- 11. Reset the table (delete all tasks)
-- DELETE FROM tasks;
-- TRUNCATE TABLE tasks; -- Uncomment to reset auto-increment too

INSERT INTO employee(id, first_name, last_name, email, phone, department_id, delete_flag, creation_date) 
VALUES (nextval('employee_seq'), :firstName, :lastName, :email, :phone, :departmentId, FALSE, CURRENT_TIMESTAMP)
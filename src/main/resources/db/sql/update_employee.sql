UPDATE employee 
SET first_name = :firstName, last_name = :lastName, email = :email, phone = :phone, department_id = :departmentId, modification_date = CURRENT_TIMESTAMP 
WHERE id = :employeeId AND delete_flag = FALSE
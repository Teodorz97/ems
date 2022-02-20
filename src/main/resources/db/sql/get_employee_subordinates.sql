SELECT id, first_name, last_name, email, phone, department_id from employee e
JOIN employees_relation er
ON e.id = er.subordinate_id
WHERE er.superior_id = :employeeId
AND e.delete_flag = FALSE
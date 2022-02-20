SELECT id, first_name, last_name, email, phone, department_id from employee e
JOIN employees_relation er
ON e.id = er.superior_id
WHERE er.subordinate_id = :employeeId
AND e.delete_flag = FALSE
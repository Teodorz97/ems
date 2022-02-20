WITH RECURSIVE superiors AS (
        SELECT e.id FROM employee e
        JOIN employees_relation er
        ON e.id = er.superior_id
        WHERE er.subordinate_id = :superiorEmployeeId
    UNION
        SELECT e.id FROM employee e
        JOIN employees_relation er
        ON e.id = er.superior_id
        INNER JOIN superiors s ON s.id = er.subordinate_id
) SELECT EXISTS (SELECT 1 FROM superiors WHERE id = :subordinateEmployeeId)
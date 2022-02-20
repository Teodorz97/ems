package com.example.ems.configuration

class SqlLoader {
    
    companion object{
        
        val GET_EMPLOYEES = getSQLStatement("get_employees.sql")
        val INSERT_EMPLOYEE = getSQLStatement("insert_employee.sql")
        val UPDATE_EMPLOYEE = getSQLStatement("update_employee.sql")
        val DELETE_EMPLOYEE = getSQLStatement("delete_employee.sql")
        val CHECK_DEPARTMENT_EXISTS = getSQLStatement("check_department_exists.sql")
        val GET_EMPLOYEE_SUPERIORS = getSQLStatement("get_employee_superiors.sql")
        val GET_EMPLOYEE_SUBORDINATES = getSQLStatement("get_employee_subordinates.sql")
        val INSERT_EMPLOYEE_RELATION = getSQLStatement("insert_employee_relation.sql")
        val GET_EXISTING_EMPLOYEES_ID = getSQLStatement("get_existing_employees_id.sql")
        val CHECK_FOR_CIRCULAR_RELATION = getSQLStatement("check_for_circular_relation.sql")

        private fun getSQLStatement(sqlFileName: String): String {
            val filePath = "/db/sql/$sqlFileName"
            return this::class.java.getResource(filePath).readText(Charsets.UTF_8)
        }
    }

    

    
    
}
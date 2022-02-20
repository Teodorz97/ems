package com.example.ems.employee

import com.example.ems.configuration.SqlLoader
import com.example.ems.model.Employee
import com.example.ems.model.EmployeeWithId
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class EmployeeRepository (val jdbcTemplate: NamedParameterJdbcTemplate){
    
    private val employeeRowMapper = { rs: ResultSet, _: Int ->
        EmployeeWithId(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getLong("department_id")
        )
    }
    
    fun insertEmployee(employee: Employee){
        this.jdbcTemplate.update(SqlLoader.INSERT_EMPLOYEE, getEmployeeParameters(employee))
    }
    
    fun updateEmployee(employeeId: Long, employee: Employee): Int{
        return this.jdbcTemplate.update(SqlLoader.UPDATE_EMPLOYEE, getEmployeeParameters(employee).addValue("employeeId", employeeId))
    }

    fun deleteEmployee(employeeId: Long): Int{
        return jdbcTemplate.update(SqlLoader.DELETE_EMPLOYEE, MapSqlParameterSource("employeeId", employeeId))
    }
    
    fun getEmployees(employeeId: Long? = null): List<EmployeeWithId>{
        var sql = SqlLoader.GET_EMPLOYEES
        val params = MapSqlParameterSource()
        if (employeeId != null){
            sql += " AND id = :employeeId"
            params.addValue("employeeId", employeeId)
        }
        return jdbcTemplate.query(sql, params, employeeRowMapper)
    }
    
    fun getAllSuperiorsOfAnEmployee(employeeId: Long): List<EmployeeWithId>{
        return jdbcTemplate.query(SqlLoader.GET_EMPLOYEE_SUPERIORS, MapSqlParameterSource("employeeId", employeeId), employeeRowMapper)
    }
    
    fun getAllSubordinatesOfAnEmployee(employeeId: Long): List<EmployeeWithId>{
        return jdbcTemplate.query(SqlLoader.GET_EMPLOYEE_SUBORDINATES, MapSqlParameterSource("employeeId", employeeId), employeeRowMapper)
    }
    
    fun insertEmployeeRelation(superiorEmployeeId: Long, subordinateEmployeeId: Long){
        jdbcTemplate.update(SqlLoader.INSERT_EMPLOYEE_RELATION, getEmployeeRelationParameters(superiorEmployeeId, subordinateEmployeeId))
    }
    
    fun getExistingEmployeesId(superiorEmployeeId: Long, subordinateEmployeeId: Long): List<Long>{
        return jdbcTemplate.query(SqlLoader.GET_EXISTING_EMPLOYEES_ID, getEmployeeRelationParameters(superiorEmployeeId, subordinateEmployeeId)){ rs: ResultSet, _: Int ->
            rs.getLong("id")
        }
    }
    
    fun checkForCircularRelation(superiorEmployeeId: Long, subordinateEmployeeId: Long): Boolean{
        return jdbcTemplate.query(SqlLoader.CHECK_FOR_CIRCULAR_RELATION, getEmployeeRelationParameters(superiorEmployeeId, subordinateEmployeeId)){ rs, _ ->
            rs.getBoolean("exists")
        }.first()
    }

    private fun getEmployeeParameters(employee: Employee): MapSqlParameterSource{
        return MapSqlParameterSource().addValue("firstName", employee.firstName)
            .addValue("lastName", employee.lastName)
            .addValue("email", employee.email)
            .addValue("phone", employee.phone)
            .addValue("departmentId", employee.departmentId)
    }

    private fun getEmployeeRelationParameters(superiorEmployeeId: Long, subordinateEmployeeId: Long): MapSqlParameterSource{
        return MapSqlParameterSource().addValue("superiorEmployeeId", superiorEmployeeId)
            .addValue("subordinateEmployeeId", subordinateEmployeeId)
    }
}
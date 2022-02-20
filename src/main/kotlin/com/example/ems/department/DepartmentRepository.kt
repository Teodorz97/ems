package com.example.ems.department

import com.example.ems.configuration.SqlLoader
import com.example.ems.exceptions.InvalidRequestBodyException
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.invoke.MethodHandles
import kotlin.jvm.Throws

@Repository
class DepartmentRepository (val jdbcTemplate: NamedParameterJdbcTemplate){
    
    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    @Throws(InvalidRequestBodyException::class)
    fun checkDepartmentExists(departmentId: Long){
        if(!jdbcTemplate.query(SqlLoader.CHECK_DEPARTMENT_EXISTS, 
                MapSqlParameterSource("departmentId", departmentId)) { rs, _ -> rs.getBoolean("exists")}.first()){
            logger.warn("Department with id {} does not exist", departmentId)
            throw InvalidRequestBodyException("Department with id $departmentId does not exist")
        }
    }
 
}
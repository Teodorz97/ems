package com.example.ems.employee

import com.example.ems.department.DepartmentRepository
import com.example.ems.exceptions.InvalidRequestBodyException
import com.example.ems.model.Employee
import com.example.ems.model.EmployeeWithId
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import java.lang.invoke.MethodHandles

interface EmployeeService{
    fun saveEmployee(employee: Employee)
    fun updateEmployee(employeeId: Long, employee: Employee)
    fun deleteEmployee(employeeId: Long)
    fun getEmployee(employeeId: Long): EmployeeWithId
    fun getAllEmployees(): List<EmployeeWithId>
    fun getAllSuperiorsOfAnEmployee(employeeId: Long): List<EmployeeWithId>
    fun getAllSubordinatesOfAnEmployee(employeeId: Long): List<EmployeeWithId>
    fun insertEmployeeRelation(superiorEmployeeId: Long, subordinateEmployeeId: Long)
}

@Service
class EmployeeServiceImpl(
    val employeeRepository: EmployeeRepository,
    val departmentRepository: DepartmentRepository) : EmployeeService{
    
    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    @Throws(InvalidRequestBodyException::class)
    override fun saveEmployee(employee: Employee) {
        logger.info("Saving employee {}", employee)
        departmentRepository.checkDepartmentExists(employee.departmentId)
        try {
            employeeRepository.insertEmployee(employee)
            logger.info("Employee successfully created")
        } catch (e: DuplicateKeyException) {
            if(e.message!!.contains(employee.email)){
                logger.warn("Unable to save {} email {} already exists", employee, employee.email)
                throw InvalidRequestBodyException("Employee with email ${employee.email} already exists")
            }
            if (e.message!!.contains(employee.phone)){
                logger.warn("Unable to save {} phone {} already exists", employee, employee.phone)
                throw InvalidRequestBodyException("Employee with phone ${employee.phone} already exists")
            }
        }
    }

    @Throws(InvalidRequestBodyException::class)
    override fun updateEmployee(employeeId: Long, employee: Employee) {
        logger.info("Updating employee with id {}", employeeId)
        departmentRepository.checkDepartmentExists(employee.departmentId)
        try {
            if(employeeRepository.updateEmployee(employeeId, employee) == 0){
                logger.warn("Employee with id {} doesn't exist", employeeId)
                throw InvalidRequestBodyException("Employee with id $employeeId does not exist")
            }
            logger.info("Employee with id {} successfully updated", employeeId)
        } catch (e: DuplicateKeyException) {
            logger.warn("Unable to update {} {}", employee, e.message)
            if(e.message!!.contains(employee.email)){
                logger.warn("Unable to update {} email {} already exists", employee, employee.email)
                throw InvalidRequestBodyException("Employee with email ${employee.email} already exists")
            }
            if (e.message!!.contains(employee.phone)){
                logger.warn("Unable to update {} phone {} already exists", employee, employee.phone)
                throw InvalidRequestBodyException("Employee with phone ${employee.phone} already exists")
            }
        }
        
    }

    @Throws(InvalidRequestBodyException::class)
    override fun deleteEmployee(employeeId: Long) {
        logger.info("Deleting employee with id {}", employeeId)
        if(employeeRepository.deleteEmployee(employeeId) == 0){
            logger.warn("Employee with id {} doesn't exist", employeeId)
            throw InvalidRequestBodyException("Employee with id $employeeId doesn't exist")
        }
        logger.info("Employee with id {} successfully deleted", employeeId)
    }

    @Throws(InvalidRequestBodyException::class)
    override fun getEmployee(employeeId: Long): EmployeeWithId {
        logger.info("Getting employee with id {}", employeeId)
        val employee = employeeRepository.getEmployees(employeeId)
        if (employee.isNotEmpty()){
            return employee.first()
        } else{
            logger.warn("Employee with id {} doesn't exist", employeeId)
            throw InvalidRequestBodyException("Employee with id $employeeId doesn't exist")
        }
    }

    override fun getAllEmployees(): List<EmployeeWithId> {
        logger.info("Getting all employees")
        return employeeRepository.getEmployees()
    }

    override fun getAllSuperiorsOfAnEmployee(employeeId: Long): List<EmployeeWithId> {
        logger.info("Getting superiors for employee with id {}", employeeId)
        return employeeRepository.getAllSuperiorsOfAnEmployee(employeeId)
    }

    override fun getAllSubordinatesOfAnEmployee(employeeId: Long): List<EmployeeWithId> {
        logger.info("Getting subordinates for employee with id {}", employeeId)
        return employeeRepository.getAllSubordinatesOfAnEmployee(employeeId)
    }

    @Throws(InvalidRequestBodyException::class)
    override fun insertEmployeeRelation(superiorEmployeeId: Long, subordinateEmployeeId: Long) {
        logger.info("Inserting employee relation, superior id {}, subordinate id {}", superiorEmployeeId, subordinateEmployeeId)
        if(superiorEmployeeId == subordinateEmployeeId){
            logger.warn("Self relation not allowed")
            throw InvalidRequestBodyException("Invalid relation, self relations are not allowed")
        }
        val ids = employeeRepository.getExistingEmployeesId(superiorEmployeeId, subordinateEmployeeId)
        if(ids.isEmpty()){
            logger.warn("Superior employee with id {} and subordinate employee with id {} doesn't exist", superiorEmployeeId, subordinateEmployeeId)
            throw InvalidRequestBodyException("Superior employee with id $superiorEmployeeId and subordinate employee with id $subordinateEmployeeId doesn't exist")
        }
        if(ids.size == 1){
            if(ids.contains(superiorEmployeeId)){
                logger.warn("Subordinate employee with id {} doesn't exist", subordinateEmployeeId)
                throw InvalidRequestBodyException("Subordinate employee with id $subordinateEmployeeId doesn't exist")
            }else{
                logger.warn("Superior employee with id {} doesn't exist", superiorEmployeeId)
                throw InvalidRequestBodyException("Superior employee with id $superiorEmployeeId doesn't exist")
            }
        }
        if(employeeRepository.checkForCircularRelation(superiorEmployeeId, subordinateEmployeeId)){
            logger.warn("Employee {} can't be superior to {}. Circular relation detected", superiorEmployeeId, subordinateEmployeeId)
            throw InvalidRequestBodyException("Employee $superiorEmployeeId can't be superior to $subordinateEmployeeId. Circular relation detected")
        }

        try {
            employeeRepository.insertEmployeeRelation(superiorEmployeeId, subordinateEmployeeId)
        } catch (e: DuplicateKeyException) {
            logger.warn("Relation already exists", superiorEmployeeId, subordinateEmployeeId)
            throw InvalidRequestBodyException("Relation already exists")
        }

    }

}
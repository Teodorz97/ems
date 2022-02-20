package com.example.ems.employee

import com.example.ems.exceptions.InvalidRequestBodyException
import com.example.ems.model.Employee
import com.example.ems.model.EmployeeWithId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("/employee")
class EmployeeController(val employeeService: EmployeeService) {

    @PostMapping("/save")
    fun insertEmployee(@Valid @RequestBody employee: Employee): ResponseEntity<String> {
        return try {
            employeeService.saveEmployee(employee)
            ResponseEntity.ok("Employee created")
        } catch (e: InvalidRequestBodyException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        } 
    }
    
    @PostMapping("/update/{employeeId}")
    fun updateEmployee(@PathVariable("employeeId") employeeId: Long, @Valid @RequestBody employee: Employee): ResponseEntity<String>{
        return try {
            employeeService.updateEmployee(employeeId, employee)
            ResponseEntity.ok("Employee updated")
        } catch (e: InvalidRequestBodyException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @DeleteMapping("/delete/{employeeId}")
    fun deleteEmployeeById(@PathVariable employeeId: Long): ResponseEntity<String> {
        return try {
            employeeService.deleteEmployee(employeeId)
            ResponseEntity.ok("Employee deleted")
        } catch (e: InvalidRequestBodyException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
    
    @GetMapping("/{employeeId}")
    fun getEmployee(@PathVariable employeeId: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(employeeService.getEmployee(employeeId))
        } catch (e: InvalidRequestBodyException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
    
    @GetMapping("/list")
    fun getAllEmployees(): ResponseEntity<List<EmployeeWithId>> {
        return ResponseEntity.ok(employeeService.getAllEmployees())
    }

    @GetMapping("/list-superiors/{employeeId}")
    fun getEmployeeSuperiors(@PathVariable employeeId: Long): ResponseEntity<List<EmployeeWithId>> {
        return ResponseEntity.ok(employeeService.getAllSuperiorsOfAnEmployee(employeeId))
    }

    @GetMapping("/list-subordinates/{employeeId}")
    fun getEmployeeSubordinates(@PathVariable employeeId: Long): ResponseEntity<List<EmployeeWithId>> {
        return ResponseEntity.ok(employeeService.getAllSubordinatesOfAnEmployee(employeeId))
    }

    @PostMapping("/save-relation/{superiorEmployeeId}/{subordinateEmployeeId}")
    fun insertEmployeeRelation(@PathVariable superiorEmployeeId: Long, @PathVariable subordinateEmployeeId: Long): ResponseEntity<String> {
        return try {
            employeeService.insertEmployeeRelation(superiorEmployeeId, subordinateEmployeeId)
            ResponseEntity.ok("Employee relation created")
        } catch (e: InvalidRequestBodyException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
    
    
}
package com.example.ems.model
import javax.validation.constraints.Email
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

open class Employee(
    @field:Size(max = 50, message = "First name has exceeded maximum character limit of 50")
    @field:Pattern(regexp = "^[a-zA-Z]+\$", message = "Invalid first name format")
    open val firstName: String,
    @field:Size(max = 50, message = "Last name has exceeded maximum character limit of 50")
    @field:Pattern(regexp = "^[a-zA-Z]+\$", message = "Invalid last name format")
    open val lastName: String,
    @field:Email(message = "Invalid email format")
    open val email: String,
    @field:Pattern(regexp = "(^\\+?[0-9]{7,15})", message = "Invalid phone number format")
    open val phone: String,
    open val departmentId: Long
)

data class EmployeeWithId(
    val id: Long, 
    override val firstName: String, 
    override val lastName: String, 
    override val email: String, 
    override val phone: String, 
    override val departmentId: Long
) : Employee(firstName, lastName, email, phone, departmentId)

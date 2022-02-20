package com.example.ems.configuration.web

import org.slf4j.LoggerFactory
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.invoke.MethodHandles
import java.util.stream.Collectors


@ControllerAdvice
class GlobalExceptionHandler {
    
    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleInvalidInputData(ex: MethodArgumentNotValidException): ResponseEntity<MutableMap<String, List<String?>>> {
        logger.warn("Invalid input data. {}", ex.message)

        val body: MutableMap<String, List<String?>> = HashMap()

        val errors: List<String?> = ex.bindingResult
            .fieldErrors
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList())

        body["errors"] = errors
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

}
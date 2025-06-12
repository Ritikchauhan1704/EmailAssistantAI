package com.email.writer.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.email.writer.entity.EmailRequest
@RestController
@RequestMapping("api/email")
class EmailGeneratorController {

    @PostMapping
    fun generateEmail(@RequestBody emailRequest: EmailRequest): ResponseEntity<String> {
        return ResponseEntity.ok("");
    }
}
package com.hsfl.springbreak.backend.demo

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000")
@RestController
class DemoController(val repository: DemoDataRepository) {
    @GetMapping("/")
    fun index(): String = "Hello World!"

    @GetMapping("user")
    fun insertUser(demoData: DemoData): DemoData = repository.save(demoData)

    @GetMapping("user/all")
    fun getAllData(): List<DemoData> = repository.findAll().toList()
}
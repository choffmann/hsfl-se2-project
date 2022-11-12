package com.hsfl.springbreak.spring_break.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController(val repository: DemoDataRepository) {
    @GetMapping("/")
    fun index(): String = "Hello World!"

    @GetMapping("user")
    fun insertUser(demoData: DemoData): DemoData = repository.save(demoData)

    @GetMapping("user/all")
    fun getAllData(): List<DemoData> = repository.findAll().toList()
}
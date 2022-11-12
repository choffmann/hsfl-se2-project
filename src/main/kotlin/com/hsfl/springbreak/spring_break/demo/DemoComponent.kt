package com.hsfl.springbreak.spring_break.demo

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DemoComponent(val repository: DemoDataRepository): ApplicationRunner {
    // Save DemoDate in database on init
    override fun run(args: ApplicationArguments?) {
        listOf(
            DemoData(firstName = "Hans", lastName = "Müller"),
            DemoData(firstName = "Dimi", lastName = "Dorni"),
            DemoData(firstName = "Hektor", lastName = "Panzer"),
            DemoData(firstName = "Arnold", lastName = "Schwarzenegger"),
        ).forEach { data ->
            repository.save(data)
        }
    }
}
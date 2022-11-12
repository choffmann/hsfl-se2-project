package com.hsfl.springbreak.spring_break.demo

import org.springframework.stereotype.Component

@Component
class DemoComponent(val repository: DemoDataRepository) {
    // Save DemoDate in database on init
    init {
        listOf(
            DemoData(firstName = "Hans", lastName = "Müller"),
            DemoData(firstName = "Dimi", lastName = "Dorni"),
            DemoData(firstName = "Hektor", lastName = "Panzer"),
        ).forEach { data ->
            repository.save(data)
        }
    }
}
package com.hsfl.springbreak.spring_break.demo

import org.springframework.stereotype.Component

@Component
class DemoComponent(val repository: DemoDataRepository) {

    // Save DemoDate in database on inti
    init {
        listOf(
            DemoDataDao(firstName = "Hans", lastName = "Müller"),
            DemoDataDao(firstName = "Dimi", lastName = "Dorni"),
            DemoDataDao(firstName = "Hektor", lastName = "Panzer"),
        ).forEach { data ->
            repository.save(data.toEntry())
        }
    }
}
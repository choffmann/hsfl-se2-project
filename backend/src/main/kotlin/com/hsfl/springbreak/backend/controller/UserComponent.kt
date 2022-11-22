package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.data.UserEntity
import com.hsfl.springbreak.backend.repository.UserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class UserComponent(private val repository: UserRepository): ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        repository.save(
            UserEntity(firstName = "Root", lastName = "Administrator", email = "root@admin.com", password = "geheim")
        )
    }
}
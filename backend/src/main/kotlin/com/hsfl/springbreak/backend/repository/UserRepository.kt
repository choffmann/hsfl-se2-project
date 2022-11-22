package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.data.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}
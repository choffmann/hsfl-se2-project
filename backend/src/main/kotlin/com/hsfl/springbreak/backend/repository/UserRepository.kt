package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.UserEntity
import com.hsfl.springbreak.backend.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?

}
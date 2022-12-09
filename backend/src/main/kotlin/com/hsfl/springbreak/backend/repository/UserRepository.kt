package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

@Transactional(Transactional.TxType.MANDATORY)
interface UserRepository : CrudRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}
package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.IngredientEntity
import com.hsfl.springbreak.backend.entity.UserEntity
import org.springframework.data.repository.CrudRepository

interface IngredientRepository: CrudRepository<IngredientEntity, Long> {
    fun findByName(name: String): IngredientEntity?
}
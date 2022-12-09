package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.RecipeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository : JpaRepository<RecipeEntity, Long> {
    fun findRecipeByTitle(name: String): RecipeEntity?
}
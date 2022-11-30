package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.RecipeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface RecipeRepository: JpaRepository<RecipeEntity, Long> {
    //fun findRecipeById(Id: Long): RecipeEntity?
}
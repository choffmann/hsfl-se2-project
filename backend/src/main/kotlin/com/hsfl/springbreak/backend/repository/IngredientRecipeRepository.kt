package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import org.springframework.data.repository.CrudRepository

interface IngredientRecipeRepository: CrudRepository<IngredientRecipeEntity, Long> {
}
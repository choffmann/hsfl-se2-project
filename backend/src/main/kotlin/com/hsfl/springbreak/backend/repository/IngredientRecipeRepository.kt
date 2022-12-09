package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import com.hsfl.springbreak.backend.entity.RecipeEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface IngredientRecipeRepository: CrudRepository<IngredientRecipeEntity, Long> {
    //fun deleteAllByRecipeId(recipeId: Long): Unit
    fun deleteByRecipe(recipe: RecipeEntity): Unit
}
package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.exceptions.RecipeNotFoundException
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.repository.RecipeRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class RecipeService(private val repository: RecipeRepository) {

    fun getRecipeById(recipeId: Long): RecipeEntity? = repository.findById(recipeId).get()

    fun createNewRecipe(recipe: Recipe): RecipeEntity? = repository.save(RecipeEntity.fromDto(recipe))

    fun updateRecipe(recipeId: Long, recipe: Recipe): RecipeEntity? {
        return if (repository.existsById(recipeId)) {
            repository.save(RecipeEntity.fromDto(recipe))
        } else throw  Exception("Recipe not in database")
    }

    fun deleteRecipeById(recipeId: Long) {
        return if (repository.existsById(recipeId)) {
            repository.deleteById(recipeId)
        } else throw Exception("Recipe not in database")
    }
}
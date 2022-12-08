package com.hsfl.springbreak.backend.service

import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.IngredientRecipeId
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.repository.IngredientRecipeRepository
import com.hsfl.springbreak.backend.repository.RecipeRepository
import com.hsfl.springbreak.backend.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class RecipeJpaService(private val recipeRepository: RecipeRepository, val userRepository: UserRepository,
                       val ingredientRepository: IngredientRecipeRepository) {

    fun getRecipeById(recipeId: Long): ApiResponse<Recipe> {
        val searchedRecipe = recipeRepository.findById(recipeId).orElse(null)
        return if (searchedRecipe != null) {
            ApiResponse(data = searchedRecipe.toDto(), success = true)
        } else {
            ApiResponse(error = "No such recipe", success = false)
        }
    }

    fun getRecipeByName(recipeName: String): ApiResponse<Recipe> {
        val searchedRecipe = recipeRepository.findRecipeByTitle(recipeName)
        return if (searchedRecipe != null) {
            ApiResponse(data = searchedRecipe.toDto(), success = true)
        } else{
            ApiResponse(error = "No such recipe name", success = false)
        }
    }

    fun createNewRecipe(newRecipe: Recipe.CreateRecipe, userId: Long): ApiResponse<Recipe> {
        //TODO: Kann beim Request die ID angebeben werden?
        val user = userRepository.findById(userId).get()
        val savedRecipe = recipeRepository.save(RecipeEntity.fromDto(newRecipe, user))
        val ingredientDtos = newRecipe.ingredients.map { IngredientRecipe(
            id = IngredientRecipeId( recipeId = savedRecipe.id!!, ingredientId = it.ingredient.id),
            recipe = savedRecipe.toDto(),
            ingredient = it.ingredient,
            unit = it.unit,
            amount = it.amount
        )}
        ingredientDtos.map { ingredientRepository.save(IngredientRecipeEntity.fromDto(it)) }
        return ApiResponse(data = savedRecipe.toDto(), success = true)
    }

    fun updateRecipe(changes: Recipe.ChangeRecipe, recipeId: Long): ApiResponse<Recipe> {
        val searchedRecipe = recipeRepository.findById(recipeId).orElse(null)
        return if (searchedRecipe != null) {
            val savedRecipe = recipeRepository.save(RecipeEntity.fromDto(changes, searchedRecipe))
            ApiResponse(data = savedRecipe.toDto(), success = true)
        } else {
            ApiResponse(error = "Update failed", success = false)
        }
    }

    fun deleteRecipeById(recipeId: Long) {
        return if (recipeRepository.existsById(recipeId)) {
            recipeRepository.deleteById(recipeId)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with ID \"$recipeId\" doesn't exist")
    }
}
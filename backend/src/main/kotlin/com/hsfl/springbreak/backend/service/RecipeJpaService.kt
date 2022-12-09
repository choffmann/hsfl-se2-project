package com.hsfl.springbreak.backend.service

import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import com.hsfl.springbreak.backend.entity.IngredientRecipeKey
import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.IngredientRecipeId
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.repository.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class RecipeJpaService(private val recipeRepository: RecipeRepository,
                       val userRepository: UserRepository,
                       val ingredientRecipeRepository: IngredientRecipeRepository,
                       val ingredientRepo: IngredientRepository,
                       val categoryRepository: CategoryRepository,
                       val difficultyRepository: DifficultyRepository) {


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

    fun createNewRecipe(newRecipe: Recipe.CreateRecipe): ApiResponse<Recipe> {
        val user = userRepository.findById(newRecipe.creatorId).get()
        val category = categoryRepository.findById(newRecipe.creatorId).get()
        val difficulty = difficultyRepository.findById(newRecipe.difficultyId).get()
        val savedRecipe = recipeRepository.save(RecipeEntity.fromDto(newRecipe, user, category, difficulty))

        val selectedIngredients = newRecipe.ingredients.map { currentIngredient ->
            ingredientRepo.findByName(currentIngredient.ingredientName)?.toDto()?.let {
                IngredientRecipe(
                    id = IngredientRecipeId(recipeId = savedRecipe.id!!, ingredientId = it.id),
                    recipe = savedRecipe.toDto(), ingredient = it, unit = currentIngredient.unit,
                    amount = currentIngredient.amount
                )
            } ?: return ApiResponse(error = "No such Ingredient: \"${currentIngredient.ingredientName}\"")
        }
        savedRecipe.ingredients = selectedIngredients.map { IngredientRecipeEntity.fromDto(it) }
        return ApiResponse(data = recipeRepository.save(savedRecipe).toDto(), success = true)
    }

    fun updateRecipe(changes: Recipe.ChangeRecipe, recipeId: Long): ApiResponse<Recipe> {
        val searchedRecipe = recipeRepository.findById(changes.ingredientId).orElse(null)
        return if (searchedRecipe != null) {
            val savedRecipe = recipeRepository.save(RecipeEntity.fromDto(changes, searchedRecipe))
            if (changes.ingredients != null) {
                val newIngredients = changes.ingredients.map {
                    ingredientRepo.findByName(it.ingredientName)?.let { foundIngredient ->
                        IngredientRecipeEntity(
                            id = IngredientRecipeKey(recipeId = searchedRecipe.id, ingredientId = foundIngredient.id),
                            recipe = searchedRecipe, ingredient = foundIngredient,
                            unit = it.ingredientName, amount = it.amount
                        )
                    } ?: return ApiResponse(error = "No such Ingredient: \"${it.ingredientName}\"")
                }
                searchedRecipe.ingredients = newIngredients
            }
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
package com.hsfl.springbreak.backend.service

import com.hsfl.springbreak.backend.entity.IngredientEntity
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
import javax.transaction.Transactional

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

    /**
     * Takes an CreateRecipe-Entity and fetches relevant data to save a new Recipe entity as well as corresponding ingredients to database.
     * @param recipe The recipe to be created.
     */
    fun createRecipe(recipe: Recipe.CreateRecipe): ApiResponse<Recipe> {
        // fetch information from database
        val user = userRepository.findById(recipe.creatorId).get()
        val category = categoryRepository.findById(recipe.categoryId).get()
        val difficulty = difficultyRepository.findById(recipe.difficultyId).get()

        // save new recipe to database
        val savedRecipe = recipeRepository.save(RecipeEntity.fromDto(recipe, user, category, difficulty))

        // check given ingredients for existence in database or create new
        val ingredients = saveIngredients(recipe.ingredients, savedRecipe)

        // fetch recipe-proxy and add ingredients
        val recipeProxy = recipeRepository.findById(savedRecipe.id!!).get()
        recipeProxy.ingredients = ingredients

        // save proxy to database
        recipeRepository.save(recipeProxy)

        return ApiResponse(success = true)
    }

    /**
     * @param changes The recipe to be updated.
     * TODO: Add method description
     */

    fun updateRecipe(changes: Recipe.ChangeRecipe): ApiResponse<Recipe> {
        // fetch recipe proxy
        val recipeProxy = recipeRepository.findById(changes.recipeId).orElse(null)
            ?: return ApiResponse("Recipe not found", success = false)

        // delete existing recipeIngredientEntities
        recipeProxy.ingredients?.let { ingredientRecipeRepository.deleteAll(it) }

        // ingredientRecipeRepository.deleteByRecipe(recipeProxy)
        /*
        for (test: IngredientRecipeEntity in recipeProxy.ingredients!!) {
            ingredientRecipeRepository.dele
        }


         */
        // update recipe values
        recipeProxy.title = changes.title
        recipeProxy.shortDescription = changes.shortDescription
        recipeProxy.price = changes.price
        recipeProxy.duration = changes.duration
        recipeProxy.difficulty = difficultyRepository.findById(changes.difficultyId).get()
        recipeProxy.category = categoryRepository.findById(changes.categoryId).get()
        recipeProxy.image = changes.image
        recipeProxy.longDescription = changes.longDescription
        recipeProxy.ingredients = saveIngredients(changes.ingredients, recipeProxy)

        // save changes to database
        recipeRepository.save(recipeProxy)

        return ApiResponse(success = true)
        /*
        val savedRecipe = recipeRepository.save(RecipeEntity.fromDto(changes, recipeProxy))

        if (changes.ingredients != null) {
            val ingredients = changes.ingredients.map { ingredient ->
                ingredientRepo.findByName(ingredient.ingredientName)
                    ?: ingredientRepo.save(IngredientEntity(name = ingredient.ingredientName))
                IngredientRecipeEntity(
                    id = IngredientRecipeKey(recipeId = recipeProxy.id, ingredientId = foundIngredient.id),
                    recipe = recipeProxy, ingredient = foundIngredient,
                    unit = it.ingredientName, amount = it.amount
                )
                ingredientRepo.findByName(it.ingredientName)?.let { foundIngredient ->
                    IngredientRecipeEntity(
                        id = IngredientRecipeKey(recipeId = recipeProxy.id, ingredientId = foundIngredient.id),
                        recipe = recipeProxy, ingredient = foundIngredient,
                        unit = it.ingredientName, amount = it.amount
                    )
                }
            }
            recipeProxy.ingredients = ingredients
        }


        return ApiResponse(data = savedRecipe.toDto(), success = true)

         */
    }

    fun deleteRecipeById(recipeId: Long) {
        return if (recipeRepository.existsById(recipeId)) {
            recipeRepository.deleteById(recipeId)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with ID \"$recipeId\" doesn't exist")
    }
}
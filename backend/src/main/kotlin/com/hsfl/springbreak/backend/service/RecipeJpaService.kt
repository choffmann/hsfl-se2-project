package com.hsfl.springbreak.backend.service

import com.hsfl.springbreak.backend.entity.IngredientEntity
import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import com.hsfl.springbreak.backend.entity.IngredientRecipeKey
import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.repository.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.transaction.Transactional

@Service
@Transactional
class RecipeJpaService(private val recipeRepository: RecipeRepository,
                       val userRepository: UserRepository,
                       val ingredientRecipeRepository: IngredientRecipeRepository,
                       val ingredientRepository: IngredientRepository,
                       val categoryRepository: CategoryRepository,
                       val difficultyRepository: DifficultyRepository) {


    /**
     * Return a recipe by its id
     * @param id The id of the recipe to be returned
     */
    fun getRecipeById(id: Long): ApiResponse<Recipe> {
        val recipe = recipeRepository.findById(id).orElse(null)
        return if (recipe != null)
            ApiResponse(data = recipe.toDto(), success = true)
        else
            ApiResponse(error = "No such recipe", success = false)
    }

    /**
     * Return a recipe by its name
     * @param name The id of the recipe to be returned
     */
    fun getRecipeByName(name: String): ApiResponse<Recipe> {
        val recipe = recipeRepository.findRecipeByTitle(name)
        return if (recipe != null)
            ApiResponse(data = recipe.toDto(), success = true)
        else
            ApiResponse(error = "No such recipe name", success = false)
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
     * TODO: Method description
     * @param changes The recipe to be updated.
     */

    fun updateRecipe(changes: Recipe.ChangeRecipe): ApiResponse<Recipe> {
        // fetch recipe proxy
        val recipeProxy = recipeRepository.findById(changes.recipeId).orElse(null)
            ?: return ApiResponse("Recipe not found", success = false)

        // delete existing recipeIngredientEntities
        ingredientRecipeRepository.deleteByRecipeId(recipeProxy.id!!)

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
    }

    /**
     * Delete a recipe from the database by its id.
     * @param id The id of the recipe to be deleted.
     */
    fun deleteRecipeById(id: Long) {
        return if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with ID \"$id\" doesn't exist")
    }

    /**
     * Saves a list of ingredients to database if necessary.
     * @param ingredients Referenced ingredients to be saved to database.
     * @param recipe The referenced recipe.
     */
    private fun saveIngredients(ingredients: List<IngredientRecipe.WithoutRecipe>, recipe: RecipeEntity): List<IngredientRecipeEntity> {
        return ingredients.map { ingredient ->
            val ingredientProxy = ingredientRepository.findByName(ingredient.ingredientName)
                ?: ingredientRepository.save(IngredientEntity(name = ingredient.ingredientName))

            IngredientRecipeEntity(
                id = IngredientRecipeKey(recipeId = recipe.id, ingredientId = ingredientProxy.id),
                recipe = recipe, ingredient = ingredientProxy, unit = ingredient.unit,
                amount = ingredient.amount
            )
        }
    }


    fun getFavoritesById(id: Long): ApiResponse<List<Recipe>> {
        return ApiResponse()
    }
}

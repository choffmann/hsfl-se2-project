package com.hsfl.springbreak.backend.service

import com.hsfl.springbreak.backend.entity.IngredientEntity
import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import com.hsfl.springbreak.backend.entity.IngredientRecipeKey
import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.repository.*
import org.springframework.stereotype.Service
import java.sql.Blob
import java.time.LocalDate
import javax.sql.rowset.serial.SerialBlob
import javax.transaction.Transactional

@Service
@Transactional
class RecipeJpaService(
    private val recipeRepository: RecipeRepository,
    val userRepository: UserRepository,
    val ingredientRecipeRepository: IngredientRecipeRepository,
    val ingredientRepository: IngredientRepository,
    val categoryRepository: CategoryRepository,
    val difficultyRepository: DifficultyRepository
) {

    /**
     * Return a recipe by its id.
     * @param id The id of the recipe to be returned.
     */
    fun getRecipeById(id: Long): ApiResponse<Recipe> {
        return if (recipeRepository.existsById(id)) {
            ApiResponse(data = recipeRepository.findById(id).get().toDto(), success = true)
        } else {
            ApiResponse(error = "No such recipe", success = false)
        }
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
    fun createRecipe(recipe: Recipe.CreateRecipe): ApiResponse<Recipe.Response> {
        // fetch information from database
        val user = userRepository.findById(recipe.creatorId).get()
        val category = categoryRepository.findById(recipe.categoryId).get()
        val difficulty = difficultyRepository.findById(recipe.difficultyId).get()
        val createTime = LocalDate.now()

        // save new recipe to database
        val savedRecipe = recipeRepository.save(RecipeEntity.fromDto(recipe, user, category, difficulty, createTime))

        // check given ingredients for existence in database or create new
        val ingredients = saveIngredients(recipe.ingredients, savedRecipe)

        // fetch recipe-proxy and add ingredients
        val recipeProxy = recipeRepository.findById(savedRecipe.id!!).get()
        recipeProxy.ingredients = ingredients

        return ApiResponse(data = recipeRepository.save(recipeProxy).toResponse(), success = true)
    }

    /**
     * Overwrites all given recipe values from a given recipe.
     * @param recipe The recipe to be updated from type Recipe.ChangeRecipe.
     */

    fun updateRecipe(recipe: Recipe.ChangeRecipe): ApiResponse<Recipe> {
        // fetch recipe proxy
        val recipeProxy = recipeRepository.findById(recipe.recipeId).orElse(null)
            ?: return ApiResponse("Recipe not found", success = false)

        // delete existing recipeIngredientEntities
        ingredientRecipeRepository.deleteByRecipeId(recipeProxy.id!!)

        // update recipe values
        recipeProxy.title = recipe.title
        recipeProxy.shortDescription = recipe.shortDescription
        recipeProxy.price = recipe.price
        recipeProxy.duration = recipe.duration
        recipeProxy.difficulty = difficultyRepository.findById(recipe.difficultyId).get()
        recipeProxy.category = categoryRepository.findById(recipe.categoryId).get()
        recipeProxy.longDescription = recipe.longDescription
        recipeProxy.ingredients = saveIngredients(recipe.ingredients, recipeProxy)

        // save changes to database
        recipeRepository.save(recipeProxy)

        return ApiResponse(success = true)
    }

    /**
     * Delete a recipe from the database by its id.
     * @param id The id of the recipe to be deleted.
     */
    fun deleteRecipeById(id: Long): ApiResponse<Recipe> {
        return if (recipeRepository.existsById(id)) {
            val proxy = recipeRepository.findById(id).get()
            recipeRepository.deleteById(id)
            ApiResponse(data = proxy.toDto(), success = true)
        } else {
            ApiResponse(error = "Element not found", success = false)
        }
    }

    /**
     * Saves a list of ingredients to database if necessary.
     * @param ingredients Referenced ingredients to be saved to database.
     * @param recipe The referenced recipe.
     */
    private fun saveIngredients(
        ingredients: List<IngredientRecipe.WithoutRecipe>,
        recipe: RecipeEntity
    ): List<IngredientRecipeEntity> {
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

    /**
     * Updates a recipes image and returns the recipe.
     * @param file The file to be saved as new image.
     * @param id The image of the corresponding recipe.
     */
    fun updateRecipeImage(file: ByteArray, id: Long): ApiResponse<Recipe> {
        return if (recipeRepository.existsById(id)) {
            // fetch recipe from database
            val recipeProxy = recipeRepository.findById(id).get()

            // convert file to blob
            val blob: Blob = SerialBlob(file)

            // save image to user
            recipeProxy.image = blob
            recipeRepository.save(recipeProxy)

            ApiResponse(data = recipeProxy.toDto(), success = true)
        } else {
            ApiResponse(success = false)
        }

    }
}

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
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class RecipeService(
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
    fun getRecipeById(id: Long): ApiResponse<Recipe.Response> {
        return if (recipeRepository.existsById(id)) {
            val recipeProxy = recipeRepository.findById(id).get()
            recipeProxy.views += 1
            ApiResponse(data = recipeProxy.toResponse(), success = true)
        } else {
            ApiResponse(error = "No recipe with id: $id", success = false)
        }
    }

    /**
     * Return a recipe by its name
     * @param name The id of the recipe to be returned
     */
    fun getRecipeByName(name: String): ApiResponse<Recipe.Response> {
        val recipe = recipeRepository.findRecipeByTitle(name)
        return if (recipe != null) ApiResponse(data = recipe.toResponse(), success = true)
        else ApiResponse(error = "No recipe with name: $name", success = false)
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
        val createTime = LocalDateTime.now()

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

    fun updateRecipe(recipe: Recipe.ChangeRecipe): ApiResponse<Recipe.Response> {
        // fetch recipe proxy
        val recipeProxy = recipeRepository.findById(recipe.recipeId).orElse(null) ?: return ApiResponse(
            "Recipe not found",
            success = false
        )

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
        return ApiResponse(data = recipeRepository.save(recipeProxy).toResponse(), success = true)
    }

    /**
     * Delete a recipe from the database by its id.
     * @param id The id of the recipe to be deleted.
     */
    fun deleteRecipeById(id: Long): ApiResponse<Recipe.Response> {
        return if (recipeRepository.existsById(id)) {
            val proxy = recipeRepository.findById(id).get()
            recipeRepository.deleteById(id)
            ApiResponse(data = proxy.toResponse(), success = true)
        } else {
            ApiResponse(error = "No recipe with id: $id", success = false)
        }
    }

    /**
     * Saves a list of ingredients to database if necessary.
     * @param ingredients Referenced ingredients to be saved to database.
     * @param recipe The referenced recipe.
     */
    private fun saveIngredients(
        ingredients: List<IngredientRecipe.WithoutRecipe>, recipe: RecipeEntity
    ): List<IngredientRecipeEntity> {
        return ingredients.map { ingredient ->
            val ingredientProxy = ingredientRepository.findByName(ingredient.ingredientName)
                ?: ingredientRepository.save(IngredientEntity(name = ingredient.ingredientName))

            IngredientRecipeEntity(
                id = IngredientRecipeKey(recipeId = recipe.id, ingredientId = ingredientProxy.id),
                recipe = recipe,
                ingredient = ingredientProxy,
                unit = ingredient.unit,
                amount = ingredient.amount
            )
        }
    }

    /**
     * Saves a recipe image to server and return the URL to the image.
     * @param file The image to be saved to database.
     * @param id The user id corresponding to the uploaded file.
     */
    fun setImage(id: Long, file: MultipartFile): ApiResponse<String> {
        return if (recipeRepository.existsById(id)) {

            // Creating the path where the image should be saved
            val filePath = Paths.get("").toAbsolutePath().toString() + "/backend/src/main/resources/recipe/$id"

            // Save the image to the userprofile folder
            file.transferTo(File(filePath))

            // Get the user entity and safe the local path in the image attribute
            val recipe = recipeRepository.findById(id).get()
            recipe.image = filePath
            recipeRepository.save(recipe)

            // Return the URL Path where the image can be fetched
            ApiResponse(data = "http://localhost:8080/api/recipe/image/$id.png", success = true)
        } else {
            ApiResponse(error = "Invalid recipe ID", success = false)
        }
    }

    /**
     * Returns a ByteArray o
     * @param id The user's id whose profile image shall be returned.
     */
    fun getImageById(id: Long): ResponseEntity<ByteArray>? {
        return if (recipeRepository.existsById(id)) {
            val recipeProxy = recipeRepository.findById(id).get()
            if(recipeProxy.image != null) {
                val byteArray = Files.readAllBytes(File(recipeProxy.image!!).toPath())
                ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("recipe/png")).body(byteArray)
            } else {
                ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("recipe/png")).body(null)
            }
        } else {
            ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("recipe/png")).body(null)
        }
    }

    /**
     * Return a list of all recipes.
     */
    fun getRecipes(): ApiResponse<List<Recipe.Response>> {
        return ApiResponse(data = recipeRepository.findAll().map { it.toResponse() }, success = true)
    }

    /**
     * Return a list of all recipes sorted by score.
     */
    fun getRecipesByPopularity(): ApiResponse<List<Recipe.Response?>> {
        return ApiResponse(data = recipeRepository.findByOrderByScoreDesc().map { it?.toResponse() }, success = true)
    }

    /**
     * Return a list of all recipes created by a given user.
     * @param id The user's id whose recipes shall be returned.
     */
    fun getRecipesByCreator(id: Long): ApiResponse<List<Recipe.Response?>> {
        return ApiResponse(
            data = recipeRepository.findRecipeEntitiesByCreator_Id(id).map { it?.toResponse() }, success = true
        )
    }
}

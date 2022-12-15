package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.service.RecipeService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import javax.transaction.Transactional

/**
 * Calls recipe-related functions from recipe-service.
 */
@CrossOrigin("http://localhost:3000")
@Transactional
@RestController
class RecipeController(val recipeService: RecipeService) {

    /**
     * API-Endpoint for getting a recipe by its id.
     * @param rId The recipe's ID from the request parameter
     * @return API-Response with the Recipe-DTO or an error
     */
    @GetMapping("api/recipes")
    fun getRecipeById(@RequestParam("rId") rId: Long): ApiResponse<Recipe.Response> = recipeService.getRecipeById(rId)

    /*
    @GetMapping("api/recipes", params = ["name"])
    fun getRecipeByName(@RequestParam("name") name: String): ApiResponse<Recipe.Response> =
        recipeService.getRecipeByName(name)

     */

    /**
     * API-Endpoint for getting all stored recipes.
     * @return API-Response with a list of Recipe-DTOs or an error
     */
    @GetMapping("api/recipes", params = ["all"])
    fun getRecipes(): ApiResponse<List<Recipe.Response>> = recipeService.getRecipes()

    /**
     * API-Endpoint for getting the best rated recipes.
     * @return API-Response with a list of recipe-DTOs or an error
     */
    @GetMapping("api/recipes/popularity")
    fun getRecipesByPopularity(): ApiResponse<List<Recipe.Response?>> = recipeService.getRecipesByPopularity()

    /**
     * API-Endpoint for getting a recipe by the creator's id.
     * @param cId The creator's ID from the request parameter
     * @return API-Response with the Recipe-DTO or an error
     */
    @GetMapping("api/recipes/creator")
    fun getRecipesByCreator(@RequestParam("cId") cId: Long): ApiResponse<List<Recipe.Response?>> =
        recipeService.getRecipesByCreator(cId)

    /**
     * API-Endpoint for creating a new recipe.
     * @param recipe The Recipe-DTO from the request body.
     * @return API-Response with the saved Recipe-DTO or an error
     */
    @PostMapping("api/recipes")
    fun createRecipe(@RequestBody recipe: Recipe.CreateRecipe): ApiResponse<Recipe.Response> =
        recipeService.createRecipe(recipe)

    /**
     * API-Endpoint for updating a recipe.
     * @param changes The Recipe-DTO with new changes from the request body
     * @return API-Response with the saved Recipe-DTO or an error
     */
    @PutMapping("api/recipes")
    fun updateRecipe(@RequestBody changes: Recipe.ChangeRecipe): ApiResponse<Recipe.Response> =
        recipeService.updateRecipe(changes)

    //TODO: Umbauen
    @PostMapping("upload-profile-image")
    fun uploadProfileImage(@RequestParam("image") file: MultipartFile, @RequestParam("id") id: Long): ApiResponse<String> {
        println(file.originalFilename)
        val newPath = "/temp/save-profile-image/$id"
        File(newPath).writeBytes(file.inputStream.readAllBytes())
        return ApiResponse(data = "http://localhost:8080/profile-image/1")
    }

    //TODO: Umbauen
    @PutMapping("api/recipes/image")
    fun setImage(@RequestParam("image") file: MultipartFile, @RequestParam id: Long): ApiResponse<Recipe.Response> =
        recipeService.updateRecipeImage(file.bytes, id)

    /**
     * API-Endpoint for deleting a recipe by its ID.
     * @param rId The recipe's ID from the request parameter
     * @return API-Response with the deleted Recipe-DTO or an error
     */
    @DeleteMapping("api/recipes")
    fun deleteRecipe(@RequestParam("rId") rId: Long): ApiResponse<Recipe.Response> = recipeService.deleteRecipeById(rId)

}
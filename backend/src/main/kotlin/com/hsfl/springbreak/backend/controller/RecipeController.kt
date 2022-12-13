package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.service.RecipeService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import javax.transaction.Transactional

/**
 * Call recipe-related functions from service.
 */
@CrossOrigin("http://localhost:3000")
@Transactional
@RestController
class RecipeController(val recipeService: RecipeService) {

    @GetMapping("api/recipes", params = ["id"])
    fun getRecipeById(@RequestParam("id") id: Long): ApiResponse<Recipe.Response> = recipeService.getRecipeById(id)

    @GetMapping("api/recipes", params = ["name"])
    fun getRecipeByName(@RequestParam("name") name: String): ApiResponse<Recipe.Response> =
        recipeService.getRecipeByName(name)

    @GetMapping("api/recipes")
    fun getRecipes(): ApiResponse<List<Recipe.Response>> = recipeService.getRecipes()

    @GetMapping("api/recipes/popularity")
    fun getRecipesByPopularity(): ApiResponse<List<Recipe.Response?>> = recipeService.getRecipesByPopularity()

    @GetMapping("api/recipes/creator")
    fun getRecipesByCreator(@RequestParam("id") id: Long): ApiResponse<List<Recipe.Response?>> =
        recipeService.getRecipesByCreator(id)

    @PostMapping("api/recipes")
    fun createRecipe(@RequestBody recipe: Recipe.CreateRecipe): ApiResponse<Recipe.Response> =
        recipeService.createRecipe(recipe)

    @PutMapping("api/recipes")
    fun updateRecipe(@RequestBody changes: Recipe.ChangeRecipe): ApiResponse<Recipe.Response> =
        recipeService.updateRecipe(changes)

    @PutMapping("api/recipes/image")
    fun setImage(@RequestParam("image") file: MultipartFile, @RequestParam id: Long): ApiResponse<Recipe.Response> =
        recipeService.updateRecipeImage(file.bytes, id)

    @DeleteMapping("api/recipes")
    fun deleteRecipe(@RequestParam("id") id: Long): ApiResponse<Recipe.Response> = recipeService.deleteRecipeById(id)

}
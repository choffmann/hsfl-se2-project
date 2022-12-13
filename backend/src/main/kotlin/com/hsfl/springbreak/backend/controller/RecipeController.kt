package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.service.RecipeService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

/**
 * Call recipe-related functions from service.
 */
@CrossOrigin("http://localhost:3000")
@Transactional
@RestController
class RecipeController(val recipeService: RecipeService) {

    @GetMapping("api/recipes?user={id}")
    fun getRecipeById(@PathVariable("id") id: Long): ApiResponse<Recipe.Response> =
        recipeService.getRecipeById(id)

    @GetMapping("api/recipes?name={name}")
    fun getRecipeByName(@PathVariable("name") name: String): ApiResponse<Recipe.Response> =
        recipeService.getRecipeByName(name)

    @GetMapping("api/recipes")
    fun getRecipes(): ApiResponse<List<Recipe.Response>> =
        recipeService.getRecipes()

    @GetMapping("api/recipes/popularity")
    fun getRecipesByPopularity(): ApiResponse<List<Recipe.Response>> =
        recipeService.getRecipesByPopularity()

    @GetMapping("api/recipes/creator?user={id}")
    fun getRecipesByCreator(@PathVariable("id") id: Long): ApiResponse<List<Recipe.Response?>> =
        recipeService.getRecipesByCreator(id)

    @PostMapping("api/recipes")
    fun createRecipe(@RequestBody recipe: Recipe.CreateRecipe): ApiResponse<Recipe.Response> =
        recipeService.createRecipe(recipe)

    @PutMapping("api/recipes")
    fun updateRecipe(@RequestBody changes: Recipe.ChangeRecipe): ApiResponse<Recipe.Response> =
        recipeService.updateRecipe(changes)

    @PutMapping("api/recipes/image?recipe={id}")
    fun setImage(@RequestParam("image") file: MultipartFile, @PathVariable id: Long): ApiResponse<Recipe.Response> =
        recipeService.updateRecipeImage(file.bytes, id)

    @DeleteMapping("api/recipes?recipe={id}")
    fun deleteRecipe(@PathVariable("id") id: Long): ApiResponse<Recipe.Response> =
        recipeService.deleteRecipeById(id)

}
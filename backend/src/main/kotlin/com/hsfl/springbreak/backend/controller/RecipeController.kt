package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.service.RecipeJpaService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

/**
 * Call recipe-related functions from service.
 */
@CrossOrigin("http://localhost:3000")
@Transactional
@RestController
class RecipeController(val recipeService: RecipeJpaService) {

    @GetMapping("api/recipes/{id}")
    fun getRecipeById(@PathVariable("id") id: Long): ApiResponse<Recipe.Response> =
        recipeService.getRecipeById(id)

    @GetMapping("api/recipes/findByName/{name}")
    fun getRecipeByName(@PathVariable("name") name: String): ApiResponse<Recipe.Response> =
        recipeService.getRecipeByName(name)

    @GetMapping("api/recipes")
    fun getRecipes(): ApiResponse<List<Recipe.Response>> =
        recipeService.getRecipes()

    @PostMapping("api/recipes")
    fun createRecipe(@RequestBody recipe: Recipe.CreateRecipe): ApiResponse<Recipe.Response> =
        recipeService.createRecipe(recipe)

    @PutMapping("api/recipes")
    fun updateRecipe(@RequestBody changes: Recipe.ChangeRecipe): ApiResponse<Recipe.Response> =
        recipeService.updateRecipe(changes)

    @PutMapping("api/recipes/image/{id}")
    fun setImage(@RequestParam("image") file: MultipartFile, @PathVariable id: Long): ApiResponse<Recipe.Response> =
        recipeService.updateRecipeImage(file.bytes, id)

    @DeleteMapping("api/recipes/{id}")
    fun deleteRecipe(@PathVariable("id") id: Long): ApiResponse<Recipe.Response> =
        recipeService.deleteRecipeById(id)

}
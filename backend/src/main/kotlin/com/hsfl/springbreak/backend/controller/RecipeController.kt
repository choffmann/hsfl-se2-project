package com.hsfl.springbreak.backend.controller

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

    @GetMapping("recipes/{id}")
    fun getRecipeById(@PathVariable("id") id: Long): ApiResponse<Recipe> =
        recipeService.getRecipeById(id)

    @GetMapping("recipes/findByName/{name}")
    fun getRecipeByName(@PathVariable("name") name: String): ApiResponse<Recipe> =
        recipeService.getRecipeByName(name)

    @PostMapping("recipes")
    fun createRecipe(@RequestBody recipe: Recipe.CreateRecipe): ApiResponse<Recipe.Response> =
        recipeService.createRecipe(recipe)

    @PutMapping("recipes")
    fun updateRecipe(@RequestBody changes: Recipe.ChangeRecipe) =
        recipeService.updateRecipe(changes)

    @PutMapping("recipes/image/{id}")
    fun setImage(@RequestParam("image") file: MultipartFile, @PathVariable id: Long): ApiResponse<Recipe> =
        recipeService.updateRecipeImage(file.bytes, id)

    @DeleteMapping("recipes/{id}")
    fun deleteRecipe(@PathVariable("id") id: Long): ApiResponse<Recipe> =
        recipeService.deleteRecipeById(id)



    @GetMapping("recipe/favorite/{id}")
    fun getFavoritesById(@PathVariable("id") id: Long): ApiResponse<List<Recipe>> =
        recipeService.getFavoritesById(id)
}
package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.service.RecipeJpaService
import org.springframework.web.bind.annotation.*
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
    fun createRecipe(@RequestBody recipe: Recipe.CreateRecipe): ApiResponse<Recipe> =
        recipeService.createRecipe(recipe)

    @PutMapping("recipes")
    fun updateRecipe(@RequestBody changes: Recipe.ChangeRecipe) =
        recipeService.updateRecipe(changes)

    @DeleteMapping("recipes/{id}")
    fun deleteRecipe(@PathVariable("id") id: Long): ApiResponse<Recipe> =
        recipeService.deleteRecipeById(id)

    @PostMapping("recipe/favorite/{rId}/{uId}")
    fun setFavoriteById(@PathVariable("rId") rId: Long, @PathVariable("uId") uId: Long) =
        recipeService.addFavoriteById(rId, uId)

    @GetMapping("recipe/favorite/{id}")
    fun getFavoritesById(@PathVariable("id") id: Long): ApiResponse<List<Recipe>> =
        recipeService.getFavoritesById(id)
}
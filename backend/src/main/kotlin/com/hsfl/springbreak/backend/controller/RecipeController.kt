package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.repository.RecipeRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000")
@RestController
class RecipeController(val recipeService: RecipeService) {

   @GetMapping("api/recipes/{id}")
   fun getRecipeById(@PathVariable("id") recipeId: Long): ApiResponse<Recipe> {
      recipeService.getRecipeById(recipeId)?.let { recipe ->
         return ApiResponse(data = recipe.toDto(), success = true)
      } ?: return ApiResponse(error = "No Recipe with the ID: $recipeId", success = false)
   }

   @PostMapping("api/recipes")
   fun createRecipe(@RequestBody payload: Recipe): ApiResponse<Recipe>? =
      ApiResponse(data = recipeService.createNewRecipe(payload)?.toDto(), success = true)
      /*
      recipeService.createNewRecipe(payload)?.let { recipe ->
         return ApiResponse(data = recipe.toDto(), success = true)
      } ?: return ApiResponse(error = "Create Error", success = false)
      */

   @PutMapping("api/recipes/{id}")
   fun updateRecipe(@PathVariable("id") recipeId: Long, @RequestBody payload: Recipe): ApiResponse<Recipe>? =
      ApiResponse(data = recipeService.updateRecipe(recipeId, payload)?.toDto(), success = true)

   @DeleteMapping("api/recipes/{id}")
   fun deleteRecipe(@PathVariable("id") recipeId: Long): Unit =
      recipeService.deleteRecipeById(recipeId)
}
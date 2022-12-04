package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.repository.RecipeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@CrossOrigin("http://localhost:3000")
@RestController
class RecipeController(val recipeRepository: RecipeRepository) {

   /*
   @GetMapping("api/recipes/{id}")
   fun findRecipeById(@PathVariable("id") recipeId: Long): Recipe = recipeRepository.findById(recipeId)
      .orElseThrow().toDto()
    */

   @GetMapping("api/recipes/{id}")
   fun findRecipeById(@PathVariable("id") recipeId: Long): ApiResponse<Recipe> {
      recipeRepository.findByIdOrNull(recipeId)?.let { recipe ->
         return ApiResponse(data = recipe.toDto(), success = true)
      } ?: return ApiResponse(error = "No Recipe with the ID: $recipeId", success = false)
   }

   @PostMapping("api/recipes")
   fun createNewRecipe(@RequestBody newRecipe: Recipe): ApiResponse<Recipe> {
      //RecipeEntity.fromDto(newRecipe).category
      val savedRecipe = recipeRepository.save(RecipeEntity.fromDto(newRecipe)).toDto()
      print("-------- "+savedRecipe+"----- \n")
      return if (savedRecipe != null) {
         ApiResponse(data = savedRecipe, success = true)
      } else {
         ApiResponse(error = "Error by saving", success = false)
      }
   }


   @PutMapping("api/recipes/{id}")
   fun updateRecipe(@PathVariable("id") recipeId: Long, @RequestBody recipe: Recipe): ApiResponse<Recipe> {
      return if (recipeRepository.existsById(recipeId)) {
         val result =  recipeRepository.findById(recipeId).get()
         result.title = RecipeEntity.fromDto(recipe).title
         result.shortDescription = RecipeEntity.fromDto(recipe).shortDescription
         result.price = RecipeEntity.fromDto(recipe).price
         result.duration = RecipeEntity.fromDto(recipe).duration
         //result.category = RecipeEntity.fromDto(recipe).category
        // result.creator = RecipeEntity.fromDto(recipe).creator
         return ApiResponse(data = recipeRepository.save(result).toDto(), success = true)
         /*
        it.rating = RecipeEntity.fromDto(recipe).rating
        it.difficulty = RecipeEntity.fromDto(recipe).difficulty
        it.createTime = RecipeEntity.fromDto(recipe).createTime
        it.ingredients = RecipeEntity.fromDto(recipe).ingredients
        it.image = RecipeEntity.fromDto(recipe).image
        it.longDescription = RecipeEntity.fromDto(recipe).longDescription
        it.views = RecipeEntity.fromDto(recipe).views
        it.userFavorites = RecipeEntity.fromDto(recipe).userFavorites
        return@map recipeRepository.save(it)
         */
      } else return ApiResponse(error = "No Recipe with the ID: $recipeId", success = false)
   }

   @DeleteMapping("api/recipes/{id}")
   fun deleteRecipe(@PathVariable("id")recipeId: Long): Unit =
      if (recipeRepository.existsById(recipeId)) {
         recipeRepository.deleteById(recipeId)
      } else {
         throw ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with ID \"$recipeId\" doesn't exist")
      }

   /*
recipeRepository.findById(recipeId).map {
      it.title = RecipeEntity.fromDto(recipe).title
      it.shortDescription = RecipeEntity.fromDto(recipe).shortDescription
      it.price = RecipeEntity.fromDto(recipe).price
      it.duration = RecipeEntity.fromDto(recipe).duration
      it.category = RecipeEntity.fromDto(recipe).category
      it.creator = RecipeEntity.fromDto(recipe).creator
      /*
      it.rating = RecipeEntity.fromDto(recipe).rating
      it.difficulty = RecipeEntity.fromDto(recipe).difficulty
      it.createTime = RecipeEntity.fromDto(recipe).createTime
      it.ingredients = RecipeEntity.fromDto(recipe).ingredients
      it.image = RecipeEntity.fromDto(recipe).image
      it.longDescription = RecipeEntity.fromDto(recipe).longDescription
      it.views = RecipeEntity.fromDto(recipe).views
      it.userFavorites = RecipeEntity.fromDto(recipe).userFavorites
      return@map recipeRepository.save(it)
       */
   }.orElseGet {
      // recipe.id = recipeId
      return@orElseGet recipeRepository.save(RecipeEntity.fromDto(recipe)))
   }.toDto()
 */

   /*
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

    */
}
package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.entity.IngredientEntity
import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Ingredient
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.repository.IngredientRecipeRepository
import com.hsfl.springbreak.backend.repository.IngredientRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@CrossOrigin("http://localhost:3000")
@RestController
class IngredientController(val ingredientRecipeRepository: IngredientRecipeRepository, val ingredientRepository: IngredientRepository) {

    @GetMapping("api/ingredients")
    fun getAllIngredients(): ApiResponse<List<Ingredient>> {
        val ingredientEntities = ingredientRepository.findAll().distinct()
        return if (ingredientEntities.isNotEmpty()) {
            val ingredients = ingredientEntities.map { it.toDto() }
            ApiResponse(data = ingredients, success = true)
        } else {
            ApiResponse(error = "No ingredients available", success = false)
        }
    }

    @PostMapping("api/ingredientRecipe")
    fun createNewIngredientRecipe(@RequestBody newIngredientRecipe: IngredientRecipe): ApiResponse<IngredientRecipe> {
        val savedIngredientRecipe = ingredientRecipeRepository.save(IngredientRecipeEntity.fromDto(newIngredientRecipe)).toDto()
        print("-------- "+savedIngredientRecipe+"----- \n")
        return if (savedIngredientRecipe != null) {
            ApiResponse(data = savedIngredientRecipe, success = true)
        } else {
            ApiResponse(error = "Error by saving", success = false)
        }
    }
}
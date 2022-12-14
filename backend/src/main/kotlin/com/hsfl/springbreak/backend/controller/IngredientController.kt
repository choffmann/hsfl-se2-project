package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Ingredient
import com.hsfl.springbreak.backend.repository.IngredientRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


/**
 * Call ingredient-related functions from ingredient-repository.
 */
@CrossOrigin("http://localhost:3000")
@RestController
class IngredientController(
    val ingredientRepository: IngredientRepository
) {

    /**
     * API-Endpoint for getting a list of all available ingredients.
     * @return API-Response with a list of all stored ingredients or an error
     */
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
}
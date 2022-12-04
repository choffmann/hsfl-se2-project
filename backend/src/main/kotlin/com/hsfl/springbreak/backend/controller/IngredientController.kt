package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.repository.IngredientRecipeRepository
import com.hsfl.springbreak.backend.repository.RecipeRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@CrossOrigin("http://localhost:3000")
@RestController
class IngredientController(val ingredientRecipeRepository: IngredientRecipeRepository) {

    @PostMapping("api/ingredientRecepie")
    fun createNewingredientRecipe(@RequestBody newingredientRecipe: IngredientRecipe): ApiResponse<IngredientRecipe> {
        val savedIngredientRecipe = ingredientRecipeRepository.save(IngredientRecipeEntity.fromDto(newingredientRecipe)).toDto()
        print("-------- "+savedIngredientRecipe+"----- \n")
        return if (savedIngredientRecipe != null) {
            ApiResponse(data = savedIngredientRecipe, success = true)
        } else {
            ApiResponse(error = "Error by saving", success = false)
        }
    }
}
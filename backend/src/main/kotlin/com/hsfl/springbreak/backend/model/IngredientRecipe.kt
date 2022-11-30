package com.hsfl.springbreak.backend.model


data class IngredientRecipeId(
    val recipeId: Long,
    val ingredientId: Long
)

data class IngredientRecipe(
    val id: IngredientRecipeId,
    val recipe: Recipe,
    val ingredient: Ingredient,
    val unit: String,
    val amount: Int
)
package com.hsfl.springbreak.backend.model

data class IngredientRecipeId(
    val recipeId: Long,
    val ingredientId: Long
)

data class IngredientRecipe(
    val id: IngredientRecipeId,
    val unit: String,
    val amount: Int
) {
    data class WithoutRecipe(
        val ingredientName: String,
        val unit: String,
        val amount: Int
    )

    data class Response(
        val id: Long,
        val name: String,
        val unit: String,
        val amount: Int
    )

}
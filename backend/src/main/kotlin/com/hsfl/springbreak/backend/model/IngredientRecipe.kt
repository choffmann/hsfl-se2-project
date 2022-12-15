package com.hsfl.springbreak.backend.model

/**
 * Combination of the recipe- and ingredient-ID
 */
data class IngredientRecipeId(
    val recipeId: Long, val ingredientId: Long
)

/**
 * DTO of ingredient-recipe-entity
 */
data class IngredientRecipe(
    val id: IngredientRecipeId, val unit: String, val amount: Int
) {
    /**
     * DTO, where the recipe name, unit and amount are logged.
     * This DTO is stored in the ingredient list of a create-recipe-DTO.
     * Therefore, there are no recipe related attributes.
     */
    data class WithoutRecipe(
        val ingredientName: String, val unit: String, val amount: Int
    )

    /**
     * DTO, where the recipe ID, name, unit and amount are logged.
     * This DTO is stored in the ingredient list of a response-recipe-DTO.
     * Additionally to the ingredient names, the user also becomes the ingredient
     * IDs from the response.
     */
    data class Response(
        val id: Long, val name: String, val unit: String, val amount: Int
    )

}
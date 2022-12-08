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
) {
    data class WithoutRecipe (
        val ingredient: Ingredient,
        val unit: String,
        val amount: Int
        )
}

/*TODO
1. List von
{
 val ingredient: Ingredient,
    val unit: String,
    val amount: Int
    }
    in recipe speichern. (kann sein dass funktioniert schon)
 2. Rating schauen
    - logic: für (likes, dislikes)
 3. Favorite
  - rezept zu Favorite add
  - rezept von Favorite liste löschen (eine liste oder mehrere?)

 */
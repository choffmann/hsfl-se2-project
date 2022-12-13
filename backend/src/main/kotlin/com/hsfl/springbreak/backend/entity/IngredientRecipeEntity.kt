package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.IngredientRecipeId
import java.io.Serializable
import javax.persistence.*

@Embeddable
data class IngredientRecipeKey(
    @Column(name = "recipe_id") val recipeId: Long?,
    @Column(name = "ingredient_id") val ingredientId: Long?,
) : Serializable

@Entity
data class IngredientRecipeEntity(
    @EmbeddedId val id: IngredientRecipeKey?,
    @ManyToOne(cascade = [CascadeType.ALL]) @MapsId("recipeId") @JoinColumn(name = "recipe_id") val recipe: RecipeEntity?,
    @ManyToOne @MapsId("ingredientId") @JoinColumn(name = "ingredient_id") val ingredient: IngredientEntity?,
    @Column val unit: String,
    @Column val amount: Int
) {
    fun toDto(): IngredientRecipe = IngredientRecipe(
        id = IngredientRecipeId(recipeId = this.recipe?.id!!, ingredientId = this.ingredient?.id!!),
        unit = this.unit,
        amount = this.amount
    )

    fun toResponse(): IngredientRecipe.Response = IngredientRecipe.Response(
        id = this.ingredient?.id!!, name = this.ingredient.name, unit = this.unit, amount = this.amount
    )
}


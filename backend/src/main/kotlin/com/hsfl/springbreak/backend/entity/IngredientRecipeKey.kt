package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Ingredient
import com.hsfl.springbreak.backend.model.Recipe
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.MapsId

@Embeddable
data class IngredientRecipeKey(
    @Column val recipeId: Long?,
    @Column val ingredientId: Long?,
): Serializable

@Entity
data class IngredientRecipe(
    @EmbeddedId val id: IngredientRecipeKey,
    @ManyToOne @MapsId("recipeId") val recipe: RecipeEntity,
    @ManyToOne @MapsId("ingredientId") val ingredient: IngredientEntity,
    @Column val unit: String,
    @Column val amount: Int
)
{
    fun toRecipe(): Recipe = this.recipe.toDto()
    fun toIngredient(): Ingredient = this.ingredient.toDto()
    fun fromIngredient(ingredient: Ingredient): IngredientRecipe =
        IngredientRecipe(id = IngredientRecipeKey(null, ingredient.id),
            recipe = createEmptyRecipe(),
            ingredient = IngredientEntity(name = "", recipe = mutableListOf()),
            unit = "",
            amount = 0
        )

    fun createEmptyRecipe(): RecipeEntity {
        return RecipeEntity(
            null,
            "",
            "",
            0.0,
            null,
            0.0,
            null,
            null,
            null,
            null,
            mutableListOf(),
            "",
            "",
            0,
            mutableListOf()
        )
    }
}


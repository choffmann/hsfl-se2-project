package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Ingredient
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.IngredientRecipeId
import com.hsfl.springbreak.backend.model.Recipe
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId

@Embeddable
data class IngredientRecipeKey(
    @Column(name = "recipe_id") val recipeId: Long?,
    @Column(name = "ingredient_id") val ingredientId: Long?,
) : Serializable

@Entity
data class IngredientRecipeEntity(
    @EmbeddedId val id: IngredientRecipeKey?,
    @ManyToOne(cascade = [CascadeType.ALL]) @MapsId("recipeId") @JoinColumn(name = "recipe_id") val recipe: RecipeEntity?,
    @ManyToOne(cascade = [CascadeType.ALL]) @MapsId("ingredientId") @JoinColumn(name = "ingredient_id") val ingredient: IngredientEntity?,
    @Column val unit: String,
    @Column val amount: Int
) {
    fun toDto(): IngredientRecipe = IngredientRecipe(
        id = IngredientRecipeId(recipeId = this.recipe?.id!!, ingredientId = this.ingredient?.id!!),
        recipe = this.recipe.toDto(),
        ingredient = this.ingredient.toDto(),
        unit = this.unit,
        amount = this.amount
    )

    fun toIngredient(): Ingredient = this.ingredient!!.toDto()
    fun toRecipe(): Recipe = this.recipe!!.toDto()

    companion object {
        fun fromDto(dto: IngredientRecipe): IngredientRecipeEntity = IngredientRecipeEntity(
            id = IngredientRecipeKey(recipeId = dto.recipe.id, ingredientId = dto.ingredient.id),
            recipe = RecipeEntity.fromDto(dto.recipe),
            ingredient = IngredientEntity.fromDto(dto.ingredient),
            unit = dto.unit,
            amount = dto.amount
        )

        fun fromIngredients(ingredient: Ingredient, recipe: Recipe): IngredientRecipeEntity = IngredientRecipeEntity(
            id = IngredientRecipeKey(recipeId = recipe.id, ingredientId = ingredient.id),
            recipe = RecipeEntity.fromDto(recipe),
            ingredient = IngredientEntity.fromDto(ingredient),
            unit = "",
            amount = 0
        )

        /*
        fun fromIngredients(ingredient: Ingredient, recipe: Recipe.CreateRecipe): IngredientRecipeEntity = IngredientRecipeEntity(
            id = IngredientRecipeKey(recipeId = recipe.id, ingredientId = ingredient.id),
            recipe = RecipeEntity.fromDto(recipe),
            ingredient = IngredientEntity.fromDto(ingredient),
            unit = "",
            amount = 0
        )
         */
    }
}


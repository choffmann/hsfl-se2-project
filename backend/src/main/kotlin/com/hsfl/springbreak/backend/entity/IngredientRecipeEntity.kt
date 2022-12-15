package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.IngredientRecipeId
import java.io.Serializable
import javax.persistence.*

/**
 * The ID of the Ingredient-Recipe-Entity consists of
 * the recipe's and ingredient's ID. Therefore the
 * Embeddable annotation declare the coupling of these
 * entities
 */
@Embeddable
data class IngredientRecipeKey(
    @Column(name = "recipe_id") val recipeId: Long?,
    @Column(name = "ingredient_id") val ingredientId: Long?,
) : Serializable

/**
 * The Ingredient-Recipe entity is an association table wich
 * enables the many-to-many relation between the recipe and
 * the ingredient entity. In addition, this entity has two
 * own attributes for unit and amount.
 */
@Entity
data class IngredientRecipeEntity(
    @EmbeddedId val id: IngredientRecipeKey?,
    @ManyToOne(cascade = [CascadeType.ALL]) @MapsId("recipeId") @JoinColumn(name = "recipe_id") val recipe: RecipeEntity?,
    @ManyToOne @MapsId("ingredientId") @JoinColumn(name = "ingredient_id") val ingredient: IngredientEntity?,
    @Column val unit: String,
    @Column val amount: Int
) {

    /**
     * Converts the current Ingredient-Recipe-Entity to an Ingredient-Recipe-DTO.
     * @return Ingredient-Recipe-DTO
     */
    fun toDto(): IngredientRecipe = IngredientRecipe(
        id = IngredientRecipeId(recipeId = this.recipe?.id!!, ingredientId = this.ingredient?.id!!),
        unit = this.unit,
        amount = this.amount
    )

    /**
     * Converts the current Ingredient-Recipe-Entity to a Response-DTO. Only the
     * ingredient-ID, -name, -unit and -amount are passed to the DTO-conversion.
     * @return Ingredient-Recipe-Response-DTO
     */
    fun toResponse(): IngredientRecipe.Response = IngredientRecipe.Response(
        id = this.ingredient?.id!!, name = this.ingredient.name, unit = this.unit, amount = this.amount
    )
}


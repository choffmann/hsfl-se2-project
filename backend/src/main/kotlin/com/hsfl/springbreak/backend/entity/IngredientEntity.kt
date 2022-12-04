package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Ingredient
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity(name = "ingredient")
data class IngredientEntity(
    @Id @GeneratedValue val id: Long? = null,
    @Column val name: String,
    @OneToMany(mappedBy = "ingredient", cascade = [CascadeType.ALL]) val recipes: List<IngredientRecipeEntity>?
) {

    fun toDto(): Ingredient = Ingredient(
        id = this.id!!,
        recipes = this.recipes!!.map { it.toRecipe() },
        name = this.name
    )

    companion object {
        fun fromDto(dto: Ingredient): IngredientEntity = IngredientEntity(
            id = dto.id,
            name = dto.name,
            recipes = dto.recipes.map { IngredientRecipeEntity.fromIngredients(dto, it) }
        )
    }
}
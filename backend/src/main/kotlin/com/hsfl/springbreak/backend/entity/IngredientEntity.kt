package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Ingredient
import javax.persistence.*

@Entity(name = "ingredient")
data class IngredientEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column val name: String,
) {

    /**
     * Converts the current Ingredient-Entity to a Ingredient-DTO.
     * @return Ingredient-DTO
     */
    fun toDto(): Ingredient = Ingredient(
        id = this.id!!, name = this.name
    )

    /**
     * Converts a Ingredient-DTO to an Ingredient-Entity.
     * @param dto Ingredient-DTO
     * @return Ingredient-Entity
     */
    companion object {
        fun fromDto(dto: Ingredient): IngredientEntity = IngredientEntity(
            id = dto.id,
            name = dto.name,
        )
    }
}
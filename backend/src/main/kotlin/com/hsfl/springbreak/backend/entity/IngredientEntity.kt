package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Ingredient
import javax.persistence.*

@Entity(name = "ingredient")
data class IngredientEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column val name: String,
) {

    fun toDto(): Ingredient = Ingredient(
        id = this.id!!, name = this.name
    )

    companion object {
        fun fromDto(dto: Ingredient): IngredientEntity = IngredientEntity(
            id = dto.id,
            name = dto.name,
        )
    }
}
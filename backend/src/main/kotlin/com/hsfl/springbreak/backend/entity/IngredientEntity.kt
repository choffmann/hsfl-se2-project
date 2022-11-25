package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Difficulty
import com.hsfl.springbreak.backend.model.Ingredient
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity(name = "ingredient")
data class IngredientEntity(
    @Id @GeneratedValue val id: Long? = null,
    @Column val name: String,
    @OneToMany val recipe: MutableList<IngredientRecipe> = mutableListOf()
) : DataEntity<Ingredient, IngredientEntity> {

    override fun toDto(): Ingredient = Ingredient(
        id = this.id!!,
        name = this.name
    )

    override fun fromDto(dto: Ingredient): IngredientEntity {
        TODO("Not yet implemented")
    }
}
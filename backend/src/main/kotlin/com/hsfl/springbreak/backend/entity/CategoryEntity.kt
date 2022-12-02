package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Category
import com.hsfl.springbreak.backend.model.Difficulty
import javax.persistence.*

@Entity(name = "category")
data class CategoryEntity(
    @Id @GeneratedValue val id: Long? = null,
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL]) val recipes: List<RecipeEntity>,
    @Column val name: String
) {

    fun toDto(): Category = Category(
        id = this.id!!,
        recipes = this.recipes.map { it.toDto() },
        name = this.name
    )

    companion object {
        fun fromDto(dto: Category): CategoryEntity = CategoryEntity(
            id = dto.id,
            recipes = dto.recipes.map { RecipeEntity.fromDto(it) },
            name = dto.name
        )
    }
}

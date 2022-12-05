package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Difficulty
import com.hsfl.springbreak.backend.model.Rating
import javax.persistence.*

@Entity(name = "difficulty")
data class DifficultyEntity(
    @Id @GeneratedValue val id: Long? = null,
   // @OneToMany(mappedBy = "difficulty", cascade = [CascadeType.ALL]) val recipes: List<RecipeEntity>,
    @Column val name: String
) {

    fun toDto(): Difficulty = Difficulty(
        id = this.id!!,
       // recipes = this.recipes.map { it.toDto() },
        name = this.name
    )

    companion object {
        fun fromDto(dto: Difficulty): DifficultyEntity = DifficultyEntity(
            id = dto.id,
           // recipes = dto.recipes.map { RecipeEntity.fromDto(it) },
            name = dto.name
        )
    }
}


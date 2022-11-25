package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Difficulty
import com.hsfl.springbreak.backend.model.Rating
import javax.persistence.*

@Entity(name = "difficulty")
data class DifficultyEntity(
    @Id @GeneratedValue val id: Long? = null,
    @Column val name: String
) : DataEntity<Difficulty, DifficultyEntity> {

    override fun toDto(): Difficulty = Difficulty(
        id = this.id!!,
        name = this.name
    )

    override fun fromDto(dto: Difficulty): DifficultyEntity {
        TODO("Not yet implemented")
    }
}


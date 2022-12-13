package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Difficulty
import javax.persistence.*

@Entity(name = "difficulty")
data class DifficultyEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null, @Column val name: String
) {

    fun toDto(): Difficulty = Difficulty(
        id = this.id!!, name = this.name
    )

    companion object {
        fun fromDto(dto: Difficulty): DifficultyEntity = DifficultyEntity(
            id = dto.id, name = dto.name
        )
    }
}


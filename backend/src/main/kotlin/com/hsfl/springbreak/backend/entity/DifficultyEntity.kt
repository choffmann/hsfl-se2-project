package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Difficulty
import javax.persistence.*

@Entity(name = "difficulty")
data class DifficultyEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null, @Column val name: String
) {

    /**
     * Converts the current Difficulty-Entity to a Difficulty-DTO.
     * @return Category-DTO
     */
    fun toDto(): Difficulty = Difficulty(
        id = this.id!!, name = this.name
    )

    companion object {
        /**
         * Converts a Difficulty-DTO to a Difficulty-Entity.
         * @param dto Difficulty-DTO
         * @return Difficulty-Entity
         */
        fun fromDto(dto: Difficulty): DifficultyEntity = DifficultyEntity(
            id = dto.id, name = dto.name
        )
    }
}


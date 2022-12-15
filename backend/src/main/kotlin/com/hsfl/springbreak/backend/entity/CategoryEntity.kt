package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Category
import javax.persistence.*

/**
 * The entity of recipe category
 * @param id category's ID
 * @param name category's name
 */
@Entity(name = "category")
data class CategoryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null, @Column val name: String
) {

    /**
     * Converts the current Category-Entity to a Category-DTO.
     * @return Category-DTO
     */
    fun toDto(): Category = Category(
        id = this.id!!, name = this.name
    )

    companion object {
        /**
         * Converts a Category-DTO to a Category-Entity.
         * @param dto Category-DTO
         * @return Category-Entity
         */
        fun fromDto(dto: Category): CategoryEntity = CategoryEntity(
            id = dto.id, name = dto.name
        )
    }
}

package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Category
import com.hsfl.springbreak.backend.model.Difficulty
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "category")
data class CategoryEntity(
    @Id @GeneratedValue val id: Long? = null,
    @Column val name: String
) : DataEntity<Category, CategoryEntity> {

    override fun toDto(): Category = Category(
        id = this.id!!,
        name = this.name
    )

    override fun fromDto(dto: Category): CategoryEntity {
        TODO("Not yet implemented")
    }
}

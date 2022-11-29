package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.User
import javax.persistence.*

@Entity(name = "users")
data class UserEntity(
    @Id @GeneratedValue val id: Long? = null,
    @Column val firstName: String,
    @Column val lastName: String,
    @Column val email: String,
    @Column val password: String,
    @Column val image: String? = null,
    @ManyToMany @JoinTable(
        name = "user_favorite",
        joinColumns = [JoinColumn(name = "users_id")],
        inverseJoinColumns = [JoinColumn(name = "recipe_id")]
    ) val favoriteRecipe: List<RecipeEntity>? = null
) {

    fun toDto(): User = User(
        id = this.id!!,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password,
        image = this.image
    )

    companion object {
        fun fromDto(dto: User): UserEntity = UserEntity(
            id = dto.id,
            firstName = dto.firstName,
            lastName = dto.lastName,
            email = dto.email,
            password = dto.password,
            image = dto.image
        )
    }
}

package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.User
import org.hibernate.type.BlobType
import java.sql.Blob
import javax.persistence.*

@Entity(name = "users")
data class UserEntity(
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) val id: Long? = null,
    @Column val firstName: String,
    @Column val lastName: String,
    @Column val email: String,
    @Column val password: String,
    @Column @Lob var image: Blob? = null,
    @ManyToMany @JoinTable(
        name = "user_favorite",
        joinColumns = [JoinColumn(name = "users_id")],
        inverseJoinColumns = [JoinColumn(name = "recipe_id")]
    ) val favorites: MutableList<RecipeEntity> = mutableListOf<RecipeEntity>()


) {

    fun toDto(): User = User(
        id = this.id!!,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password,
        image = this.image,
        favorites = toRecipeDto(this.favorites)
        // ratings = this.rating.map { it.toDto() }
    )

    private fun toRecipeDto(dtoList: MutableList<RecipeEntity>): MutableList<Recipe> {
        val resultList = mutableListOf<Recipe>()
        dtoList.forEach {
            resultList.add(it.toDto())
        }
        return resultList
    }


    companion object {
        fun fromDto(dto: User): UserEntity = UserEntity(
            id = dto.id,
            firstName = dto.firstName,
            lastName = dto.lastName,
            email = dto.email,
            password = dto.password,
            image = dto.image,
            favorites = fromRecipeDto(dto.favorites)
            //favorites = dto.favorites.map { RecipeEntity.fromDto(it) }
            // ratings = dto.ratings.map { RecipeEntity.fromDto }
        )

        private fun fromRecipeDto(dtoList: MutableList<Recipe>): MutableList<RecipeEntity> {
            val resultList = mutableListOf<RecipeEntity>()
            dtoList.forEach {
                resultList.add(RecipeEntity.fromDto(it))
            }
            return resultList
        }

        fun fromDto(dto: User.ChangeProfile, defaultUser: UserEntity): UserEntity = UserEntity(
            id = defaultUser.id!!,
            email = defaultUser.email,
            password = dto.password ?: defaultUser.password,
            firstName = dto.firstName ?: defaultUser.firstName,
            lastName = dto.lastName ?: defaultUser.lastName,
            favorites = defaultUser.favorites
            // image = dto.image ?: defaultUser.image
        )

        fun fromDto(dto: User.Register): UserEntity = UserEntity(
            firstName = dto.firstName,
            lastName = dto.lastName,
            password = dto.password,
            email = dto.email,
            favorites = mutableListOf()
        )
    }
}

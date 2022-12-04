package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.*
import java.io.Serializable
//import javax.persistence.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId

@Embeddable
data class UserRecipeKey(
        @Column(name = "recipe_id") val recipeId: Long,
        @Column(name = "users_id") val userId: Long,
) : Serializable

@Entity(name = "Favorite")
data class FavoriteEntity(
        @EmbeddedId val id: UserRecipeKey?,
        @ManyToOne @MapsId("recipeId") @JoinColumn(name = "recipe_id") val recipe: RecipeEntity,
        @ManyToOne @MapsId("userId") @JoinColumn(name = "users_id") val user: UserEntity,
)
{
    fun toDto(): Favorite = Favorite(
            id = UserRecipeId(recipeId = this.recipe.id!!, userId = this.user.id!!),
            recipe = this.recipe.toDto(),
            user = this.user.toDto(),

            )

    fun toUser(): User = this.user.toDto()
    fun toRecipe(): Recipe = this.recipe.toDto()

    companion object {
        fun fromDto(dto: Favorite): FavoriteEntity = FavoriteEntity(
                id = UserRecipeKey(recipeId = dto.recipe.id, userId = dto.user.id),
                recipe = RecipeEntity.fromDto(dto.recipe),
                user = UserEntity.fromDto(dto.user),


                )

    }
}









package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Rating
import com.hsfl.springbreak.backend.model.User
import javax.persistence.*

@Entity(name = "rating")
data class RatingEntity(
   // @Id @Column(name = "recipe_id") val id: Long? = null,
        @Id @GeneratedValue(strategy=GenerationType.IDENTITY) val id: Long? = null,
        @Column var stars: Double,
        @ManyToOne(cascade = [CascadeType.ALL]) @JoinColumn(name = "recipe_id") val recipe: RecipeEntity,
        @ManyToOne(cascade = [CascadeType.ALL]) @JoinColumn(name = "user_id") val user: UserEntity
) {

    fun toDto(): Rating = Rating(
        id = this.id!!,
        stars = this.stars,
        recipe = this.recipe.toDto(),
        user = this.user.toDto()
    )

    companion object {
        fun fromDto(dto: Rating): RatingEntity = RatingEntity(
            id = dto.id,
            stars = dto.stars,
            recipe = RecipeEntity.fromDto(dto.recipe),
            user = UserEntity.fromDto(dto.user)
        )

        fun fromDto(newRating: Rating.SendRating, recipe: RecipeEntity, user: UserEntity): RatingEntity =
            RatingEntity(
                stars = newRating.stars,
                recipe = recipe,
                user = user
        )
    }
}

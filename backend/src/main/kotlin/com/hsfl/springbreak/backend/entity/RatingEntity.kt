package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Rating
import javax.persistence.*

@Entity(name = "rating")
data class RatingEntity(
    @Id @Column(name = "recipe_id") val id: Long? = null,
    @OneToOne @MapsId @JoinColumn(name = "recipe_id") val recipe: RecipeEntity,
    @Column val likes: Int,
    @Column val dislike: Int
) {

    fun toDto(): Rating = Rating(
        id = this.id!!,
        like = this.likes,
        dislike = this.dislike,
        recipe = this.recipe.toDto()
    )

    companion object {
        fun fromDto(dto: Rating): RatingEntity = RatingEntity(
            id = dto.id,
            likes = dto.like,
            dislike = dto.dislike,
            recipe = RecipeEntity.fromDto(dto.recipe)
        )
    }
}

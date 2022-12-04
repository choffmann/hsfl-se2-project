package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Rating
import javax.persistence.*

@Entity(name = "rating")
data class RatingEntity(
   // @Id @Column(name = "recipe_id") val id: Long? = null,
        @Id @GeneratedValue val id: Long? = null,
        @Column var likes: Int,
        @Column var dislike: Int,
        @OneToOne @JoinColumn(name = "recipe_id") val recipe: RecipeEntity,
) {

    fun toDto(): Rating = Rating(
        id = this.id!!,
        likes = this.likes,
        dislike = this.dislike,
        recipe = this.recipe.toDto()
    )

    companion object {
        fun fromDto(dto: Rating): RatingEntity = RatingEntity(
            id = dto.id,
            likes = dto.likes,
            dislike = dto.dislike,
            recipe = RecipeEntity.fromDto(dto.recipe)
        )
    }
}

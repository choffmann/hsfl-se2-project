package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Rating
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.model.User
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "rating")
data class RatingEntity(
    @Id @GeneratedValue val id: Long? = null,
    @OneToOne val recipe: RecipeEntity?,
    @Column val like: Int,
    @Column val dislike: Int
) : DataEntity<Rating, RatingEntity> {

    override fun toDto(): Rating = Rating(
        id = this.id!!,
        like = this.like,
        dislike = this.dislike
    )

    override fun fromDto(dto: Rating): RatingEntity = RatingEntity (
        id = null,
        recipe = null,
        like = dto.like,
        dislike = dto.dislike
    )
}

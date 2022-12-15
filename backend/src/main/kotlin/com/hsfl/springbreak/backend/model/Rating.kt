package com.hsfl.springbreak.backend.model

/**
 * DTO of rating-entity.
 */
data class Rating(
    val id: Long, val stars: Double, val recipe: Recipe, val user: User
) {
    /**
     * Dto which is used for sending a rating for a recipe.
     * It contains the amount of rating stars (1-5), the
     * corresponding recipe ID and the creator's ID.
     */
    data class SendRating(
        val stars: Double, val recipeId: Long, val userId: Long
    )
}

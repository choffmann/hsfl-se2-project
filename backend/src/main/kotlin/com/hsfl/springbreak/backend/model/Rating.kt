package com.hsfl.springbreak.backend.model

data class Rating(
    val id: Long, val stars: Double, val recipe: Recipe, val user: User
) {
    data class SendRating(
        val stars: Double, val recipeId: Long, val userId: Long
    )
}

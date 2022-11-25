package com.hsfl.springbreak.backend.model

data class Rating(
    val id: Long,
    val likes: Int,
    val dislike: Int,
    val recipe: Recipe
)

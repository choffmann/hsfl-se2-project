package com.hsfl.springbreak.backend.model

data class UserRecipeId(
        val recipeId: Long,
        val userId: Long
)

data class Favorite(
        val id: UserRecipeId,
        val recipe: Recipe,
        val user: User,
    )

package com.hsfl.springbreak.backend.model

import java.sql.Blob
import java.time.LocalDateTime

data class Recipe(
    val id: Long,
    val title: String,
    val shortDescription: String,
    val price: Double,
    val duration: Double,
    val difficulty: Difficulty,
    val category: Category,
    val creator: User,
    val createTime: LocalDateTime,
    val image: Blob?,
    val longDescription: String?,
    val views: Int,
    val score: Double,
    val ratings: List<Rating>,
    val ingredients: List<IngredientRecipe>
) {

    data class CreateRecipe(
        val title: String,
        val shortDescription: String?,
        val price: Double?,
        val duration: Double?,
        val difficultyId: Long,
        val categoryId: Long,
        val creatorId: Long,
        val longDescription: String?,
        val ingredients: List<IngredientRecipe.WithoutRecipe>
    )

    data class ChangeRecipe(
        val recipeId: Long,
        val title: String,
        val shortDescription: String?,
        val price: Double?,
        val duration: Double?,
        val difficultyId: Long,
        val categoryId: Long,
        val longDescription: String?,
        val ingredients: List<IngredientRecipe.WithoutRecipe>
    )

    data class Response(
        val id: Long,
        val title: String,
        val shortDescription: String,
        val price: Double,
        val duration: Double,
        val difficulty: Difficulty,
        val category: Category,
        val creator: User.Response,
        val createTime: LocalDateTime,
        val image: Blob?,
        val longDescription: String?,
        val views: Int,
        val ingredients: List<IngredientRecipe.Response>,
        val score: Double
    )

}

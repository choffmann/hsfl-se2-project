package com.hsfl.springbreak.frontend.client.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Int,
    val title: String,
    val shortDescription: String,
    val price: Double,
    val duration: Double,
    val difficulty: Difficulty,
    val category: Category,
    val creator: User,
    val createTime: String,
    val image: String?,
    val longDescription: String,
    val views: Int,
    val ingredients: List<Ingredient>,
    val score: Double
) {

    @Serializable
    data class Create(
        val title: String,
        val shortDescription: String,
        val price: Double,
        val duration: Double,
        val difficultyId: Int,
        val categoryId: Int,
        val creatorId: Int,
        val longDescription: String,
        val ingredients: List<Ingredient.Create>
    )

    @Serializable
    data class Response(
        val error: String? = null,
        val data: Recipe? = null,
        val success: Boolean = false
    )

    @Serializable
    data class ResponseList(
        val error: String? = null,
        val data: List<Recipe> = emptyList(),
        val success: Boolean = false
    )

    @Serializable
    data class Update(
        @SerialName("recipeId") val id: Int,
        val title: String? = null,
        val shortDescription: String? = null,
        val price: Double? = null,
        val duration: Double? = null,
        val difficultyId: Int? = null,
        val categoryId: Int? = null,
        val longDescription: String? = null,
        val ingredients: List<Ingredient.Create>? = null
    )

    @Serializable
    data class ImageResponse(
        val error: String? = null,
        val data: String? = null,
        val success: Boolean = false
    )

    @Serializable
    data class Rating(
        val stars: Double,
        val recipeId: Int,
        val userId: Int
    )

    @Serializable
    data class RatingResponse(
        val error: String? = null,
        val data: Double? = null,
        val success: Boolean = false
    )
}
package com.hsfl.springbreak.frontend.client.data.model

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
    val image: String,
    val longDescription: String,
    val views: Int,
    val ingredient: Ingredient
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
        val ingredient: List<Ingredient.Create>
    )

    @Serializable
    data class Response(
        val error: String? = null,
        val data: Recipe? = null,
        val success: Boolean = false
    )

    @Serializable
    data class Update(
        val id: Long,
        val title: String? = null,
        val shortDescription: String? = null,
        val price: Double? = null,
        val duration: Double? = null,
        val difficultyId: Long? = null,
        val categoryId: Long? = null,
        val longDescription: String? = null,
        val ingredient: List<Ingredient>? = null
    )

    @Serializable
    data class Image(
        val imageUrl: String
    )

    @Serializable
    data class ImageResponse(
        val error: String? = null,
        val imageUrl: String? = null,
        val success: Boolean = false
    )
}
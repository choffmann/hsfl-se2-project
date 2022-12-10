package com.hsfl.springbreak.frontend.client.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val id: Int,
    val name: String,
    val amount: Int,
    val unit: String
) {

    @Serializable
    data class Create(
        @SerialName("ingredientName") val name: String,
        val amount: Int,
        val unit: String
    )

    @Serializable
    data class GetAllResponse(
        val error: String? = null,
        val data: List<Label> = emptyList(),
        val success: Boolean = false
    )

    @Serializable
    data class Label(
        val id: Long,
        @SerialName("name") val label: String
    )
}
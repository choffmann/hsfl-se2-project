package com.hsfl.springbreak.frontend.client.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val id: Long?,
    val name: String
) {
    @Serializable
    data class GetAllResponse(
        val error: String? = null,
        val data: List<Ingredient> = emptyList(),
        val success: Boolean = false
    )

    data class Label(
        val id: Long,
        val label: String
    )
}
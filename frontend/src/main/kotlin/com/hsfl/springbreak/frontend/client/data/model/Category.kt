package com.hsfl.springbreak.frontend.client.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Long,
    val name: String
) {
    @Serializable
    data class GetAllResponse(
        val error: String? = null,
        val data: List<Category> = emptyList(),
        val success: Boolean = false
    )
}
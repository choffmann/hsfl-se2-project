package com.hsfl.springbreak.frontend.client.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Difficulty(
    val id: Int,
    val name: String
) {
    @Serializable
    data class GetAllResponse(
        val error: String? = null,
        val data: List<Difficulty> = emptyList(),
        val success: Boolean = false
    )
}
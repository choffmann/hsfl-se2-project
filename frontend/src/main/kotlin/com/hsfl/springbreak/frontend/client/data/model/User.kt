package com.hsfl.springbreak.frontend.client.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val image: String? = null,
    val favorites: List<Favorite> = emptyList()
) {
    @Serializable
    data class Login(
        val email: String,
        val password: String
    )

    data class State(
        val firstName: String?,
        val lastName: String?,
        val email: String?,
        val password: String?,
        val image: String? = null
    )

    @Serializable
    data class Register(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String
    )

    @Serializable
    data class Image(
        val imageUrl: String
    )

    @Serializable
    data class Response(
        val error: String? = null,
        val data: User? = null,
        val success: Boolean = false
    )

    @Serializable
    data class ImageResponse(
        val error: String? = null,
        val imageUrl: String? = null,
        val success: Boolean = false
    )

    @Serializable
    data class UpdateProfile(
        val id: Int,
        val firstName: String?,
        val lastName: String?,
        val password: String?
    )
}


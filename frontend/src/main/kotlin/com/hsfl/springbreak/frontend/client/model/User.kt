package com.hsfl.springbreak.frontend.client.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
) {
    @Serializable
    data class Login(
        val email: String,
        val password: String
    )

    @Serializable
    data class Response(
        val error: String? = null,
        val data: User? = null,
        val success: Boolean = false
    )
}


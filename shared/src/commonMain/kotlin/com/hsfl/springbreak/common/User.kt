package com.hsfl.springbreak.common

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
}

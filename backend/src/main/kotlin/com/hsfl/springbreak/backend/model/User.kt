package com.hsfl.springbreak.backend.model

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
) {
    data class Login(
        val email: String,
        val password: String
    )
}

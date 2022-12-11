package com.hsfl.springbreak.backend.model

import java.sql.Blob

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val image: String?
) {
    data class Login(
        val email: String,
        val password: String
    )

    data class Register(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String,
        val image: String?
    )

    data class ChangeProfile(
        val firstName: String?,
        val lastName: String?,
        val image: String?
    )
}


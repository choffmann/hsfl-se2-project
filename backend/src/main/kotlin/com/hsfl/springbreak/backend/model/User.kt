package com.hsfl.springbreak.backend.model

import java.sql.Blob

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val image: Blob?,
    val favorites: MutableList<Recipe>
    // val rating: List<Recipe>
) {
    data class Login(
        val email: String,
        val password: String
    )

    data class Register(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String
    )

    data class ChangeProfile(
        val id: Long,
        val firstName: String?,
        val lastName: String?,
        val password: String?
    )
}


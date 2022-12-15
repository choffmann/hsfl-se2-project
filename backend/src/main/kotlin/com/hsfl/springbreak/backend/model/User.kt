package com.hsfl.springbreak.backend.model

/**
 * DTO of user-entity
 */
data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val image: String?,
    val favorites: MutableList<Recipe>
) {
    /**
     * DTO which is used for the login request by the user. It only contains
     * the email and the password values.
     */
    data class Login(
        val email: String, val password: String
    )

    /**
     * DTO which is used for the register request by the user. It only contains
     * the user's firstname, lastname, email and the password.
     */
    data class Register(
        val firstName: String, val lastName: String, val email: String, val password: String
    )

    /**
     * DTO which is used for the data transfer at the
     * updating user put request. It only contains the attributes
     * that can be changed by a user and the corresponding user ID
     * (E-Mail can't be changed).
     */
    data class ChangeProfile(
        val id: Long, val firstName: String?, val lastName: String?, val password: String?
    )

    /**
     * DTO for transferring the profile information to the user.
     */
    data class Response(
        val id: Long,
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String,
        val image: String?
    )

}


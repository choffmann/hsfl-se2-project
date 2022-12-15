package com.hsfl.springbreak.backend.model

/**
 * The API-Response which is sent back to the user.
 * If no failures occurred in the JPA-logic, then
 * the data-attribute is filed with the corresponding
 * response-DTO or other expected content and the
 * success-attribute is set to true.
 * Otherwise, the error-attribute becomes a message and
 * the success-attribute is set to false.
 */
data class ApiResponse<T>(
    val error: String? = null, val data: T? = null, val success: Boolean = false
)

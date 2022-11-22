package com.hsfl.springbreak.common

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val error: String? = null,
    val data: T? = null,
    val success: Boolean = false
)

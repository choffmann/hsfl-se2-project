package com.hsfl.springbreak.common


data class ApiResponse<T>(
    val error: String? = null,
    val data: T? = null,
    val success: Boolean = false
)

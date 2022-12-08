package com.hsfl.springbreak.frontend.client.data.model

import kotlinx.serialization.Serializable

@Serializable
data class APIResponse<T>(
    val error: String? = null,
    val data: T? = null,
    val success: Boolean = false
) {
    companion object {
        fun <T> fromResponse(error: String?, data: T?, success: Boolean): APIResponse<T> = APIResponse(
            error = error,
            data = data,
            success = success
        )
    }
}
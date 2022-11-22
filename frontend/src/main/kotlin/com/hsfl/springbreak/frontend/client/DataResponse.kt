package com.hsfl.springbreak.frontend.client

sealed class DataResponse<T> {
    class Success<T>(val data: T) : DataResponse<T>()
    class Error<T>(val error: String): DataResponse<T>()
    class Loading<T> : DataResponse<T>()
    class Unauthorized<T>(val error: String): DataResponse<T>()

    suspend fun <out: T> handleDataResponse(
        onSuccess: suspend (T) -> Unit,
        onError: suspend (String) -> Unit,
        onLoading: suspend () -> Unit,
        onUnauthorized: suspend (String) -> Unit
    ) = when (this) {
        is Error -> onError(this.error)
        is Loading -> onLoading()
        is Success -> onSuccess(this.data)
        is Unauthorized -> onUnauthorized(this.error)
    }
}

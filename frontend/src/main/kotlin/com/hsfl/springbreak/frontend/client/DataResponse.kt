package com.hsfl.springbreak.frontend.client

import com.hsfl.springbreak.frontend.client.viewmodel.UiEvent
import com.hsfl.springbreak.frontend.client.viewmodel.UiEventViewModel

sealed class DataResponse<T> {
    class Success<T>(val data: T) : DataResponse<T>()
    class Error<T>(val error: String): DataResponse<T>()
    class Loading<T> : DataResponse<T>()
    class Unauthorized<T>(val error: String): DataResponse<T>()

    suspend fun <out: T> handleDataResponse(
        onSuccess: suspend (T) -> Unit,
        onError: suspend (String) -> Unit = {
            UiEventViewModel.onEvent(UiEvent.ShowError(it))
        },
        onLoading: suspend () -> Unit = {
            UiEventViewModel.onEvent(UiEvent.ShowLoading)
        },
        onUnauthorized: suspend (String) -> Unit
    ) = when (this) {
        is Error -> onError(this.error)
        is Loading -> onLoading()
        is Success -> onSuccess(this.data)
        is Unauthorized -> onUnauthorized(this.error)
    }
}

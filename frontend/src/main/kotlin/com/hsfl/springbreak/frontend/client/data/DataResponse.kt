package com.hsfl.springbreak.frontend.client.data

import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState

sealed class DataResponse<T> {
    class Success<T>(val data: T) : DataResponse<T>()
    class Error<T>(val error: String): DataResponse<T>()
    class Loading<T> : DataResponse<T>()

    suspend fun <out: T> handleDataResponse(
        onSuccess: suspend (T) -> Unit,
        onError: suspend (String) -> Unit = {
            UiEventState.onEvent(UiEvent.ShowError(it))
        },
        onLoading: suspend () -> Unit = {
            UiEventState.onEvent(UiEvent.ShowLoading)
        }
    ) = when (this) {
        is Error -> onError(this.error)
        is Loading -> onLoading()
        is Success -> onSuccess(this.data)
    }
}

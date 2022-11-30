package com.hsfl.springbreak.frontend.client.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mui.material.SnackbarCloseReason

object MessageViewModel {
    private val _openSnackbar = MutableStateFlow(UiEventViewModel.uiState.value is UiEvent.ShowMessage)
    val openSnackbar: StateFlow<Boolean> = _openSnackbar

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun onEvent(event: SnackbarEvent) {
        when (event) {
            is SnackbarEvent.Show -> showError()
            is SnackbarEvent.Close -> closeSnackbar()
        }
    }

    private fun showError() {
        if (UiEventViewModel.uiState.value is UiEvent.ShowMessage) {
            _openSnackbar.value = true
            _message.value = (UiEventViewModel.uiState.value as UiEvent.ShowMessage).msg
        }
    }

    private fun closeSnackbar() {
        _openSnackbar.value = false
        UiEventViewModel.onEvent(UiEvent.Idle)
    }

}

sealed class SnackbarEvent {
    data class Show(val value: String) : SnackbarEvent()
    data class Close(val event: dynamic, val reason: SnackbarCloseReason) : SnackbarEvent()
}
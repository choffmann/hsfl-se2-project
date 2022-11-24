package com.hsfl.springbreak.frontend.client.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mui.material.SnackbarCloseReason

object ErrorViewModel {
    private val _openErrorSnackbar = MutableStateFlow(UiEventViewModel.uiState.value is UiEvent.ShowError)
    val openErrorSnackbar: StateFlow<Boolean> = _openErrorSnackbar

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun onEvent(event: SnackbarEvent) {
        when (event) {
            is SnackbarEvent.Show -> showError()
            is SnackbarEvent.Close -> closeSnackbar()
        }
    }

    private fun showError() {
        if (UiEventViewModel.uiState.value is UiEvent.ShowError) {
            _openErrorSnackbar.value = true
            _errorMessage.value = (UiEventViewModel.uiState.value as UiEvent.ShowError).error
        }
    }

    private fun closeSnackbar() {
        _openErrorSnackbar.value = false
        UiEventViewModel.onEvent(UiEvent.Idle)
    }
}

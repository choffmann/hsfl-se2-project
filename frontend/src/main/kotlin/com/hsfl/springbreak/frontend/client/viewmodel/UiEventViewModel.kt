package com.hsfl.springbreak.frontend.client.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


object UiEventViewModel {
    private val _uiState = MutableStateFlow<UiEvent>(UiEvent.Idle)
    val uiState: StateFlow<UiEvent> = _uiState

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ShowLoading -> _uiState.value = UiEvent.ShowLoading
            is UiEvent.ShowError -> {
                _uiState.value = UiEvent.ShowError(event.error)
                ErrorViewModel.onEvent(ErrorEvent.ShowError(event.error))
            }
            is UiEvent.Idle -> _uiState.value = UiEvent.Idle
        }
    }
}

sealed class UiEvent {
    // Show loading in Ui
    object ShowLoading: UiEvent()

    // Show error in snackbar
    data class ShowError(val error: String): UiEvent()

    // Call on success
    object Idle: UiEvent()
}
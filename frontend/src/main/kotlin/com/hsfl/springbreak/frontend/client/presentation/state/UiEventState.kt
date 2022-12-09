package com.hsfl.springbreak.frontend.client.presentation.state

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.MessageViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.SnackbarEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


object UiEventState {
    private val _uiState = MutableStateFlow<UiEvent>(UiEvent.Idle)
    val uiState: StateFlow<UiEvent> = _uiState

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ShowLoading -> _uiState.value = UiEvent.ShowLoading
            is UiEvent.ShowError -> {
                // TODO: Don't call ErrorViewModel.onEvent here
                _uiState.value = UiEvent.ShowError(event.error)
                MessageViewModel.onEvent(SnackbarEvent.Show(event.error))
            }
            is UiEvent.Idle -> _uiState.value = UiEvent.Idle
            is UiEvent.ShowMessage -> {
                // TODO: Don't call MessageViewModel.onEvent here
                _uiState.value = UiEvent.ShowMessage(event.msg)
                MessageViewModel.onEvent(SnackbarEvent.Show(event.msg))
            }
        }
    }
}

sealed class UiEvent {
    // Show loading in Ui
    object ShowLoading: UiEvent()

    // Show error in snackbar
    data class ShowError(val error: String): UiEvent()

    data class ShowMessage(val msg: String): UiEvent()

    // Call on success
    object Idle: UiEvent()
}
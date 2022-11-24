package com.hsfl.springbreak.frontend.client.viewmodel

import kotlinx.coroutines.flow.*

object DebugViewModel {
    private val _authState = MutableStateFlow(DebugListData())
    val authState: StateFlow<DebugListData> = _authState

    private var showLoading: Boolean = UiEventViewModel.uiState.value is UiEvent.ShowLoading

    init {
        _authState.value = authState.value.copy(text = "Halllooo")
    }

    fun onEvent(event: DebugEvent) {
        when (event) {
            is DebugEvent.OnSwitchAuthorized -> onSwitchAuthorized()
            is DebugEvent.OnSwitchShowLoading -> showLoading()
        }
    }

    private fun showLoading() {
        if (showLoading) {
            UiEventViewModel.onEvent(UiEvent.Idle)
            showLoading = false
        } else {
            UiEventViewModel.onEvent(UiEvent.ShowLoading)
            showLoading = true
        }
    }

    private fun onSwitchAuthorized() {
        println("DebugViewModel::onSwitchAuthorized")
        _authState.value = authState.value.copy(auth = !_authState.value.auth)
        println("onSwitchAuthorized::${authState.value}")
    }
}

sealed class DebugEvent {
    object OnSwitchAuthorized : DebugEvent()
    object OnSwitchShowLoading: DebugEvent()
}

data class DebugListData(
    val auth: Boolean = false,
    val text: String = ""
)
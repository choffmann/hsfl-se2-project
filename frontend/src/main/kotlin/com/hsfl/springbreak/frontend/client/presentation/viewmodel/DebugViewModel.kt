package com.hsfl.springbreak.frontend.client.presentation.viewmodel

import com.hsfl.springbreak.frontend.client.presentation.state.AuthEvent
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DebugViewModel(private val authorizedState: AuthState) {
    val authState: StateFlow<Boolean> = authorizedState.authorized

    private var _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    init {
        MainScope().launch {
            UiEventState.uiState.collectLatest {
                println("DebugViewModel::collectLatest::${it is UiEvent.ShowLoading}")
                _showLoading.value = it is UiEvent.ShowLoading
            }
        }
    }

    fun onEvent(event: DebugEvent) {
        when (event) {
            is DebugEvent.OnSwitchAuthorized -> onSwitchAuthorized()
            is DebugEvent.OnSwitchShowLoading -> showLoading()
            is DebugEvent.OnThrowError -> throwError(event.msg)
            is DebugEvent.SendMessage -> senMessage(event.msg)
        }
    }

    private fun throwError(msg: String) {
        UiEventState.onEvent(UiEvent.ShowError(msg))
    }
    private fun senMessage(msg: String) {
        UiEventState.onEvent(UiEvent.ShowMessage(msg))
    }

    private fun showLoading() {
        if (showLoading.value) {
            UiEventState.onEvent(UiEvent.Idle)
        } else {
            UiEventState.onEvent(UiEvent.ShowLoading)
        }
    }

    private fun onSwitchAuthorized() {
        if (authState.value) {
            authorizedState.onEvent(AuthEvent.IsUnauthorized)
        } else {
            authorizedState.onEvent(AuthEvent.IsAuthorized)
        }
    }
}

sealed class DebugEvent {
    object OnSwitchAuthorized : DebugEvent()
    object OnSwitchShowLoading : DebugEvent()
    data class OnThrowError(val msg: String) : DebugEvent()
    data class SendMessage(val msg: String): DebugEvent()
}
package com.hsfl.springbreak.frontend.client.viewmodel

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

object DebugViewModel {
    val authState: StateFlow<Boolean> = AuthViewModel.authorized

    private var _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    init {
        MainScope().launch {
            UiEventViewModel.uiState.collectLatest {
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
        UiEventViewModel.onEvent(UiEvent.ShowError(msg))
    }
    private fun senMessage(msg: String) {
        UiEventViewModel.onEvent(UiEvent.ShowMessage(msg))
    }

    private fun showLoading() {
        if (showLoading.value) {
            UiEventViewModel.onEvent(UiEvent.Idle)
        } else {
            UiEventViewModel.onEvent(UiEvent.ShowLoading)
        }
    }

    private fun onSwitchAuthorized() {
        if (authState.value) {
            AuthViewModel.onEvent(AuthEvent.IsUnauthorized)
        } else {
            AuthViewModel.onEvent(AuthEvent.IsAuthorized)
        }
    }
}

sealed class DebugEvent {
    object OnSwitchAuthorized : DebugEvent()
    object OnSwitchShowLoading : DebugEvent()
    data class OnThrowError(val msg: String) : DebugEvent()
    data class SendMessage(val msg: String): DebugEvent()
}
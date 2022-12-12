package com.hsfl.springbreak.frontend.client.presentation.viewmodel

import com.hsfl.springbreak.frontend.client.presentation.state.AuthEvent
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.di.di
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance
import web.storage.localStorage

object NavViewModel {
    private val _loginButtonClicked = MutableStateFlow(false)
    val authState: AuthState by di.instance()

    fun onEvent(event: NavEvent) {
        when (event) {
            is NavEvent.OnOpenLoginDialog -> onOpenLoginDialog()
            is NavEvent.OnCloseLoginDialog -> onCloseLoginDialog()
            is NavEvent.OnLogout -> onLogout()
        }
    }

    private fun onOpenLoginDialog() = MainScope().launch {
        _loginButtonClicked.emit(true)
    }

    private fun onCloseLoginDialog() {
        _loginButtonClicked.value = false
    }

    private fun onLogout() {
        authState.onEvent(AuthEvent.IsUnauthorized)
        localStorage.clear()
        UiEventState.onEvent(UiEvent.ShowMessage("Du wurdest erfolgreich abgemeldet"))
    }
}

sealed class NavEvent {
    object OnOpenLoginDialog : NavEvent()
    object OnCloseLoginDialog : NavEvent()
    object OnLogout : NavEvent()
}
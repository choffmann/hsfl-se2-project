package com.hsfl.springbreak.frontend.client.presentation.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthState {
    private val _authorized = MutableStateFlow(true)
    val authorized: StateFlow<Boolean> = _authorized

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.IsAuthorized -> _authorized.value = true
            is AuthEvent.IsUnauthorized -> _authorized.value = false
        }
    }
}

sealed class AuthEvent {
    object IsAuthorized: AuthEvent()
    object IsUnauthorized: AuthEvent()
}

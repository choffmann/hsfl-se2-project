package com.hsfl.springbreak.frontend.client.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AuthViewModel {
    private val _authorized = MutableStateFlow(false)
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

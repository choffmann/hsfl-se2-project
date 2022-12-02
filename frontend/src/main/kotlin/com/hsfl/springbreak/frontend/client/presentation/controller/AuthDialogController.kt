package com.hsfl.springbreak.frontend.client.presentation.controller

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthDialogController(
    private val loginViewModel: LoginViewModel,
    private val registerViewModel: RegisterViewModel
) {
    private val _loginDialogOpen = MutableStateFlow(false)
    val loginDialogOpen: StateFlow<Boolean> = _loginDialogOpen

    private val _registerDialogOpen = MutableStateFlow(false)
    val registerDialogOpen: StateFlow<Boolean> = _registerDialogOpen

    fun onEvent(event: AuthDialogControllerEvent) {
        when (event) {
            is AuthDialogControllerEvent.OpenLoginDialog -> openLoginDialog()
            is AuthDialogControllerEvent.OpenRegisterDialog -> openRegisterDialog()
            is AuthDialogControllerEvent.OnCloseLoginDialog -> closeLoginDialog()
            is AuthDialogControllerEvent.OnCloseRegisterDialog -> closeRegisterDialog()
            is AuthDialogControllerEvent.OnLogin -> {}
            is AuthDialogControllerEvent.OnRegister -> {}
        }
    }

    private fun openLoginDialog() {
        _loginDialogOpen.value = true
    }

    private fun closeLoginDialog() {
        _loginDialogOpen.value = false
    }

    private fun openRegisterDialog() {
        closeLoginDialog()
        _registerDialogOpen.value = true
    }

    private fun closeRegisterDialog() {
        _registerDialogOpen.value = false
    }
}

sealed class AuthDialogControllerEvent {
    object OpenLoginDialog : AuthDialogControllerEvent()
    object OpenRegisterDialog : AuthDialogControllerEvent()
    data class OnCloseLoginDialog(val event: dynamic, val reason: String) : AuthDialogControllerEvent()
    data class OnCloseRegisterDialog(val event: dynamic, val reason: String) : AuthDialogControllerEvent()
    object OnLogin : AuthDialogControllerEvent()
    object OnRegister : AuthDialogControllerEvent()
}
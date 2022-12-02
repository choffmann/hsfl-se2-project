package com.hsfl.springbreak.frontend.client.presentation.controller

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthController(
    private val loginViewModel: LoginViewModel,
    private val registerViewModel: RegisterViewModel
) {
    private val _loginDialogOpen = MutableStateFlow(false)
    val loginDialogOpen: StateFlow<Boolean> = _loginDialogOpen

    private val _registerDialogOpen = MutableStateFlow(false)
    val registerDialogOpen: StateFlow<Boolean> = _registerDialogOpen

    fun onEvent(event: LoginControllerEvent) {
        when (event) {
            is LoginControllerEvent.OpenLoginDialog -> openLoginDialog()
            is LoginControllerEvent.OpenRegisterDialog -> openRegisterDialog()
            is LoginControllerEvent.OnCloseLoginDialog -> closeLoginDialog()
            is LoginControllerEvent.OnCloseRegisterDialog -> closeRegisterDialog()
            is LoginControllerEvent.OnLogin -> {}
            is LoginControllerEvent.OnRegister -> {}
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

sealed class LoginControllerEvent {
    object OpenLoginDialog : LoginControllerEvent()
    object OpenRegisterDialog : LoginControllerEvent()
    data class OnCloseLoginDialog(val event: dynamic, val reason: String) : LoginControllerEvent()
    data class OnCloseRegisterDialog(val event: dynamic, val reason: String) : LoginControllerEvent()
    object OnLogin : LoginControllerEvent()
    object OnRegister : LoginControllerEvent()
}
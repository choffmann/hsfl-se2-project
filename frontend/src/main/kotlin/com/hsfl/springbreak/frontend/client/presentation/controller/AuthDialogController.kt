package com.hsfl.springbreak.frontend.client.presentation.controller

import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import web.file.File

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
            is AuthDialogControllerEvent.OnLogin -> onLogin(event.email, event.password)
            is AuthDialogControllerEvent.OnRegister -> onRegister(
                firstName = event.firstName,
                lastName = event.lastName,
                email = event.email,
                password = event.password,
                confirmedPassword = event.confirmedPassword,
                profileImage = event.profileImage
            )
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

    private fun onLogin(email: String, password: String) {
        loginViewModel.login(User.Login(email, password))
    }

    private fun onRegister(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmedPassword: String,
        profileImage: File?
    ) {
        registerViewModel.register(
            user = User.Register(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
            ),
            confirmedPassword = confirmedPassword,
            profileImage = profileImage
        )
    }
}

sealed class AuthDialogControllerEvent {
    object OpenLoginDialog : AuthDialogControllerEvent()
    object OpenRegisterDialog : AuthDialogControllerEvent()
    data class OnCloseLoginDialog(val event: dynamic, val reason: String) : AuthDialogControllerEvent()
    data class OnCloseRegisterDialog(val event: dynamic, val reason: String) : AuthDialogControllerEvent()
    data class OnLogin(val email: String, val password: String) : AuthDialogControllerEvent()
    data class OnRegister(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String,
        val confirmedPassword: String,
        val profileImage: File?
    ) : AuthDialogControllerEvent()
}
package com.hsfl.springbreak.frontend.client.viewmodel

import com.hsfl.springbreak.frontend.client.model.User
import com.hsfl.springbreak.frontend.client.usecases.LoginUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) {
    private val _emailText = MutableStateFlow("")
    val emailText: StateFlow<String> = _emailText

    private val _passwordText = MutableStateFlow("")
    val passwordText: StateFlow<String> = _passwordText

    private val _openDialog = MutableStateFlow(false)
    val openDialog: StateFlow<Boolean> = _openDialog

    companion object {
        private val _openRegisterDialog = MutableStateFlow(false)
        val openRegisterDialog: StateFlow<Boolean> = _openRegisterDialog

        fun closeRegisterDialog() {
            _openRegisterDialog.value = false
        }
    }


    init {
        MainScope().launch {
            NavViewModel.loginButtonClicked.collectLatest { _openDialog.value = it }
        }
    }


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> _emailText.value = event.value
            is LoginEvent.EnteredPassword -> _passwordText.value = event.value
            is LoginEvent.OnLogin -> onLogin()
            is LoginEvent.OnRegister -> onRegister()
            is LoginEvent.OnCloseDialog -> onCloseDialog()
            is LoginEvent.OnCloseRegisterDialog -> _openRegisterDialog.value = false
        }
    }

    private fun onLogin() = MainScope().launch {
        loginUseCase(User.Login(email = emailText.value, password = passwordText.value)).collect { response ->
            response.handleDataResponse<User>(
                onSuccess = {
                    println(it)
                    AuthViewModel.onEvent(AuthEvent.IsAuthorized)
                    onCloseDialog()
                    UiEventViewModel.onEvent(UiEvent.Idle)
                },
                onUnauthorized = { println(it) })
        }
    }

    private fun onRegister() {
        onCloseDialog()
        _openRegisterDialog.value = true
    }

    private fun onCloseDialog() {
        NavViewModel.onEvent(NavEvent.OnCloseLoginDialog)
    }
}

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object OnLogin : LoginEvent()
    object OnRegister : LoginEvent()
    object OnCloseRegisterDialog : LoginEvent()
    data class OnCloseDialog(val event: dynamic, val reason: String) : LoginEvent()
}
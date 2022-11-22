package com.hsfl.springbreak.frontend.client.viewmodel

import com.hsfl.springbreak.common.User
import com.hsfl.springbreak.frontend.client.usecases.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import react.redux.useDispatch

class LoginViewModel(
    private val loginUseCase: LoginUseCase, private val scope: CoroutineScope
) {
    private val _emailText = MutableStateFlow("")
    val emailText: StateFlow<String> = _emailText

    private val _passwordText = MutableStateFlow("")
    val passwordText: StateFlow<String> = _passwordText

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> _emailText.value = event.value
            is LoginEvent.EnteredPassword -> _emailText.value = event.value
            is LoginEvent.OnLogin -> onLogin()
            is LoginEvent.OnRegister -> {}
            is LoginEvent.OnCloseDialog -> {}
        }
    }

    fun onLogin() = scope.launch {
        loginUseCase(User.Login(email = emailText.value, password = passwordText.value)).collect { response ->
            response.handleDataResponse<User>(
                onLoading = { println("Loading...") },
                onSuccess = { println(it) },
                onError = { println("Error") },
                onUnauthorized = { println("Unauthorized") })
        }
    }
}

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object OnLogin : LoginEvent()
    object OnRegister : LoginEvent()
    object OnCloseDialog : LoginEvent()
}
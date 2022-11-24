package com.hsfl.springbreak.frontend.client.viewmodel

import com.hsfl.springbreak.frontend.client.model.User
import com.hsfl.springbreak.frontend.client.usecases.LoginUseCase
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class LoginViewModel(
    private val loginUseCase: LoginUseCase, private val scope: CoroutineScope
) {
    private val _emailText = MutableStateFlow("")
    val emailText: StateFlow<String> = _emailText

    private val _passwordText = MutableStateFlow("")
    val passwordText: StateFlow<String> = _passwordText


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> { _emailText.value = event.value }
            is LoginEvent.EnteredPassword -> _passwordText.value = passwordText.value
            is LoginEvent.OnLogin -> onLogin()
            is LoginEvent.OnRegister -> onRegister()
            is LoginEvent.OnCloseDialog -> onCloseDialog()
        }
    }

    private fun onLogin() = scope.launch {
        loginUseCase(User.Login(email = emailText.value, password = passwordText.value)).collect { response ->
            response.handleDataResponse<User>(
                onLoading = { println("Loading...") },
                onSuccess = { println(it) },
                onError = { println(it) },
                onUnauthorized = { println(it) })
        }

    }

    private fun onRegister() = scope.launch {
        //println("LoginViewModel::onRegister()")
        val response = window
            .fetch("http://localhost:8080/test")
            .await()
            .text()
            .await()
        println(response)
        val j: User.Response = Json.decodeFromString(response)
        println(j)
    }

    fun onCloseDialog() {
        println("LoginViewModel::onCloseDialog()")
    }
}

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object OnLogin : LoginEvent()
    object OnRegister : LoginEvent()
    object OnCloseDialog : LoginEvent()
}
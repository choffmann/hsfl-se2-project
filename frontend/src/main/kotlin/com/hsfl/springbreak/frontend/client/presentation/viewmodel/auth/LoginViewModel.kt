package com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth

import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val scope: CoroutineScope = MainScope()
) {

    fun onEvent(event: LoginEvent) {

    }

    fun login(user: User.Login) = scope.launch {
        userRepository.login(user).collect { response ->
            response.handleDataResponse<User>(
                onSuccess = { println(it) },
                onUnauthorized = { println("LoginViewModel::Unauthorized") }
            )
        }
    }

    private fun validateEmail() {
        TODO("validate Email")
    }
}

sealed class LoginEvent {
    object OnLogin : LoginEvent()
}

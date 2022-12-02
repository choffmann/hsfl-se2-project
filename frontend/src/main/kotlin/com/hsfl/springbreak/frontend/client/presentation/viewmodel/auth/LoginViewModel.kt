package com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth

import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val scope: CoroutineScope = MainScope()
) {
    private val _emailText = MutableStateFlow("")
    val emailText: StateFlow<String> = _emailText

    private val _passwordText = MutableStateFlow("")
    val passwordText: StateFlow<String> = _passwordText

    fun login(user: User.Login) = scope.launch {
        userRepository.login(user).collect {
            it.handleDataResponse<User>(
                onSuccess = { println("AuthViewModel::Success") },
                onUnauthorized = { println("AuthViewModel::Unauthorized") }
            )
        }
    }

    private fun validateEmail() {
        TODO("validate Email")
    }
}

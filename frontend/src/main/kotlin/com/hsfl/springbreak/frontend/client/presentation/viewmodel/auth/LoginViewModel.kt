package com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth

import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.data.repository.UserRepository
import com.hsfl.springbreak.frontend.client.presentation.state.AuthEvent
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.di.di
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.kodein.di.instance

class LoginViewModel(
    private val userRepository: UserRepository,
    private val scope: CoroutineScope = MainScope()
) {
    fun login(user: User.Login) = scope.launch {
        userRepository.login(user).collect { response ->
            response.handleDataResponse<User>(
                onSuccess = {
                    val authState: AuthState by di.instance()
                    authState.onEvent(AuthEvent.IsAuthorized)
                    UiEventState.onEvent(UiEvent.ShowMessage(it.toString()))
                },
                onUnauthorized = { println("LoginViewModel::Unauthorized") }
            )
        }
    }

    private fun validateEmail() {
        TODO("validate Email")
    }
}

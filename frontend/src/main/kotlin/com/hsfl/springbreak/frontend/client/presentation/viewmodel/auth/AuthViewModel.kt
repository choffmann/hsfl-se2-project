package com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth

import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class AuthViewModel<T: AuthViewModelEvent>(
    _userRepository: UserRepository,
    _scope: CoroutineScope = MainScope()
) {
    private val userRepository = _userRepository
    private val scope = _scope

    abstract fun onEvent(event: T)
}

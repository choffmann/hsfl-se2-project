package com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth

import com.hsfl.springbreak.frontend.client.data.repository.UserRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
): AuthViewModel<RegisterEvent>(
    _userRepository = userRepository
) {
    private val _openDialog = MutableStateFlow(false)
    val openDialog: StateFlow<Boolean> = _openDialog

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnOpenDialog -> openDialog()
            is RegisterEvent.OnCloseDialog -> closeDialog()
        }
    }

    private fun openDialog() {
        _openDialog.value = true
    }

    private fun closeDialog() {
        _openDialog.value = false
    }
}

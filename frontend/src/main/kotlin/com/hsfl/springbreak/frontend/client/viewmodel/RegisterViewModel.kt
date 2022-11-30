package com.hsfl.springbreak.frontend.client.viewmodel

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterViewModel {
    private val _openDialog = MutableStateFlow(false)
    val openDialog: StateFlow<Boolean> = _openDialog

    init {
        MainScope().launch {
            LoginViewModel.openRegisterDialog.collectLatest { _openDialog.value = it }
        }
    }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnCloseDialog -> onCloseDialog()
        }
    }

    private fun onCloseDialog() {
        LoginViewModel.closeRegisterDialog()
    }
}

sealed class RegisterEvent {
    //object OnRegister : RegisterEvent()
    data class OnCloseDialog(val event: dynamic, val reason: String) : RegisterEvent()
}
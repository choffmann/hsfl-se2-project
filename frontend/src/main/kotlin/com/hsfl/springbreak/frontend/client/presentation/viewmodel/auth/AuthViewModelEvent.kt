package com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth

sealed interface AuthViewModelEvent

sealed class LoginEvent : AuthViewModelEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object OnLogin : LoginEvent()
    object OnRegister : LoginEvent()
    object OnOpenDialog : LoginEvent()
    data class OnCloseDialog(val event: dynamic, val reason: String) : LoginEvent()
}

sealed class RegisterEvent : AuthViewModelEvent {
    object OnOpenDialog : RegisterEvent()
    data class OnCloseDialog(val event: dynamic, val reason: String) : RegisterEvent()
}
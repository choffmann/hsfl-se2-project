package com.hsfl.springbreak.frontend.client.presentation.viewmodel

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object NavViewModel {

    private val _loginButtonClicked = MutableStateFlow(false)
    val loginButtonClicked: StateFlow<Boolean> = _loginButtonClicked

    fun onEvent(event: NavEvent) {
        when (event) {
            is NavEvent.OnOpenLoginDialog -> onOpenLoginDialog()
            is NavEvent.OnCloseLoginDialog -> onCloseLoginDialog()
            is NavEvent.OnCategory -> TODO()
            is NavEvent.OnFavorites -> TODO()
            is NavEvent.OnMyRecipes -> TODO()
            is NavEvent.OnCreateRecipes -> TODO()
            is NavEvent.OnProfile -> TODO()
            is NavEvent.OnSettings -> TODO()
            is NavEvent.OnLogout -> onLogout()
        }
    }

    private fun onOpenLoginDialog() = MainScope().launch {
        _loginButtonClicked.emit(true)
    }

    private fun onCloseLoginDialog() {
        _loginButtonClicked.value = false
    }

    private fun onLogout() {
        AuthViewModel.onEvent(AuthEvent.IsUnauthorized)
        UiEventViewModel.onEvent(UiEvent.ShowMessage("You have been successfully logged out"))
    }
}

sealed class NavEvent {
    object OnOpenLoginDialog: NavEvent()
    object OnCloseLoginDialog: NavEvent()
    object OnCategory: NavEvent()
    object OnFavorites: NavEvent()
    object OnMyRecipes: NavEvent()
    object OnCreateRecipes: NavEvent()
    object OnProfile: NavEvent()
    object OnSettings: NavEvent()
    object OnLogout: NavEvent()
}
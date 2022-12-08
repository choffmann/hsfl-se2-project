package com.hsfl.springbreak.frontend.client.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel {
    private val _firstNameState = MutableStateFlow("")
    val firstNameState: StateFlow<String> = _firstNameState

    private val _lastNameState = MutableStateFlow("")
    val lastNameState: StateFlow<String> = _lastNameState

    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState

    private val _passwordState = MutableStateFlow("")
    val passwordState: StateFlow<String> = _passwordState

    private val _confirmedPasswordState = MutableStateFlow("")
    val confirmedPasswordState: StateFlow<String> = _confirmedPasswordState
}
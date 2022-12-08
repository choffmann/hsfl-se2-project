package com.hsfl.springbreak.frontend.client.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import web.file.File

class ProfileViewModel {
    private val _editMode = MutableStateFlow(false)
    val editMode: StateFlow<Boolean> = _editMode

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

    private val _profileImage = MutableStateFlow<File?>(null)
    val profileImage: StateFlow<File?> = _profileImage

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.FirstNameChanged -> _firstNameState.value = event.value
            is ProfileEvent.LastNameChanged -> _lastNameState.value = event.value
            is ProfileEvent.EmailChanged -> _emailState.value = event.value
            is ProfileEvent.PasswordChanged -> _passwordState.value = event.value
            is ProfileEvent.ConfirmedPasswordChanged -> _confirmedPasswordState.value = event.value
            is ProfileEvent.ProfileImageChanged -> _profileImage.value = event.file
            is ProfileEvent.OnAbort -> exitEditMode()
            is ProfileEvent.OnEdit -> enterEditMode()
            is ProfileEvent.OnSave -> saveEditValues()
        }
    }

    private fun enterEditMode() {
        _editMode.value = true
    }

    private fun exitEditMode() {
        _editMode.value = false
    }

    private fun saveEditValues() {
        TODO()
    }

}

sealed class ProfileEvent {
    object OnEdit : ProfileEvent()
    object OnSave : ProfileEvent()
    object OnAbort : ProfileEvent()
    data class FirstNameChanged(val value: String) : ProfileEvent()
    data class LastNameChanged(val value: String) : ProfileEvent()
    data class EmailChanged(val value: String) : ProfileEvent()
    data class PasswordChanged(val value: String) : ProfileEvent()
    data class ConfirmedPasswordChanged(val value: String) : ProfileEvent()
    data class ProfileImageChanged(val file: File) : ProfileEvent()
}
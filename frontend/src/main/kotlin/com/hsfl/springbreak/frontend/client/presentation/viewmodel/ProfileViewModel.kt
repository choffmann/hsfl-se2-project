package com.hsfl.springbreak.frontend.client.presentation.viewmodel

import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.state.UserStateEvent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import web.file.File

class ProfileViewModel(
    private val userState: UserState
) {
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

    private val _profileImage = MutableStateFlow("")
    val profileImage: StateFlow<String> = _profileImage

    private val _selectedProfileImage = MutableStateFlow<File?>(null)
    val selectedProfileImage: StateFlow<File?> = _selectedProfileImage

    init {
        setFlowsToUser()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.FirstNameChanged -> _firstNameState.value = event.value
            is ProfileEvent.LastNameChanged -> _lastNameState.value = event.value
            is ProfileEvent.EmailChanged -> _emailState.value = event.value
            is ProfileEvent.PasswordChanged -> _passwordState.value = event.value
            is ProfileEvent.ConfirmedPasswordChanged -> _confirmedPasswordState.value = event.value
            is ProfileEvent.ProfileImageChanged -> _selectedProfileImage.value = event.file
            is ProfileEvent.OnAbort -> exitEditMode()
            is ProfileEvent.OnEdit -> enterEditMode()
            is ProfileEvent.OnSave -> saveEditValues()
        }
    }

    private fun setFlowsToUser() = MainScope().launch {
        userState.userState.collect {
            _firstNameState.value = it.firstName
            _lastNameState.value = it.lastName
            _emailState.value = it.email
            _passwordState.value = it.password
            _confirmedPasswordState.value = it.password
            _profileImage.value = it.image ?: ""
        }
    }

    private fun enterEditMode() {
        _editMode.value = true
    }

    private fun exitEditMode() {
        _editMode.value = false
        setFlowsToUser()
    }

    private fun validateConfirmedPassword(): Boolean {
        return _passwordState.value == _confirmedPasswordState.value
        // TODO: Show error in UI
    }

    private fun saveEditValues() {
        // TODO: Send to backend
        userState.onEvent(UserStateEvent.UpdateUser(User.State(
            firstName = _firstNameState.value,
            lastName = _lastNameState.value,
            email = _emailState.value,
            password = _passwordState.value,
            // TODO: Has to be the new URL from the backend when the profile image is uploaded
            image = _profileImage.value
        )))
        exitEditMode()

        // TODO: On Success
        showMessage("Dein Profil wurde erfolgreich aktualisiert")
    }

    private fun showMessage(msg: String) {
        MessageViewModel.onEvent(SnackbarEvent.Show(msg))
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
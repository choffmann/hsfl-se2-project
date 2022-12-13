package com.hsfl.springbreak.frontend.client.presentation.viewmodel

import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.data.repository.UserRepository
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.state.UserStateEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.ProfileEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import web.file.File
import web.storage.localStorage

class ProfileViewModel(
    private val userState: UserState,
    private val userRepository: UserRepository,
    private val scope: CoroutineScope = MainScope()
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
            is ProfileEvent.OnSave -> if (validateConfirmedPassword()) saveEditValues()
            LifecycleEvent.OnMount -> setFlowsToUser()
            LifecycleEvent.OnUnMount -> {}
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

    private fun saveEditValues() = scope.launch {
        // TODO: Send to backend
        userRepository.updateUser(
            User.UpdateProfile(
                id = userState.userState.value.id,
                firstName = firstNameState.value,
                lastName = lastNameState.value,
                password = if(passwordState.value.isNotEmpty()) passwordState.value else null
            )
        ).collectLatest { response ->
            response.handleDataResponse<User>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.Idle)
                    savetoUserState(it)
                    updateLocalStorage(it)
                    exitEditMode()
                    showMessage("Dein Profil wurde erfolgreich aktualisiert")
                }
            )
        }
    }

    private fun showMessage(msg: String) {
        MessageViewModel.onEvent(SnackbarEvent.Show(msg))
    }

    private fun savetoUserState(user: User) {
        userState.onEvent(
            UserStateEvent.UpdateUser(
                User.State(
                    firstName = user.firstName,
                    lastName = user.lastName,
                    email = user.email,
                    password = user.password,
                    // TODO: Has to be the new URL from the backend when the profile image is uploaded
                    image = user.image
                )
            )
        )
    }

    private fun updateLocalStorage(user: User) {
        localStorage.setItem("userId", user.id.toString())
        localStorage.setItem("userFirstName", user.firstName)
        localStorage.setItem("userLastName", user.lastName)
        localStorage.setItem("userEmail", user.email)
        localStorage.setItem("userImage", user.image ?: "")
    }

}

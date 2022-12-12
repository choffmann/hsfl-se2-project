package com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth

import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.data.repository.UserRepository
import com.hsfl.springbreak.frontend.client.presentation.state.*
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.AuthDialogEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.di.di
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance
import web.file.File
import web.storage.localStorage

class AuthDialogViewModel(
    private val userRepository: UserRepository,
    private val scope: CoroutineScope = MainScope()

) {
    private val _loginDialogOpen = MutableStateFlow(false)
    val loginDialogOpen: StateFlow<Boolean> = _loginDialogOpen

    private val _registerDialogOpen = MutableStateFlow(false)
    val registerDialogOpen: StateFlow<Boolean> = _registerDialogOpen

    private val _passwordTextConfirmation = MutableStateFlow(RegisterPasswordTextState())
    val passwordTextConfirmation: StateFlow<RegisterPasswordTextState> = _passwordTextConfirmation

    fun onEvent(event: AuthDialogEvent) {
        when (event) {
            is AuthDialogEvent.OpenLoginDialog -> openLoginDialog()
            is AuthDialogEvent.OpenRegisterDialog -> openRegisterDialog()
            is AuthDialogEvent.OnCloseLoginDialog -> closeLoginDialog()
            is AuthDialogEvent.OnCloseRegisterDialog -> closeRegisterDialog()
            is AuthDialogEvent.OnLogin -> onLogin(event.email, event.password)
            is AuthDialogEvent.OnRegister -> onRegister(
                firstName = event.firstName,
                lastName = event.lastName,
                email = event.email,
                password = event.password,
                confirmedPassword = event.confirmedPassword,
                profileImage = event.profileImage
            )

            LifecycleEvent.OnMount -> TODO()
            LifecycleEvent.OnUnMount -> cleanStates()
        }
    }

    private fun cleanStates() {
        _loginDialogOpen.value = false
        _registerDialogOpen.value = false
        _passwordTextConfirmation.value = RegisterPasswordTextState()
    }

    private fun openLoginDialog() {
        _loginDialogOpen.value = true
    }

    private fun closeLoginDialog() {
        _loginDialogOpen.value = false
    }

    private fun openRegisterDialog() {
        closeLoginDialog()
        _registerDialogOpen.value = true
    }

    private fun closeRegisterDialog() {
        _registerDialogOpen.value = false
    }

    private fun onLogin(email: String, password: String) = scope.launch {
        userRepository.login(User.Login(email, password)).collect { response ->
            response.handleDataResponse<User>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.Idle)
                    closeLoginDialog()
                    saveUserLocal(it)
                }
            )
        }
    }

    private fun onRegister(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmedPassword: String,
        profileImage: File?
    ) {
        val registerUser = User.Register(firstName, lastName, email, password)
        if (validateRegisterPassword(password, confirmedPassword)) {
            _passwordTextConfirmation.value = passwordTextConfirmation.value.copy(error = false)
            sendRegisterRequest(registerUser, profileImage)
        } else {
            _passwordTextConfirmation.value =
                passwordTextConfirmation.value.copy(error = true, message = "Das Kennwort stimmt nicht überein")
        }
    }

    private fun validateRegisterPassword(enteredPassword: String, confirmedPassword: String): Boolean {
        return enteredPassword == confirmedPassword
    }

    private fun sendRegisterRequest(registerUser: User.Register, profileImage: File?) = scope.launch {
        userRepository.register(registerUser).collect { response ->
            response.handleDataResponse<User>(
                onSuccess = { user ->
                    UiEventState.onEvent(UiEvent.Idle)
                    if(profileImage == null) {
                        saveUserLocal(user)
                        UiEvent.ShowMessage("Dein Account wurde erfolgreich registriert")
                        closeRegisterDialog()
                    } else {
                        uploadProfileImage(user.id, profileImage)
                    }
                }
            )
        }
    }

    private fun uploadProfileImage(userId: Int, profileImage: File) = scope.launch {
        userRepository.uploadProfileImage(userId, profileImage).collect { response ->
            response.handleDataResponse<User.Image>(
                onSuccess = {
                    UiEvent.ShowMessage("Dein Account wurde erfolgreich registriert")
                    closeRegisterDialog()
                }
            )
        }
    }

    private fun saveUserLocal(user: User) {
        val userState: UserState by di.instance()
        userState.onEvent(UserStateEvent.SetUser(user))
        setLocalStorage(user)
    }

    private fun setLocalStorage(user: User) {
        val authState: AuthState by di.instance()
        authState.onEvent(AuthEvent.IsAuthorized)
        localStorage.setItem("isLoggedIn", true.toString())
        localStorage.setItem("userFirstName", user.firstName)
        localStorage.setItem("userLastName", user.lastName)
        localStorage.setItem("userEmail", user.email)
        localStorage.setItem("userImage", user.image ?: "")
    }
}


data class RegisterPasswordTextState(val error: Boolean = false, val message: String = "")
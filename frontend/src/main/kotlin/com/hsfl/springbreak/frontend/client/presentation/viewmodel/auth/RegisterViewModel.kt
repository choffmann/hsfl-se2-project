package com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth

import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.data.repository.UserRepository
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import web.file.File

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val scope: CoroutineScope = MainScope()
) {
    fun register(user: User.Register, confirmedPassword: String, profileImage: File?) = scope.launch {
        // TODO: Validate password and email
        userRepository.register(user).collect { response ->
            response.handleDataResponse<User>(
                onSuccess = {user ->
                    // TODO: Save user as state
                    UiEventState.onEvent(UiEvent.ShowMessage(user.toString()))
                    profileImage?.let { uploadProfileImage(it) }
                },
                onUnauthorized = { println(it) }
            )
        }
    }

    private fun uploadProfileImage(profileImage: File) = scope.launch {
        userRepository.uploadProfileImage(profileImage).collect { response ->
            response.handleDataResponse<User.ProfileImage>(
                onSuccess = { println(it) },
                onUnauthorized = {}
            )
        }
    }
}
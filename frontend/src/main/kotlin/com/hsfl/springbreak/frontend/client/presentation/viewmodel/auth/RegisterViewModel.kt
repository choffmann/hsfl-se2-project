package com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth

import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import web.file.File

class RegisterViewModel(
    private val userRepository: UserRepository, private val scope: CoroutineScope = MainScope()
) {
    fun register(user: User.Register, confirmedPassword: String, profileImage: File?) = scope.launch {
        println("RegisterViewModel::register")
        // TODO: Validate password and email
        userRepository.register(user, profileImage = profileImage
        ).collect { response ->
            response.handleDataResponse<User>(onSuccess = { println(it) }, onUnauthorized = { println(it) })
        }
    }
}
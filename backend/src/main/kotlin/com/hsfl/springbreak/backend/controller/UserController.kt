package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.repository.UserRepository
import com.hsfl.springbreak.common.ApiResponse
import com.hsfl.springbreak.common.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val repository: UserRepository) {

    @PostMapping("login")
    fun login(@RequestBody loginUser: User.Login): ApiResponse<User> {
        repository.findByEmail(loginUser.email)?.let {user ->
            return if (user.password == loginUser.password) {
                ApiResponse(data = user.toDto(), success = true)
            } else {
                ApiResponse(error = "Password wrong", success = false)
            }
        } ?: return ApiResponse(error = "User not found", success = false)
    }
}
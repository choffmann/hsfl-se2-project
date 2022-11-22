package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.repository.UserRepository
import com.hsfl.springbreak.common.ApiResponse
import com.hsfl.springbreak.common.Login
import com.hsfl.springbreak.common.User
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000")
@RestController
class UserController(val repository: UserRepository) {

    @PostMapping("login")
    fun login(@RequestBody loginUser: Login): ApiResponse<User> {
        repository.findByEmail(loginUser.email)?.let {user ->
            return if (user.password == loginUser.password) {
                ApiResponse(data = user.toDto(), success = true)
            } else {
                ApiResponse(error = "Email or password wrong", success = false)
            }
        } ?: return ApiResponse(error = "User not found", success = false)
    }

    @GetMapping("test")
    fun test(): ApiResponse<User> = ApiResponse(data = User(0, "C", "H", "e", "d"))
}
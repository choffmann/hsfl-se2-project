package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.repository.UserRepository
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.User
import com.hsfl.springbreak.backend.service.UserJpaService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000")
@RestController
class UserController(val repository: UserRepository, val userService: UserJpaService) {

    @PostMapping("user/register")
    fun register(@RequestBody newUser: User.Register): ApiResponse<User> =
        userService.registerUser(newUser)

    @GetMapping("user/login")
    fun login(@RequestBody loginUser: User.Login): ApiResponse<User> =
        userService.loginUser(loginUser)

    @PutMapping("user/update")
    fun updateUser(@RequestBody userChanges: User.ChangeProfile, @PathVariable userId: Long): ApiResponse<User> =
        userService.changeProfile(userChanges, userId)

    @GetMapping("user/test")
    fun test(): ApiResponse<User> = ApiResponse(data = User(0, "C", "H", "e", "d"))
}
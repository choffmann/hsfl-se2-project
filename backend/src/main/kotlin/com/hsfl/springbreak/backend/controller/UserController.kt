package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.repository.UserRepository
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.User
import com.hsfl.springbreak.backend.service.UserJpaService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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
    fun updateUser(@RequestBody userChanges: User.ChangeProfile): ApiResponse<User> =
        userService.changeProfile(userChanges)

    @PostMapping("user/image/{id}")
    fun uploadProfileImage(@RequestParam("image") file: MultipartFile, @PathVariable id: Long): ApiResponse<User> =
        userService.updateProfileImage(file.bytes, id)

}
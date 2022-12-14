package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.model.User
import com.hsfl.springbreak.backend.repository.UserRepository
import com.hsfl.springbreak.backend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@CrossOrigin("http://localhost:3000")
@RestController
class UserController(val repository: UserRepository, val userService: UserService) {

    @PostMapping("api/user/register")
    fun register(@RequestBody newUser: User.Register): ApiResponse<User> = userService.registerUser(newUser)

    @PostMapping("api/user/login")
    fun login(@RequestBody loginUser: User.Login): ApiResponse<User> = userService.loginUser(loginUser)

    @PutMapping("api/user/update")
    fun updateUser(@RequestBody userChanges: User.ChangeProfile): ApiResponse<User> =
        userService.changeProfile(userChanges)

    @PostMapping("api/user/image")
    fun uploadProfileImage(@RequestParam("image") file: MultipartFile, @RequestParam("id") id: Long): ApiResponse<String> {
        val filePath = userService.setProfilePicture(id, file)
        return ApiResponse(data = "http//localhost:8080/api/user/image/$id.png")
    }

        /*
        fun uploadProfileImage(@RequestParam("image") file: MultipartFile, @RequestParam id: Long): ApiResponse<User> =
        userService.updateProfileImage(file.bytes, id)
        */

    @GetMapping("api/user/image/{id}.png")
    fun getImageById(@PathVariable("id") id: Long): ResponseEntity<ByteArray> {
        val imageData = userService.getProfilePicture(id)
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.valueOf("image/png"))
            .body(imageData)
    }


    @PostMapping("api/user/favorite")
    fun setFavoriteById(@RequestParam("rId") rId: Long, @RequestParam("uId") uId: Long) =
        userService.setFavoriteById(rId, uId)

    @GetMapping("api/user/favorite")
    fun getFavoritesById(@RequestParam("id") id: Long): ApiResponse<List<Recipe.Response>> =
        userService.getFavoritesById(id)

    @DeleteMapping("api/user/favorite")
    fun deleteFavoriteById(@RequestParam("rId") rId: Long, @RequestParam("uId") uId: Long) =
        userService.deleteFavoriteById(rId, uId)
}
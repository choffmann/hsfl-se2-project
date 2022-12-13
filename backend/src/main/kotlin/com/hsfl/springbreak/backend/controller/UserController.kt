package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.repository.UserRepository
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
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

    @PostMapping("user/login")
    fun login(@RequestBody loginUser: User.Login): ApiResponse<User> =
            userService.loginUser(loginUser)

    @PutMapping("user/update")
    fun updateUser(@RequestBody userChanges: User.ChangeProfile): ApiResponse<User> =
            userService.changeProfile(userChanges)

    @PostMapping("user/image/{id}")
    fun uploadProfileImage(@RequestParam("image") file: MultipartFile, @PathVariable id: Long): ApiResponse<User> =
            userService.updateProfileImage(file.bytes, id)

    @PostMapping("user/favorite/{rId}/{uId}")
    fun setFavoriteById(
            @PathVariable("rId") rId: Long, @PathVariable("uId") uId: Long) =
            userService.setFavoriteById(rId, uId)

    @GetMapping("user/favorite/{id}")
    fun getFavoritesById(@PathVariable("id") id: Long): ApiResponse<List<Recipe>> =
            userService.getFavoritesById(id)

//    @DeleteMapping("user/favorite/{rId}/{uId}")
//    fun deleteFavoriteById(@PathVariable("rId") rId: Long, @PathVariable("uId") uId: Long): ApiResponse<Recipe> =
//            userService.deleteFavoriteById(rId, uId)
//}
    @DeleteMapping("user/favorite")
    fun deleteFavoriteById(
            @RequestParam(value = "recipeId", required = true) recipeId:Long,
            @RequestParam(value = "userId", required = true) userId:Long): ApiResponse<Recipe> =
            userService.deleteFavoriteById(recipeId, userId)
}
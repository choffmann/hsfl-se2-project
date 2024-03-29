package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.model.User
import com.hsfl.springbreak.backend.repository.UserRepository
import com.hsfl.springbreak.backend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 * Calls user-related functions from user-service.
 */
@CrossOrigin("http://localhost:3000")
@RestController
class UserController(val repository: UserRepository, val userService: UserService) {

    /**
     * API-Endpoint for the user registration.
     * @param newUser The User-DTO with registration data from the request body
     * @return API-Response with the saved User-DTO or an error
     */
    @PostMapping("api/user/register")
    fun register(@RequestBody newUser: User.Register): ApiResponse<User> = userService.registerUser(newUser)

    /**
     * API-Endpoint for the user login.
     * @param loginUser The User-DTO with login data from the request body
     * @return API-Response with the User-DTO or an error
     */
    @PostMapping("api/user/login")
    fun login(@RequestBody loginUser: User.Login): ApiResponse<User> = userService.loginUser(loginUser)

    /**
     * API-Endpoint for updating a recipe.
     * @param userChanges The User-DTO with new changes from the request body
     * @return API-Response with the saved User-DTO or an error
     */
    @PutMapping("api/user/update")
    fun updateUser(@RequestBody userChanges: User.ChangeProfile): ApiResponse<User> =
        userService.changeProfile(userChanges)

    /**
     * API-Endpoint for setting a profile image.
     * @param file The image to be saved to database.
     * @param id The user id corresponding to the uploaded file.
     */
    @PostMapping("api/user/image")
    fun setImage(
        @RequestParam("image") file: MultipartFile, @RequestParam("id") id: Long
    ): ApiResponse<String> = userService.setImage(id, file)

    /**
     * API-Endpoint for returning a profile image.
     * @param id The users id whose profile image shall be returned.
     */
    @GetMapping("api/user/image/{id}.png")
    fun getImageById(@PathVariable("id") id: Long): ResponseEntity<ByteArray>? =
        userService.getImageById(id)


    /**
     * API-Endpoint for setting a recipe to user's favorite list.
     * @param rId The recipe's ID from the first request parameter
     * @param uId The user's ID from the second request parameter
     * @return API-Response with the saved Recipe-DTO or an error
     */
    @PostMapping("api/user/favorite")
    fun setFavoriteById(@RequestParam("rId") rId: Long, @RequestParam("uId") uId: Long): ApiResponse<Recipe.Response> =
        userService.setFavoriteById(rId, uId)

    /**
     * API-Endpoint for getting recipes from user's favorite list.
     * @param uId The user's ID from the request parameter
     * @return API-Response with the list of favorite Recipe-DTOs or an error
     */
    @GetMapping("api/user/favorite")
    fun getFavoritesById(@RequestParam("uId") uId: Long): ApiResponse<List<Recipe.Response>> =
        userService.getFavoritesById(uId)

    /**
     * API-Endpoint for deleting a recipe from user's favorite list.
     * @param rId The recipe's ID from the first request parameter
     * @param uId The user's ID from the second request parameter
     * @return API-Response with the deleted Recipe-DTO or an error
     */
    @DeleteMapping("api/user/favorite")
    fun deleteFavoriteById(
        @RequestParam("rId") rId: Long, @RequestParam("uId") uId: Long
    ): ApiResponse<Recipe.Response> = userService.deleteFavoriteById(rId, uId)
}
package com.hsfl.springbreak.backend.service

import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.entity.UserEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.model.User
import com.hsfl.springbreak.backend.repository.RecipeRepository
import com.hsfl.springbreak.backend.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.transaction.Transactional

@Service
@Transactional
class UserService(val userRepository: UserRepository, val recipeRepository: RecipeRepository) {

    /**
     * Creates a new User.
     * @param user The user to be created from type User.Register(firstName, lastName, email, password).
     */
    fun registerUser(user: User.Register): ApiResponse<User> {
        return ApiResponse(data = userRepository.save(UserEntity.fromDto(user)).toDto(), success = true)
    }

    /**
     * Checks credentials for a given user.
     * @param user The user to be logged in from Type User.Login(email, password).
     */
    fun loginUser(user: User.Login): ApiResponse<User> {
        val userProxy = userRepository.findByEmail(user.email)
        return if ((userProxy != null) && (userProxy.password == user.password)) {
            ApiResponse(data = userProxy.toDto(), success = true)
        } else {
            ApiResponse(error = "User not found", success = false)
        }
    }

    /**
     * Update user-properties: firstname, lastname, password.
     * @param user The updated user from type ChangeProfile.
     */
    fun changeProfile(user: User.ChangeProfile): ApiResponse<User> {
        return if (userRepository.existsById(user.id)) {
            ApiResponse(
                data = userRepository.save(UserEntity.fromDto(user, userRepository.findById(user.id).get())).toDto(),
                success = true
            )
        } else {
            ApiResponse(error = "User not found", success = false)
        }

    }

    /**
     * Saves a profile image to server and return the URL to the image.
     * @param file The image to be saved to database.
     * @param id The user id corresponding to the uploaded file.
     */
    fun setImage(id: Long, file: MultipartFile): ApiResponse<String> {
        return if (userRepository.existsById(id)) {

            // Creating the path where the image should be saved
            val filePath = Paths.get("").toAbsolutePath().toString() + "/backend/src/main/resources/userProfiles/$id"

            // Save the image to the userprofile folder
            file.transferTo(File(filePath))

            // Get the user entity and safe the local path in the image attribute
            val user = userRepository.findById(id).get()
            user.image = filePath
            userRepository.save(user)

            // Return the URL Path where the image can be fetched
            ApiResponse(data = "http://localhost:8080/api/user/image/$id.png", success = true)
        } else {
            ApiResponse(error = "Invalid user ID", success = false)
        }
    }

    /**
     * Returns a ByteArray o
     * @param id The user's id whose profile image shall be returned.
     */
    fun getImageById(id: Long): ResponseEntity<ByteArray>? {
        return if (userRepository.existsById(id)) {
            val userProxy = userRepository.findById(id).get()
            if(userProxy.image != null) {
                val byteArray = Files.readAllBytes(File(userProxy.image!!).toPath())
                ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(byteArray)
            } else {
                ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(null)
            }
        } else {
            ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(null)
        }
    }

    /**
     * Add a new entity to the User-Favorite relation.
     * @param rId The referenced recipe's id.
     * @param uId The referenced user's id.
     */
    fun setFavoriteById(rId: Long, uId: Long): ApiResponse<Recipe.Response> {
        return if (recipeRepository.existsById(rId) && userRepository.existsById(uId)) {
            val userProxy = userRepository.findById(uId).get()
            val recipeProxy = recipeRepository.findById(rId).get()
            userProxy.favorites.add(recipeProxy)
            userRepository.save(userProxy)
            ApiResponse(data = recipeProxy.toResponse(), success = true)
        } else {
            ApiResponse(error = "Invalid user recipe combination", success = false)
        }
    }

    /**
     * Returns a List of a users favorite recipes.
     * @param id The id of the user whose favorites shall be returned.
     */
    fun getFavoritesById(id: Long): ApiResponse<List<Recipe.Response>> {
        return if (userRepository.existsById(id)) {
            val userProxy = userRepository.findById(id).get()
            ApiResponse(data = userProxy.favorites.map { it.toResponse() }, success = true)
        } else {
            ApiResponse(error = "User not found", success = false)
        }
    }

    /**
     * Deletes a user's favorite recipe based on its id.
     * @param rId The referenced recipe's id.
     * @param uId The referenced user's id.
     */
    fun deleteFavoriteById(rId: Long, uId: Long): ApiResponse<Recipe.Response> {
        if (userRepository.existsById(uId)) {
            for (favoriteRecipe: RecipeEntity in userRepository.findById(uId).get().favorites) {
                if (favoriteRecipe.id == rId) {
                    userRepository.findById(uId).get().favorites.remove(favoriteRecipe)
                    return ApiResponse(data = favoriteRecipe.toResponse(), success = true)
                }
            }
            return ApiResponse(error = "Invalid recipe ID", success = false)
        }
        return ApiResponse(error = "Invalid user ID", success = false)
    }

}
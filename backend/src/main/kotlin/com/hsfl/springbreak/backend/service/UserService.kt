package com.hsfl.springbreak.backend.service

import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.entity.UserEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.model.User
import com.hsfl.springbreak.backend.repository.RecipeRepository
import com.hsfl.springbreak.backend.repository.UserRepository
import org.springframework.stereotype.Service
import java.sql.Blob
import javax.sql.rowset.serial.SerialBlob
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
     * Read a given file and save it to database as blob-type.
     * @param file The image to be read and set as new profile picture.
     * @param userId The user to whom the image shall be related.
     */
    fun updateProfileImage(file: ByteArray, userId: Long): ApiResponse<User> {
        return if (userRepository.existsById(userId)) {
            // fetch user from database
            val userProxy = userRepository.findById(userId).get()

            // convert file to blob
            val blob: Blob = SerialBlob(file)

            // save image to user
            userProxy.image = blob
            userRepository.save(userProxy)

            ApiResponse(data = userProxy.toDto(), success = true)
        } else {
            ApiResponse(error = "User not found", success = false)
        }
    }

    /**
     * Add a new entity to the User-Favorite relation.
     * @param rId The referenced recipe's id.
     * @param uId The referenced user's id.
     */
    fun setFavoriteById(rId: Long, uId: Long): ApiResponse<User> {
        return if (recipeRepository.existsById(rId) && userRepository.existsById(uId)) {
            val userProxy = userRepository.findById(uId).get()
            userProxy.favorites.add(recipeRepository.findById(rId).get())
            userRepository.save(userProxy)
            ApiResponse(success = true)
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
    fun deleteFavoriteById(rId: Long, uId: Long): ApiResponse<Recipe> {
        if (userRepository.existsById(uId)) {
            for (favoriteRecipe: RecipeEntity in userRepository.findById(uId).get().favorites) {
                if (favoriteRecipe.id == rId) {
                    userRepository.findById(uId).get().favorites.remove(favoriteRecipe)
                    return ApiResponse(success = true)
                }
            }
        }
        return ApiResponse(error = "Invalid user recipe combination", success = false)
    }

}
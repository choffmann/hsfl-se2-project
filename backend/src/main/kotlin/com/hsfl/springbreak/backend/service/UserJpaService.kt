package com.hsfl.springbreak.backend.service

import com.hsfl.springbreak.backend.entity.UserEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.User
import com.hsfl.springbreak.backend.repository.UserRepository
import org.springframework.stereotype.Service
import java.sql.Blob
import javax.sql.rowset.serial.SerialBlob
import javax.transaction.Transactional

@Service
@Transactional
class UserJpaService(val userRepository: UserRepository, val recipeRepository: RecipeRepository) {

    /**
     * Creates a new User.
     * @param user The user to be created from type User.Register(firstName, lastName, email, password).
     */
    fun registerUser(user: User.Register): ApiResponse<User> {
        return ApiResponse(data = userRepository.save(UserEntity.fromDto(user)).toDto(), success = true)
    }

    fun loginUser(loginUser: User.Login): ApiResponse<User> {
        userRepository.findByEmail(loginUser.email)?.let { userEntity ->
            return if (userEntity.password == loginUser.password) {
                ApiResponse(data = userEntity.toDto(), success = true)
            } else {
                ApiResponse(error = "Email or password wrong", success = false)
            }
        } ?: return ApiResponse(error = "User doesn't exists", success = false)
    }

    /**
     * Update user-properties: firstname, lastname, password.
     * @param user The updated user from type ChangeProfile.
     */
    fun changeProfile(user: User.ChangeProfile): ApiResponse<User> {
        val userProxy = userRepository.findById(user.id).get()
        return ApiResponse(data = userRepository.save(UserEntity.fromDto(user, userProxy)).toDto(), success = true)
    }

    /**
     * Read a given file and save it to database as blob-type.
     * @param file The image to be read and set as new profile picture.
     * @param userId The user to whom the image shall be related.
     */
    fun updateProfileImage(file: ByteArray, userId: Long): ApiResponse<User> {
        // fetch user from database
        val userProxy = userRepository.findById(userId).get()

        // convert file to blob
        val blob: Blob = SerialBlob(file)

        // save image to user
        userProxy.image = blob
        userRepository.save(userProxy)

        return ApiResponse(data = userProxy.toDto(), success = true)
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
            ApiResponse(success = false)
        }
    }

    /**
     * Returns a List of a users favorite recipes.
     * @param id The id of the user whose favorites shall be returned.
     */
    fun getFavoritesById(id: Long): ApiResponse<List<Recipe>> {
        return if (userRepository.existsById(id)) {
            val userProxy = userRepository.findById(id).get()
            ApiResponse(data = userProxy.favorites.map { it.toDto() }, success = true)
        } else {
            ApiResponse(success = false)
        }
    }

    /**
     *
     * @param rId The referenced recipe's id.
     * @param uId The referenced user's id.
     */
    fun deleteFavoriteById(rId: Long, uId: Long): ApiResponse<Recipe> {
        return ApiResponse()
    }

}
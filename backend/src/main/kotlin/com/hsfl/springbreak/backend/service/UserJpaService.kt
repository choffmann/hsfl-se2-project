package com.hsfl.springbreak.backend.service

import com.hsfl.springbreak.backend.entity.UserEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.User
import com.hsfl.springbreak.backend.repository.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserJpaService(val userRepository: UserRepository) {

    fun registerUser(newUser: User.Register): ApiResponse<User> {
        val savedEntity = userRepository.save(UserEntity.fromDto(newUser)).toDto()
        return ApiResponse(data = savedEntity, success = true)
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

}
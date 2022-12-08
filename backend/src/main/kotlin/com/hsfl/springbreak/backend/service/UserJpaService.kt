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

    fun changeProfile(changes: User.ChangeProfile, userId: Long): ApiResponse<User> {
        val currentUser = userRepository.findById(userId).orElse(null)
        return if (currentUser != null) {
            val savedUser = userRepository.save(UserEntity.fromDto(changes, currentUser)).toDto()
            ApiResponse(data = savedUser, success = true)
        } else {
            ApiResponse(error = "Wrong ID", success = false)
        }

    }

}
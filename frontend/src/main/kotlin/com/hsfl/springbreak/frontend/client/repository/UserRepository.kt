package com.hsfl.springbreak.frontend.client.repository

import com.hsfl.springbreak.common.ApiResponse
import com.hsfl.springbreak.common.User

interface UserRepository {
    suspend fun login(user: User.Login): ApiResponse<User>
}
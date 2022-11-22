package com.hsfl.springbreak.frontend.client.repository

import com.hsfl.springbreak.common.ApiResponse
import com.hsfl.springbreak.common.User
import com.hsfl.springbreak.frontend.client.Client

class UserRepositoryImpl(private val client: Client): UserRepository {
    override suspend fun login(user: User.Login): ApiResponse<User> {
        return client.login(user)
    }
}
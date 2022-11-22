package com.hsfl.springbreak.frontend.client.repository

import com.hsfl.springbreak.frontend.client.Client
import com.hsfl.springbreak.frontend.client.model.User

class UserRepositoryImpl(private val client: Client): UserRepository {
    override suspend fun login(user: User.Login): User.Response {
        return client.login(user)
    }
}
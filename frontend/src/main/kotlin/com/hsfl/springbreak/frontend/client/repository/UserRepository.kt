package com.hsfl.springbreak.frontend.client.repository

import com.hsfl.springbreak.frontend.client.model.User
interface UserRepository {
    suspend fun login(user: User.Login): User.Response
}
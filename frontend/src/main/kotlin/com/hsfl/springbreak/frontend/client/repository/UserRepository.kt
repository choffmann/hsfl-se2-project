package com.hsfl.springbreak.frontend.client.repository

import com.hsfl.springbreak.frontend.client.DataResponse
import com.hsfl.springbreak.frontend.client.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(user: User.Login): Flow<DataResponse<User>>
}
package com.hsfl.springbreak.frontend.client.data.repository

import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(user: User.Login): Flow<DataResponse<User>>
}
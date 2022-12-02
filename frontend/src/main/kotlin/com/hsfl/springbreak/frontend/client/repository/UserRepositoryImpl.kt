package com.hsfl.springbreak.frontend.client.repository

import com.hsfl.springbreak.frontend.client.Client
import com.hsfl.springbreak.frontend.client.DataResponse
import com.hsfl.springbreak.frontend.client.model.APIResponse
import com.hsfl.springbreak.frontend.client.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val client: Client): UserRepository {
    override suspend fun login(user: User.Login): Flow<DataResponse<User>> = flow {
        repositoryHelper {
            val response = client.login(user)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }
}
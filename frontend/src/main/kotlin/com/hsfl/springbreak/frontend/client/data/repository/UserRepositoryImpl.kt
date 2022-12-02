package com.hsfl.springbreak.frontend.client.data.repository

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.APIResponse
import com.hsfl.springbreak.frontend.client.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import web.file.File

class UserRepositoryImpl(private val client: Client) : UserRepository {
    override suspend fun login(user: User.Login): Flow<DataResponse<User>> = flow {
        repositoryHelper {
            val response = client.login(user)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }

    override suspend fun register(user: User.Register, profileImage: File?): Flow<DataResponse<User>> = flow {
        repositoryHelper {
            val response = client.register(user)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
        profileImage?.let { file ->
            repositoryHelper {
                val response = client.updateProfileImage(file)
                APIResponse.fromResponse(response.error, response.data, response.success)
            }
        }
    }
}
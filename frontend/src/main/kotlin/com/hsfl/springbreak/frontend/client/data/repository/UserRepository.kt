package com.hsfl.springbreak.frontend.client.data.repository

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.APIResponse
import com.hsfl.springbreak.frontend.client.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import web.file.File

interface UserRepository {
    suspend fun login(user: User.Login): Flow<DataResponse<User>>
    suspend fun register(user: User.Register): Flow<DataResponse<User>>
    suspend fun uploadProfileImage(profileImage: File): Flow<DataResponse<User.ProfileImage>>
}

class UserRepositoryImpl(private val client: Client) : UserRepository {
    override suspend fun login(user: User.Login): Flow<DataResponse<User>> = flow {
        repositoryHelper {
            val response = client.login(user)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }

    override suspend fun register(user: User.Register): Flow<DataResponse<User>> = flow {
        repositoryHelper {
            val response = client.register(user)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }

    override suspend fun uploadProfileImage(profileImage: File): Flow<DataResponse<User.ProfileImage>> = flow {
        repositoryHelper {
            val response: User.Response = client.updateProfileImage(profileImage)
            response.data?.let {
                APIResponse.fromResponse(data = User.ProfileImage(it.image!!), success = true, error = null)
                // TODO: Fix type
            } ?: APIResponse.fromResponse(error = "Error on fetching profile image", success = false, data = null)
        }
    }
}
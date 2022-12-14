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
    suspend fun uploadProfileImage(userId: Int, profileImage: File): Flow<DataResponse<String>>
    suspend fun updateUser(user: User.UpdateProfile): Flow<DataResponse<User>>
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

    override suspend fun uploadProfileImage(userId: Int, profileImage: File): Flow<DataResponse<String>> = flow {
        emit(DataResponse.Loading())
        val image = client.updateProfileImage(userId, profileImage).data as? String
        image?.let {
            emit(DataResponse.Success(it))
        } ?: emit(DataResponse.Error("Fehler beim hochladen des Profilbildes."))
    }

    override suspend fun updateUser(user: User.UpdateProfile): Flow<DataResponse<User>> = flow {
        repositoryHelper {
            val response = client.updateProfile(user)
            APIResponse.fromResponse(data = response.data, success = response.success, error = response.error)
        }
    }
}
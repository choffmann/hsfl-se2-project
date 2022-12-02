package com.hsfl.springbreak.frontend.client.data.repository

import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.User
import kotlinx.coroutines.flow.Flow
import web.file.File

interface UserRepository {
    suspend fun login(user: User.Login): Flow<DataResponse<User>>
    suspend fun register(user: User.Register): Flow<DataResponse<User>>
    suspend fun uploadProfileImage(profileImage: File): Flow<DataResponse<User.ProfileImage>>
}

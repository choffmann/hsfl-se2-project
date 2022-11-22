package com.hsfl.springbreak.frontend.client.usecases

import com.hsfl.springbreak.common.User
import com.hsfl.springbreak.frontend.client.DataResponse
import com.hsfl.springbreak.frontend.client.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(private val repository: UserRepository) {
    operator fun invoke(user: User.Login): Flow<DataResponse<User>> = flow {
        useCaseHelper { repository.login(user) }
    }
}
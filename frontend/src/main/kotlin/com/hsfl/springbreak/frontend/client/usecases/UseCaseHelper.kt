package com.hsfl.springbreak.frontend.client.usecases

import com.hsfl.springbreak.frontend.client.DataResponse
import com.hsfl.springbreak.frontend.client.model.User
import io.ktor.client.network.sockets.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.FlowCollector

suspend fun <T> FlowCollector<DataResponse<User>>.useCaseHelper(callback: suspend () -> User.Response) {
    try {
        emit(DataResponse.Loading())
        callback().let { response ->
            response.data?.let {
                emit(DataResponse.Success(it))
            } ?: emit(DataResponse.Error(response.error!!))
        }
    } catch (e: Error) {
        e.printStackTrace()
        emit(DataResponse.Error(e.message ?: "An unexpected error occurred"))
    }
}
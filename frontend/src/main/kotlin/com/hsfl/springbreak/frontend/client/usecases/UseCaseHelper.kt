package com.hsfl.springbreak.frontend.client.usecases

import com.hsfl.springbreak.common.ApiResponse
import com.hsfl.springbreak.frontend.client.DataResponse
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.flow.FlowCollector

suspend fun <T> FlowCollector<DataResponse<T>>.useCaseHelper(callback: suspend () -> ApiResponse<T>) {
    try {
        emit(DataResponse.Loading())
        callback().let { response ->
            response.data?.let {
                emit(DataResponse.Success(it))
            } ?: emit(DataResponse.Error(response.error!!))
        }
    } catch (e: IOException) {
        emit(DataResponse.Error("Can't reach the server"))
    } catch (e: Exception) {
        e.printStackTrace()
        emit(DataResponse.Error("An unexpected error occurred"))
    }
}
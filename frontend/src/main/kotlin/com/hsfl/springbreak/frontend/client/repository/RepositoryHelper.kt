package com.hsfl.springbreak.frontend.client.repository

import com.hsfl.springbreak.frontend.client.DataResponse
import com.hsfl.springbreak.frontend.client.model.APIResponse
import kotlinx.coroutines.flow.FlowCollector

suspend fun <T> FlowCollector<DataResponse<T>>.repositoryHelper(callback: suspend () -> APIResponse<T>) {
    try {
        emit(DataResponse.Loading())
        callback().let { response ->
            response.data?.let {
                emit(DataResponse.Success(it))
            } ?: emit(DataResponse.Error(response.error!!))
        }
    } catch (e: Error) {
        e.printStackTrace()
        println(e.cause)
        emit(DataResponse.Error(e.message ?: "An unexpected error occurred"))
    }
}
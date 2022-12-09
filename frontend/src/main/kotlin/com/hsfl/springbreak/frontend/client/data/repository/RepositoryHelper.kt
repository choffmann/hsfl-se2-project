package com.hsfl.springbreak.frontend.client.data.repository

import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.APIResponse
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
        emit(DataResponse.Error("[DEBUG] ${e.message}"))
    } catch (e: Exception) {
        e.printStackTrace()
        println(e.cause)
        emit(DataResponse.Error("[DEBUG] ${e.message}"))
    }
}
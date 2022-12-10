package com.hsfl.springbreak.frontend.client.data.repository

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.APIResponse
import com.hsfl.springbreak.frontend.client.data.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CategoryRepository {
    suspend fun getAllCategories(): Flow<DataResponse<List<Category>>>
}

class CategoryRepositoryImpl(private val client: Client) : CategoryRepository {
    override suspend fun getAllCategories(): Flow<DataResponse<List<Category>>> = flow {
        repositoryHelper {
            val response: Category.GetAllResponse = client.getAllCategories()
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }
}
package com.hsfl.springbreak.frontend.client.data.repository

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.APIResponse
import com.hsfl.springbreak.frontend.client.data.model.Difficulty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface DifficultyRepository {
    suspend fun getAllIngredients(): Flow<DataResponse<List<Difficulty>>>
}

class DifficultyRepositoryImpl(private val client: Client) : DifficultyRepository {
    override suspend fun getAllIngredients(): Flow<DataResponse<List<Difficulty>>> = flow {
        repositoryHelper {
            val response: Difficulty.GetAllResponse = client.getAllDifficulties()
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }
}
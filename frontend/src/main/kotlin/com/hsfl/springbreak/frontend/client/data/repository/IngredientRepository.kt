package com.hsfl.springbreak.frontend.client.data.repository

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.APIResponse
import com.hsfl.springbreak.frontend.client.data.model.Ingredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IngredientRepository {
    suspend fun getAllIngredients(): Flow<DataResponse<List<Ingredient.Label>>>
}

class IngredientRepositoryImpl(private val client: Client): IngredientRepository {
    override suspend fun getAllIngredients(): Flow<DataResponse<List<Ingredient.Label>>> = flow {
        repositoryHelper {
            val response: Ingredient.GetAllResponse = client.getAllIngredients()
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }
}
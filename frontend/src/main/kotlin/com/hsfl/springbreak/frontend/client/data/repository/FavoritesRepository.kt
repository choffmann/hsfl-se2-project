package com.hsfl.springbreak.frontend.client.data.repository

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.APIResponse
import com.hsfl.springbreak.frontend.client.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface FavoritesRepository {
    suspend fun getFavorites(userId: Int): Flow<DataResponse<List<Recipe>>>
    suspend fun setFavorite(userId: Int, recipeId: Int): Flow<DataResponse<Nothing>>
    suspend fun deleteFavorite(userId: Int, recipeId: Int): Flow<DataResponse<Nothing>>
}

class FavoritesRepositoryImpl(private val client: Client): FavoritesRepository {
    override suspend fun getFavorites(userId: Int): Flow<DataResponse<List<Recipe>>> = flow {
        /*repositoryHelper {
            val response: Recipe.ResponseList = client.getMyFavorites(userId)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }*/
    }

    override suspend fun setFavorite(userId: Int, recipeId: Int): Flow<DataResponse<Nothing>> = flow {
        /*repositoryHelper {
            val response: Nothing = client.getMyFavorites(userId)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }*/
    }

    override suspend fun deleteFavorite(userId: Int, recipeId: Int): Flow<DataResponse<Nothing>> {
        TODO("Not yet implemented")
    }
}
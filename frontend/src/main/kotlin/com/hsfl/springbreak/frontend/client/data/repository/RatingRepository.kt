package com.hsfl.springbreak.frontend.client.data.repository

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.APIResponse
import com.hsfl.springbreak.frontend.client.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface RatingRepository {
    suspend fun sendRating(rating: Recipe.Rating): Flow<DataResponse<Double>>
}

class RatingRepositoryImpl(private val client: Client) : RatingRepository {
    override suspend fun sendRating(rating: Recipe.Rating): Flow<DataResponse<Double>> = flow {
        repositoryHelper {
            val response: Recipe.RatingResponse = client.sendRating(rating)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }
}
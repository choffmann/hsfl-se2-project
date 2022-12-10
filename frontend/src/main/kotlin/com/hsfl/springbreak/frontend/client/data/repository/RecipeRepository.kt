package com.hsfl.springbreak.frontend.client.data.repository

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.APIResponse
import com.hsfl.springbreak.frontend.client.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import web.file.File

interface RecipeRepository {
    suspend fun createRecipe(recipe: Recipe.Create): Flow<DataResponse<Recipe>>
    suspend fun updateRecipe(recipe: Recipe.Update): Flow<DataResponse<Recipe>>
    suspend fun deleteRecipe(recipeId: Long): Flow<DataResponse<Recipe>>
    suspend fun uploadImage(recipeImage: File?): Flow<DataResponse<Recipe.Image>>
}

class RecipeRepositoryImpl(private val client: Client) : RecipeRepository {
    override suspend fun createRecipe(recipe: Recipe.Create): Flow<DataResponse<Recipe>> = flow {
        repositoryHelper {
            val response: Recipe.Response = client.createRecipe(recipe)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }

    override suspend fun updateRecipe(recipe: Recipe.Update): Flow<DataResponse<Recipe>> = flow {
        repositoryHelper {
            val response: Recipe.Response = client.updateRecipe(recipe)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }

    override suspend fun deleteRecipe(recipeId: Long): Flow<DataResponse<Recipe>> = flow {
        repositoryHelper {
            val response: Recipe.Response = client.deleteRecipe(recipeId)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }

    override suspend fun uploadImage(recipeImage: File?): Flow<DataResponse<Recipe.Image>> = flow {
        repositoryHelper {
            val response: Recipe.ImageResponse = client.updateRecipeImage(recipeImage)
            APIResponse.fromResponse(response.error, Recipe.Image(response.imageUrl ?: ""), response.success)
        }
    }
}
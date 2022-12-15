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
    suspend fun uploadImage(recipeId: Int, recipeImage: File): Flow<DataResponse<String>>
    suspend fun getAllRecipes(): Flow<DataResponse<List<Recipe>>>
    suspend fun getRecipeById(recipeId: Int): Flow<DataResponse<Recipe>>
    suspend fun getRecipeCheapOrder(): Flow<DataResponse<List<Recipe>>>
    suspend fun getRecipeFastOrder(): Flow<DataResponse<List<Recipe>>>
    suspend fun getRecipePopularOrder(): Flow<DataResponse<List<Recipe>>>
    suspend fun getMyFavorites(userId: Int): Flow<DataResponse<List<Recipe>>>
    suspend fun getMyRecipes(userId: Int): Flow<DataResponse<List<Recipe>>>
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

    override suspend fun uploadImage(recipeId: Int, recipeImage: File): Flow<DataResponse<String>> = flow {
        emit(DataResponse.Loading())
        val image = client.updateProfileImage(recipeId, recipeImage).data as? String
        image?.let {
            emit(DataResponse.Success(it))
        } ?: emit(DataResponse.Error("Fehler beim hochladen vom Rezept Bild."))
    }

    override suspend fun getAllRecipes(): Flow<DataResponse<List<Recipe>>> = flow {
        repositoryHelper {
            val response: Recipe.ResponseList = client.getAllRecipes()
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }

    override suspend fun getRecipeById(recipeId: Int): Flow<DataResponse<Recipe>> = flow {
        repositoryHelper {
            val response: Recipe.Response = client.getRecipeById(recipeId)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }

    override suspend fun getRecipeCheapOrder(): Flow<DataResponse<List<Recipe>>> = flow {
        repositoryHelper {
            val response: Recipe.ResponseList = client.getAllRecipes()
            APIResponse.fromResponse(response.error, response.data.sortedBy { it.price }, response.success)
        }
    }

    override suspend fun getRecipeFastOrder(): Flow<DataResponse<List<Recipe>>> = flow {
        repositoryHelper {
            val response: Recipe.ResponseList = client.getAllRecipes()
            APIResponse.fromResponse(response.error, response.data.sortedBy { it.duration }, response.success)
        }
    }

    override suspend fun getRecipePopularOrder(): Flow<DataResponse<List<Recipe>>> = flow {
        repositoryHelper {
            val response: Recipe.ResponseList = client.getRecipeByPopularity()
            APIResponse.fromResponse(response.error, response.data.sortedBy { it.price }, response.success)
        }
    }

    override suspend fun getMyFavorites(userId: Int): Flow<DataResponse<List<Recipe>>> = flow {
        repositoryHelper {
            val response: Recipe.ResponseList = client.getMyFavorites(userId)
            APIResponse.fromResponse(response.error, response.data, response.success)
        }
    }

    override suspend fun getMyRecipes(userId: Int): Flow<DataResponse<List<Recipe>>> = flow {
        repositoryHelper {
            val response: Recipe.ResponseList = client.getAllRecipes()
            APIResponse.fromResponse(response.error, response.data.filter { it.creator.id == userId }, response.success)
        }
    }
}
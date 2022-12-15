package com.hsfl.springbreak.frontend.client.data

import com.hsfl.springbreak.frontend.client.data.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.RequestInit
import org.w3c.xhr.FormData
import web.file.File


interface ApiClient {
    suspend fun login(user: User.Login): User.Response
    suspend fun register(user: User.Register): User.Response
    suspend fun updateProfile(user: User.UpdateProfile): User.Response
    suspend fun updateProfileImage(userId: Int, profileImage: File): User.ImageResponse
    suspend fun getAllIngredients(): Ingredient.GetAllResponse
    suspend fun getAllDifficulties(): Difficulty.GetAllResponse
    suspend fun getAllCategories(): Category.GetAllResponse
    suspend fun getAllRecipes(): Recipe.ResponseList
    suspend fun createRecipe(recipe: Recipe.Create): Recipe.Response
    suspend fun updateRecipe(recipe: Recipe.Update): Recipe.Response
    suspend fun deleteRecipe(recipeId: Long): Recipe.Response
    suspend fun updateRecipeImage(recipeId: Int, recipeImage: File?): Recipe.ImageResponse
    suspend fun getRecipeById(recipeId: Int): Recipe.Response
    suspend fun getRecipeByPopularity(): Recipe.ResponseList
    suspend fun getMyFavorites(userId: Int): Recipe.ResponseList
    suspend fun setFavorite(userId: Int, recipeId: Int): Recipe.Response
    suspend fun deleteFavorite(userId: Int, recipeId: Int): Recipe.Response
}

class Client : ApiClient {
    private val client = HttpClient(Js) {
        install(ContentNegotiation) {
            json()
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    companion object {
        const val BASE_URL = "http://localhost:8080/api"
    }

    override suspend fun login(user: User.Login): User.Response {
        return client.post(urlString = "$BASE_URL/user/login") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    override suspend fun register(user: User.Register): User.Response {
        return client.post(urlString = "$BASE_URL/user/register") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    override suspend fun updateProfile(user: User.UpdateProfile): User.Response {
        return client.put(urlString = "$BASE_URL/user/update") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    override suspend fun updateProfileImage(userId: Int, profileImage: File): dynamic {
        // Have to use window.fetch. Ktor didn't support uploading File from JS File package for now
        val formData = FormData()
        profileImage.let { file ->
            formData.append("image", file.slice(), file.name)
        }
        val response = window.fetch(
            input = "$BASE_URL/user/image?id=$userId", init = RequestInit(
                method = "POST",
                body = formData
            )
        )
            .await()
            .json()
            .await()
        return response.asDynamic()
    }

    override suspend fun getAllIngredients(): Ingredient.GetAllResponse {
        return client.get(urlString = "$BASE_URL/ingredients").body()
    }

    override suspend fun getAllDifficulties(): Difficulty.GetAllResponse {
        return client.get(urlString = "$BASE_URL/difficulties").body()
    }

    override suspend fun getAllCategories(): Category.GetAllResponse {
        return client.get(urlString = "$BASE_URL/categories").body()
    }

    override suspend fun getAllRecipes(): Recipe.ResponseList {
        return client.submitForm(url = "$BASE_URL/recipes", formParameters = Parameters.build {
            append("all", "true")
        }, encodeInQuery = true).body()
    }

    override suspend fun createRecipe(recipe: Recipe.Create): Recipe.Response {
        return client.post(urlString = "$BASE_URL/recipes") {
            contentType(ContentType.Application.Json)
            setBody(recipe)
        }.body()
    }

    override suspend fun updateRecipe(recipe: Recipe.Update): Recipe.Response {
        return client.patch(urlString = "$BASE_URL/recipes") {
            contentType(ContentType.Application.Json)
            setBody(recipe)
        }.body()
    }

    override suspend fun deleteRecipe(recipeId: Long): Recipe.Response {
        return client.delete(urlString = "$BASE_URL/recipes$recipeId").body()
    }

    override suspend fun updateRecipeImage(recipeId: Int, recipeImage: File?): Recipe.ImageResponse {
        val formData = FormData()
        recipeImage?.let { file ->
            formData.append("image", file.slice(), file.name)
        }
        return window.fetch(
            input = "$BASE_URL/recipes/image/$recipeId", init = RequestInit(
                method = "POST",
                body = formData
            )
        )
            .await()
            .text()
            .then {
                return@then Recipe.ImageResponse(imageUrl = it)
            }
            .catch { return@catch Recipe.ImageResponse(error = it.message, success = false) }
            .await()
    }

    override suspend fun getRecipeById(recipeId: Int): Recipe.Response {
        return client.submitForm(url = "$BASE_URL/recipes", formParameters = Parameters.build {
            append("rId", recipeId.toString())
        }, encodeInQuery = true).body()
    }

    override suspend fun getRecipeByPopularity(): Recipe.ResponseList {
        return client.get(urlString = "$BASE_URL/recipes/popularity").body()
    }

    override suspend fun getMyFavorites(userId: Int): Recipe.ResponseList {
        return client.submitForm(url = "$BASE_URL/user/favorite", formParameters = Parameters.build {
            append("uId", userId.toString())
        }, encodeInQuery = true).body()
    }

    override suspend fun setFavorite(userId: Int, recipeId: Int): Recipe.Response {
        return client.post(urlString = "$BASE_URL/user/favorite?rId=$recipeId&uId=$userId").body()
    }

    override suspend fun deleteFavorite(userId: Int, recipeId: Int): Recipe.Response {
        return client.delete(urlString = "$BASE_URL/user/favorite?rId=$recipeId&uId=$userId").body()
    }
}
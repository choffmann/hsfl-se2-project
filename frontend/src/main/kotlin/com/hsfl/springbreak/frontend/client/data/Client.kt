package com.hsfl.springbreak.frontend.client.data

import com.hsfl.springbreak.frontend.client.data.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.RequestInit
import web.file.File
import org.w3c.xhr.FormData


interface ApiClient {
    suspend fun login(user: User.Login): User.Response
    suspend fun register(user: User.Register): User.Response
    suspend fun updateProfileImage(profileImage: File?): User.ImageResponse
    suspend fun getAllIngredients(): Ingredient.GetAllResponse
    suspend fun getAllDifficulties(): Difficulty.GetAllResponse
    suspend fun getAllCategories(): Category.GetAllResponse
    suspend fun createRecipe(recipe: Recipe.Create): Recipe.Response
    suspend fun updateRecipe(recipe: Recipe.Update): Recipe.Response
    suspend fun deleteRecipe(recipeId: Long): Recipe.Response
    suspend fun updateRecipeImage(recipeImage: File?): Recipe.ImageResponse
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
        return client.post(urlString = "$BASE_URL/login") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    override suspend fun register(user: User.Register): User.Response {
        return client.post(urlString = "$BASE_URL/register") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    override suspend fun updateProfileImage(profileImage: File?): User.ImageResponse {
        // Have to use window.fetch. Ktor didn't support uploading File from JS File package for now
        val formData = FormData()
        profileImage?.let { file ->
            formData.append("image", file.slice(), file.name)
        }
        return window.fetch(
            input = "$BASE_URL/upload-recipe-image", init = RequestInit(
                method = "POST",
                body = formData
            )
        )
            .await()
            .text()
            .then {
                return@then User.ImageResponse(imageUrl = it)
            }
            .catch { return@catch User.ImageResponse(error = it.message, success = false) }
            .await()
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

    override suspend fun createRecipe(recipe: Recipe.Create): Recipe.Response {
        return client.post(urlString = "$BASE_URL/recipe") {
            contentType(ContentType.Application.Json)
            setBody(recipe)
        }.body()
    }

    override suspend fun updateRecipe(recipe: Recipe.Update): Recipe.Response {
        return client.patch(urlString = "$BASE_URL/recipe") {
            contentType(ContentType.Application.Json)
            setBody(recipe)
        }.body()
    }

    override suspend fun deleteRecipe(recipeId: Long): Recipe.Response {
        return client.delete(urlString = "$BASE_URL/recipe$recipeId").body()
    }

    override suspend fun updateRecipeImage(recipeImage: File?): Recipe.ImageResponse {
        val formData = FormData()
        recipeImage?.let { file ->
            formData.append("image", file.slice(), file.name)
        }
        return window.fetch(
            input = "$BASE_URL/upload-recipe-image", init = RequestInit(
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
}
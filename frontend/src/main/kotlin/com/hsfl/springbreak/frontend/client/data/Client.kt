package com.hsfl.springbreak.frontend.client.data

import com.hsfl.springbreak.frontend.client.data.model.User
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
    suspend fun updateProfileImage(profileImage: File?): User.Response
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

    override suspend fun login(user: User.Login): User.Response {
        return client.post(urlString = "http://localhost:8080/login") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    override suspend fun register(user: User.Register): User.Response {
        return client.post(urlString = "http://localhost:8080/register") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    override suspend fun updateProfileImage(profileImage: File?): User.Response {
        // Have to use window.fetch. Ktor didn't support uploading File from JS File package for now
        val formData = FormData()
        profileImage?.let { file ->
            formData.append("image", file.slice(), file.name)
        }
        val response = window.fetch(input = "http://localhost:8080/upload-profile-image", init = RequestInit(
            method = "POST",
            body = formData
        ))
            .await()
            .text()
            .await()
        println(response)
        return User.Response()
    }
}
package com.hsfl.springbreak.frontend.client

import com.hsfl.springbreak.common.ApiResponse
import com.hsfl.springbreak.common.User
import csstype.url
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*


interface ApiClient {
    suspend fun login(user: User.Login): ApiResponse<User>
}

class Client: ApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        url("http://localhost:8080")
    }

    override suspend fun login(user: User.Login): ApiResponse<User> {
        return client.post(urlString = "/login") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }
}
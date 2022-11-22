package com.hsfl.springbreak.frontend.client

import com.hsfl.springbreak.frontend.client.model.User
import csstype.url
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.json.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*


interface ApiClient {
    suspend fun login(user: User.Login): User.Response
}

class Client: ApiClient {
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
}
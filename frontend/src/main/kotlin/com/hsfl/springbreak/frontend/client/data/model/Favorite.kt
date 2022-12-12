package com.hsfl.springbreak.frontend.client.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Favorite(
    val id: Int,
    val recipe: Recipe
)

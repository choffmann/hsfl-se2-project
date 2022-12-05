package com.hsfl.springbreak.backend.model

data class Difficulty(
    val id: Long,
    val recipes: List<Recipe>,
    val name: String
)

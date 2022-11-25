package com.hsfl.springbreak.backend.model

import java.time.LocalDate

data class Rating(
    val id: Long,
    val like: Int,
    val dislike: Int
)

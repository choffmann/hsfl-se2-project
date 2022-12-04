package com.hsfl.springbreak.backend.model

import java.time.LocalDate

data class Recipe(
    val id: Long,
    val title: String,
    val shortDescription: String,
    val price: Double,
    val duration: Double,
    val likes: Int,
    val dislikes: Int,
    val difficulty: Difficulty,
    val category: Category,
    val creator: User,
    val createTime: LocalDate,
    //val ingredients: List<Ingredient>,
    val image: String,
    val longDescription: String,
    val views: Int
    /*
    val rating: Rating,
     */
) {

}

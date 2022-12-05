package com.hsfl.springbreak.backend.model

import java.time.LocalDate

data class Recipe(
    val id: Long,
    val title: String,
    val shortDescription: String,
    val price: Double,
    val duration: Double,
    val category: Category,
    val creator: User,
    val difficulty: Difficulty,
    val rating: Rating,
    //val ingredients: List<Ingredient>
    /*
    val rating: Rating,
    val difficulty: Difficulty,
    val createTime: LocalDate,
    val ingredients: List<Ingredient>,
    val image: String,
    val longDescription: String,
    val views: Int

     */
) {

}

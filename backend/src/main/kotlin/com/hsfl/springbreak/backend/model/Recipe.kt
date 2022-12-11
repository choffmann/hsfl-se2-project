package com.hsfl.springbreak.backend.model

import java.sql.Blob
import java.time.LocalDate

data class Recipe(
    val id: Long,
    val title: String,
    val shortDescription: String,
    val price: Double,
    val duration: Double,
    val difficulty: Difficulty,
    val category: Category,
    val creator: User,
    val createTime: LocalDate,
    val image: String?,
    val longDescription: String?,
    val views: Int,
    val ingredients: List<IngredientRecipe>
    /*
    val rating: Rating,
    val ingredients: List<Ingredient>,
     */
) {

    data class CreateRecipe(
        val title: String,
        val shortDescription: String?,
        val price: Double?,
        val duration: Double?,
        val difficultyId: Long,
        val categoryId: Long,
        val creatorId: Long,
        val createTime: LocalDate?,
        val image: Blob?,
        val longDescription: String?,
        val ingredients: List<IngredientRecipe.WithoutRecipe>
    )

    data class ChangeRecipe(
        val recipeId: Long,
        val title: String,
        val shortDescription: String?,
        val price: Double?,
        val duration: Double?,
        val difficultyId: Long,
        val categoryId: Long,
        val image: String?,
        val longDescription: String?,
        val ingredients: List<IngredientRecipe.WithoutRecipe>
    )

}

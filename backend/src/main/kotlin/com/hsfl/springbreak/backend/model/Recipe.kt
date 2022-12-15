package com.hsfl.springbreak.backend.model

import java.time.LocalDateTime

/**
 * DTO of recipe-entity.
 */
data class Recipe(
    val id: Long,
    val title: String,
    val shortDescription: String,
    val price: Double,
    val duration: Int,
    val difficulty: Difficulty,
    val category: Category,
    val creator: User,
    val createTime: LocalDateTime,
    val image: String?,
    val longDescription: String?,
    val views: Int,
    val score: Double,
    val ratings: List<Rating>,
    val ingredients: List<IngredientRecipe>
) {

    /**
     * DTO which is used for the data transfer at the
     * creating post request. It only contains the attributes
     * that can be set by a user.
     */
    data class CreateRecipe(
        val title: String,
        val shortDescription: String?,
        val price: Double?,
        val duration: Int?,
        val difficultyId: Long,
        var categoryId: Long,
        val creatorId: Long,
        val longDescription: String?,
        val ingredients: List<IngredientRecipe.WithoutRecipe>
    )

    /**
     * DTO which is used for the data transfer at the
     * updating recipe put request. It only contains the attributes
     * that can be set by a user and the corresponding recipe ID.
     */
    data class ChangeRecipe(
        val recipeId: Long,
        val title: String,
        val shortDescription: String?,
        val price: Double?,
        val duration: Int?,
        val difficultyId: Long,
        val categoryId: Long,
        val longDescription: String?,
        val ingredients: List<IngredientRecipe.WithoutRecipe>
    )

    /**
     * DTO for transferring the recipe information to the user.
     */
    data class Response(
        val id: Long,
        val title: String,
        val shortDescription: String,
        val price: Double,
        val duration: Int,
        val difficulty: Difficulty,
        val category: Category,
        val creator: User.Response,
        val createTime: LocalDateTime,
        val image: String?,
        val longDescription: String?,
        val views: Int,
        val ingredients: List<IngredientRecipe.Response>,
        val score: Double
    )

}

package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

sealed interface CreateRecipeDataEvent {
    data class RecipeName(val value: String) : CreateRecipeDataEvent
    data class RecipeShortDesc(val value: String) : CreateRecipeDataEvent
    data class RecipePrice(val value: Double) : CreateRecipeDataEvent
    data class RecipeDuration(val value: Int) : CreateRecipeDataEvent
    data class RecipeDifficulty(val value: String) : CreateRecipeDataEvent
    data class RecipeCategory(val value: String) : CreateRecipeDataEvent
    object OnNext : CreateRecipeDataEvent
}
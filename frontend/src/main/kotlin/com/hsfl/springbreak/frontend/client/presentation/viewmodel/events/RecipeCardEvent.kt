package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

sealed interface RecipeCardEvent {
    object OnFavorite: RecipeCardEvent
    data class OnLaunch(val id: Int, val isFavorite: Boolean, val isMyRecipe: Boolean): RecipeCardEvent
}

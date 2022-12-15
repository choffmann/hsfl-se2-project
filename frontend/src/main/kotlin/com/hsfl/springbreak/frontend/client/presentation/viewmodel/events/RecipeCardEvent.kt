package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.RecipeListType

sealed interface RecipeCardEvent {
    data class OnFavorite(val id: Int): RecipeCardEvent
    data class OnLaunch(val type: RecipeListType): RecipeCardEvent
}

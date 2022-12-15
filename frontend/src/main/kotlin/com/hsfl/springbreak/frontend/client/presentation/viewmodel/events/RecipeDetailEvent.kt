package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

sealed interface RecipeDetailEvent {
    data class RecipeId(val id: Int) : RecipeDetailEvent
    object OnEdit : RecipeDetailEvent
    object OnFavorite : RecipeDetailEvent
    object OnUnFavorite : RecipeDetailEvent
    object OnDelete : RecipeDetailEvent
    object OnDeleteDialogConfirm : RecipeDetailEvent
    object OnDeleteDialogAbort : RecipeDetailEvent
    object CancelEdit : RecipeDetailEvent
    data class OnScoreChanged(val score: Double) : RecipeDetailEvent
}
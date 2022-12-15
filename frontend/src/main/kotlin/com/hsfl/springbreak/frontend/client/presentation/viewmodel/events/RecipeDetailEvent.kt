package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

import com.hsfl.springbreak.frontend.client.data.model.Recipe
import web.file.File

sealed interface RecipeDetailEvent {
    data class RecipeId(val id: Int) : RecipeDetailEvent
    object OnEdit : RecipeDetailEvent
    object OnFavorite : RecipeDetailEvent
    object OnDelete : RecipeDetailEvent
    object OnDeleteDialogConfirm : RecipeDetailEvent
    object OnDeleteDialogAbort : RecipeDetailEvent
    object CancelEdit : RecipeDetailEvent
    data class OnScoreChanged(val score: Double) : RecipeDetailEvent
    data class OnSaveEdit(val recipe: Recipe.Update, val recipeImage: File?): RecipeDetailEvent
}
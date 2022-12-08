package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import web.file.File

class CreateRecipeImageVM {
    private val _recipeImage: MutableStateFlow<File?> = MutableStateFlow(null)
    val recipeImage: StateFlow<File?> = _recipeImage

    fun onEvent(event: CreateRecipeImageEvent) {
        when (event) {
            is CreateRecipeImageEvent.SelectedFile -> _recipeImage.value = event.file
            is CreateRecipeImageEvent.ClearStates -> _recipeImage.value = null
        }
    }
}

sealed class CreateRecipeImageEvent {
    data class SelectedFile(val file: File): CreateRecipeImageEvent()
    object ClearStates: CreateRecipeImageEvent()
}
package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CreateRecipeImageEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import web.file.File

class CreateRecipeImageVM {
    private val _recipeImage: MutableStateFlow<File?> = MutableStateFlow(null)
    val recipeImage: StateFlow<File?> = _recipeImage

    fun onEvent(event: CreateRecipeImageEvent) {
        when (event) {
            is CreateRecipeImageEvent.SelectedFile -> _recipeImage.value = event.file
            LifecycleEvent.OnMount -> TODO()
            LifecycleEvent.OnUnMount -> clearStates()
        }
    }

    private fun clearStates() {
        _recipeImage.value = null
    }
}

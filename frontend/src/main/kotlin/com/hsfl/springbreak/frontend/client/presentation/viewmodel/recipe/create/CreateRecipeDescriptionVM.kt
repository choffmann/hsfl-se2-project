package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateRecipeDescriptionVM {
    private val _descriptionText = MutableStateFlow("")
    val descriptionText: StateFlow<String> = _descriptionText

    fun onEvent(event: CreateRecipeDescriptionEvent) {
        when (event) {
            is CreateRecipeDescriptionEvent.TextChanged -> _descriptionText.value = event.value
            is CreateRecipeDescriptionEvent.ClearStates -> clearStates()
        }
    }

    private fun clearStates() {
        _descriptionText.value = ""
    }
}

sealed class CreateRecipeDescriptionEvent {
    data class TextChanged(val value: String): CreateRecipeDescriptionEvent()
    object ClearStates: CreateRecipeDescriptionEvent()
}
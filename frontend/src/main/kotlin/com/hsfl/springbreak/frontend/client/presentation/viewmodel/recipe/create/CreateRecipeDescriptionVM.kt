package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CreateRecipeDescriptionEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateRecipeDescriptionVM {
    private val _descriptionText = MutableStateFlow("")
    val descriptionText: StateFlow<String> = _descriptionText

    fun onEvent(event: CreateRecipeDescriptionEvent) {
        when (event) {
            is CreateRecipeDescriptionEvent.TextChanged -> _descriptionText.value = event.value
            LifecycleEvent.OnMount -> { /* Nothing to do here */ }
            LifecycleEvent.OnUnMount -> clearStates()
        }
    }

    private fun clearStates() {
        _descriptionText.value = ""
    }
}

package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

sealed interface CreateRecipeDescriptionEvent {
    data class TextChanged(val value: String): CreateRecipeDescriptionEvent
}
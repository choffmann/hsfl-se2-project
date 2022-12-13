package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

import com.hsfl.springbreak.frontend.client.data.model.Recipe

sealed interface RootEvent {
    data class OnNewRecipe(val recipe: Recipe): RootEvent
}
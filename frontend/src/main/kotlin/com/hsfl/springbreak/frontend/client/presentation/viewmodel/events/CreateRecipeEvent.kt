package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

sealed interface CreateRecipeEvent {
    object OnNextStep : CreateRecipeEvent
    object OnBackStep : CreateRecipeEvent
    object OnAbort : CreateRecipeEvent
    object OnCloseAbort : CreateRecipeEvent
    object OnConfirmAbort : CreateRecipeEvent
    object OnFinished : CreateRecipeEvent
}
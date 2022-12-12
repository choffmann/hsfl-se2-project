package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

sealed interface IngredientEditDialogEvent {
    object OnSubmit : IngredientEditDialogEvent
    object OnClose : IngredientEditDialogEvent
    data class OnNameChanged(val value: String) : IngredientEditDialogEvent
    data class OnAmountChanged(val value: Int) : IngredientEditDialogEvent
    data class OnUnitChanged(val value: String) : IngredientEditDialogEvent
}
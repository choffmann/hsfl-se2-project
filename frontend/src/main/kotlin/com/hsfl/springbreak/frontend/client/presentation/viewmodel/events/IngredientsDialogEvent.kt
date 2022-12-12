package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

sealed interface IngredientsDialogEvent {
    object OnAddMoreIngredient : IngredientsDialogEvent
    object OnFinished : IngredientsDialogEvent
    object OnAbort : IngredientsDialogEvent
    object OnOpen : IngredientsDialogEvent
    object OnIngredientAutoComplete : IngredientsDialogEvent
    data class IngredientNameChanged(val value: String) : IngredientsDialogEvent
    data class IngredientAmountChanged(val value: Int) : IngredientsDialogEvent
    data class IngredientUnitChanged(val value: String) : IngredientsDialogEvent
}
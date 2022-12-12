package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.RecipeIngredient

sealed interface IngredientsTableEvent {
    object OnSelectAllRows : IngredientsTableEvent
    object OnDeleteRows : IngredientsTableEvent
    object OnEditRow : IngredientsTableEvent
    object OnCloseEditDialog : IngredientsTableEvent
    data class OnSaveEditDialog(val item: RecipeIngredient) : IngredientsTableEvent
    data class OnSelectRow(val index: Int) : IngredientsTableEvent
    data class OnNewData(val list: List<RecipeIngredient>) : IngredientsTableEvent
}
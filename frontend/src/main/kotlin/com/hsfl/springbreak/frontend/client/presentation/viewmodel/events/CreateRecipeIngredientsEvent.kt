package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

sealed interface CreateRecipeIngredientsEvent {
    object OnAddIngredient : CreateRecipeIngredientsEvent
}
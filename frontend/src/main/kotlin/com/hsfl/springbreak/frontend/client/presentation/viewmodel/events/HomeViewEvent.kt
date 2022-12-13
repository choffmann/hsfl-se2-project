package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.HomeRecipeTab

sealed interface HomeViewEvent {
    object OnCheapTab: HomeViewEvent
    object OnFastTab: HomeViewEvent
    object OnPopularTab: HomeViewEvent
    object OnAllTab: HomeViewEvent
    data class OnTabChange(val tab: HomeRecipeTab): HomeViewEvent
    //data class OnRecipe(val id: Int): HomeViewEvent
}
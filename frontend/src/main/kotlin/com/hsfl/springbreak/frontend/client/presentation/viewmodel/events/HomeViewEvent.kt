package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.HomeRecipeTab

sealed interface HomeViewEvent {
    data class OnTabChange(val tab: HomeRecipeTab) : HomeViewEvent
}
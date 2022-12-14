package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe

import com.hsfl.springbreak.frontend.client.data.repository.FavoritesRepository

class RecipeCardViewModel(
    private val favoritesRepository: FavoritesRepository
) {
    fun onEvent(event: RecipeCardEvent) {

    }
}

sealed class RecipeCardEvent {

}
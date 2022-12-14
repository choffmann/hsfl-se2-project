package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe

import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.client.data.repository.FavoritesRepository
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.MessageViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.SnackbarEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.RecipeCardEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeCardViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val userState: UserState,
    private val authState: AuthState,
    private val scope: CoroutineScope = MainScope()
) {
    private val _recipeId = MutableStateFlow(-1)
    val recipeId: StateFlow<Int> = _recipeId

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _isMyRecipe = MutableStateFlow(false)
    val isMyRecipe: StateFlow<Boolean> = _isMyRecipe

    fun onEvent(event: RecipeCardEvent) {
        when (event) {
            RecipeCardEvent.OnFavorite -> onFavorite()
            LifecycleEvent.OnMount -> {}
            LifecycleEvent.OnUnMount -> {}
            is RecipeCardEvent.OnLaunch -> {
                _recipeId.value = event.id
                _isMyRecipe.value = event.isMyRecipe
                _isFavorite.value = event.isFavorite
            }
        }
    }

    private fun onFavorite() = scope.launch {
        // Only get favorites when user is logged in
        if (isFavorite.value) {
            deleteFavorite()
        } else {
            setFavorite()
        }
    }

    private fun deleteFavorite() = scope.launch {
        favoritesRepository.deleteFavorite(userState.userState.value.id, recipeId.value).collectLatest {response ->
            response.handleDataResponse<Recipe>(
                onSuccess = {
                    _isFavorite.value = false
                    MessageViewModel.onEvent(SnackbarEvent.Show("Das Rezept wurde von deinen Favoriten entfernt."))
                }
            )
        }
    }

    private fun setFavorite() = scope.launch {
        favoritesRepository.setFavorite(userState.userState.value.id, recipeId.value).collectLatest {response ->
            response.handleDataResponse<Recipe>(
                onSuccess = {
                    _isFavorite.value = true
                    MessageViewModel.onEvent(SnackbarEvent.Show("Das Rezept wurde zu deinen Favoriten hinzugefügt."))
                }
            )
        }
    }
}

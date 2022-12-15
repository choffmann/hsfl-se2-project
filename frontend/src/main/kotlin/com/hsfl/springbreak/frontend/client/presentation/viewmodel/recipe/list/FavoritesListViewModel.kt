package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.list

import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.client.data.repository.FavoritesRepository
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesListViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val userState: UserState,
    private val scope: CoroutineScope = MainScope()
) {
    private val _recipeList = MutableStateFlow(listOf<Recipe>())
    val recipeList: StateFlow<List<Recipe>> = _recipeList

    fun onEvent(event: LifecycleEvent) {
        when (event) {
            LifecycleEvent.OnMount -> fetchFavorites()
            LifecycleEvent.OnUnMount -> _recipeList.value = listOf()
        }
    }

    private fun fetchFavorites() = scope.launch {
        favoritesRepository.getFavorites(userState.userState.value.id).collectLatest { response ->
            response.handleDataResponse<List<Recipe>>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.Idle)
                    _recipeList.value = it
                }
            )
        }
    }

}
package com.hsfl.springbreak.frontend.client.presentation.viewmodel

import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.client.data.repository.RecipeRepository
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.HomeViewEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val recipeRepository: RecipeRepository,
    private val scope: CoroutineScope = MainScope()
) {
    private val _currentTab = MutableStateFlow<HomeRecipeTab>(HomeRecipeTab.CheapTab)
    val currentTab: StateFlow<HomeRecipeTab> = _currentTab

    private val _recipeList = MutableStateFlow<List<Recipe>>(listOf())
    val recipeList: StateFlow<List<Recipe>> = _recipeList

    fun onEvent(event: HomeViewEvent) {
        when (event) {
            HomeViewEvent.OnCheapTab -> fetchRecipesByTabState()
            HomeViewEvent.OnFastTab -> fetchRecipesByTabState()
            HomeViewEvent.OnPopularTab -> fetchRecipesByTabState()
            HomeViewEvent.OnAllTab -> fetchRecipesByTabState()
            LifecycleEvent.OnMount -> fetchRecipesByTabState()
            LifecycleEvent.OnUnMount -> clearStates()
            is HomeViewEvent.OnTabChange -> handleTabChange(event.tab)
        }
    }

    private fun clearStates() {
        _currentTab.value = HomeRecipeTab.CheapTab
        _recipeList.value = emptyList()
    }

    private fun handleTabChange(tab: HomeRecipeTab) {
        _currentTab.value = tab
        fetchRecipesByTabState()
    }

    private fun fetchRecipesByTabState() = scope.launch {
        println("HomeViewModel::fetchRecipesByTabState")
        when (currentTab.value) {
            HomeRecipeTab.AllTab -> recipeRepository.getAllRecipes().saveToState()
            HomeRecipeTab.CheapTab -> recipeRepository.getRecipeCheapOrder().saveToState()
            HomeRecipeTab.FastTab -> recipeRepository.getRecipeFastOrder().saveToState()
            HomeRecipeTab.PopularTab -> TODO()
        }
    }

    private suspend fun Flow<DataResponse<List<Recipe>>>.saveToState() {
        this.collectLatest { response ->
            response.handleDataResponse<List<Recipe>>(
                onSuccess = { list ->
                    UiEventState.onEvent(UiEvent.Idle)
                    _recipeList.value = list
                }
            )
        }
    }
}

sealed class HomeRecipeTab {
    object CheapTab : HomeRecipeTab()
    object FastTab : HomeRecipeTab()
    object PopularTab : HomeRecipeTab()
    object AllTab : HomeRecipeTab()

    fun toInt() = when (this) {
        CheapTab -> 0
        FastTab -> 1
        PopularTab -> 2
        AllTab -> 3
    }
}


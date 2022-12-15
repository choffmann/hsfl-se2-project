package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.detail

import com.hsfl.springbreak.frontend.client.data.model.Category
import com.hsfl.springbreak.frontend.client.data.model.Difficulty
import com.hsfl.springbreak.frontend.client.data.repository.CategoryRepository
import com.hsfl.springbreak.frontend.client.data.repository.DifficultyRepository
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.RecipeDetailEditEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeDetailEditViewModel(
    private val categoryRepository: CategoryRepository,
    private val difficultyRepository: DifficultyRepository,
    private val scope: CoroutineScope = MainScope()
) {
    private val _categoryList = MutableStateFlow(listOf<Category>())
    val categoryList: StateFlow<List<Category>> = _categoryList

    private val _difficultyList = MutableStateFlow(listOf<Difficulty>())
    val difficultyList: StateFlow<List<Difficulty>> = _difficultyList

    fun onEvent(event: RecipeDetailEditEvent) {
        when (event) {
            LifecycleEvent.OnMount -> onInit()
            LifecycleEvent.OnUnMount -> clearStates()
        }
    }

    private fun clearStates() {
        _categoryList.value = listOf()
        _difficultyList.value = listOf()
    }

    private fun onInit() {
        fetchCategoryList()
        fetchDifficultyList()
    }

    private fun fetchCategoryList() = scope.launch {
        categoryRepository.getAllCategories().collectLatest { response ->
            response.handleDataResponse<List<Category>>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.Idle)
                    _categoryList.value = it
                }
            )
        }
    }

    private fun fetchDifficultyList() = scope.launch {
        difficultyRepository.getAllDifficulties().collectLatest { response ->
            response.handleDataResponse<List<Difficulty>>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.Idle)
                    _difficultyList.value = it
                }
            )
        }
    }
}

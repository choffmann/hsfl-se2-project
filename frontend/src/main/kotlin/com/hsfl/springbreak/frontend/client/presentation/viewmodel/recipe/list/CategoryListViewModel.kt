package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.list

import com.hsfl.springbreak.frontend.client.data.model.Category
import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.client.data.repository.CategoryRepository
import com.hsfl.springbreak.frontend.client.data.repository.RecipeRepository
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CategoryListEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CategoryListViewModel(
    private val categoryRepository: CategoryRepository,
    private val recipeRepository: RecipeRepository,
    private val userState: UserState,
    private val authState: AuthState,
    private val scope: CoroutineScope = MainScope()
) {
    private val _categoryList = MutableStateFlow(listOf<Category>())
    val categoryList: StateFlow<List<Category>> = _categoryList

    private val _recipeList = MutableStateFlow(listOf<Recipe>())
    val recipeList: StateFlow<List<Recipe>> = _recipeList

    fun onEvent(event: CategoryListEvent) {
        when (event) {
            is LifecycleEvent.OnMount -> fetchCategoryList()
            is LifecycleEvent.OnUnMount -> cleanStates()
            is CategoryListEvent.OnCategoryClick -> getRecipeToCategory(event.categoryId)
        }
    }

    private fun cleanStates() {
        _categoryList.value = listOf()
    }

    private fun getRecipeToCategory(categoryId: Int) = scope.launch {
        categoryRepository.getRecipesByCategory(categoryId).collectLatest { response ->
            response.handleDataResponse<List<Recipe>>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.Idle)
                    _recipeList.value = it
                }
            )
        }
    }

    private fun fetchCategoryList() = scope.launch {
        categoryRepository.getAllCategories().collectLatest { response ->
            response.handleDataResponse<List<Category>>(
                onSuccess = { list ->
                    UiEventState.onEvent(UiEvent.Idle)
                    _categoryList.value = list
                }
            )
        }
    }
}
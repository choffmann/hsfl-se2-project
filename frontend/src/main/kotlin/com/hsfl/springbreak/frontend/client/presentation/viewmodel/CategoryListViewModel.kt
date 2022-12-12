package com.hsfl.springbreak.frontend.client.presentation.viewmodel

import com.hsfl.springbreak.frontend.client.data.model.Category
import com.hsfl.springbreak.frontend.client.data.repository.CategoryRepository
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
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
    private val scope: CoroutineScope = MainScope()
) {
    private val _categoryList = MutableStateFlow(listOf<Category>())
    val categoryList: StateFlow<List<Category>> = _categoryList

    fun onEvent(event: CategoryListEvent) {
        when (event) {
            is LifecycleEvent.OnMount -> fetchCategoryList()
            is LifecycleEvent.OnUnMount -> cleanStates()
        }
    }

    private fun cleanStates() {
        _categoryList.value = listOf()
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
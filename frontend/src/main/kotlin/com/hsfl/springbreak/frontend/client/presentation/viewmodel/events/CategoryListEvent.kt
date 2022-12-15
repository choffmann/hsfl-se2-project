package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

sealed interface CategoryListEvent{
    data class OnCategoryClick(val categoryId: Int): CategoryListEvent
}

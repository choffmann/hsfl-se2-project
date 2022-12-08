package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IngredientsTableVM {
    private val _ingredientsList = MutableStateFlow<MutableList<RecipeIngredient>>(mutableListOf())
    val ingredientsList: StateFlow<List<RecipeIngredient>> = _ingredientsList

    private val _selectedIngredients = MutableStateFlow<MutableList<RecipeIngredient>>(mutableListOf())
    val selectedIngredients: StateFlow<List<RecipeIngredient>> = _selectedIngredients

    private val _randomState = MutableStateFlow<Int>(0)
    val randomState: StateFlow<Int> = _randomState

    fun onEvent(event: IngredientsTableEvent) {
        when (event) {
            is IngredientsTableEvent.OnSelectAllRows -> TODO()
            is IngredientsTableEvent.OnSelectRow -> TODO()
            is IngredientsTableEvent.OnNewData -> addIngredient(event.list)
        }
    }

    private fun addIngredient(list: List<RecipeIngredient>) {
        _randomState.value++
        _ingredientsList.value.addAll(list)
    }
}

sealed class IngredientsTableEvent {
    object OnSelectAllRows: IngredientsTableEvent()
    data class OnSelectRow(val entry: RecipeIngredient): IngredientsTableEvent()
    data class OnNewData(val list: List<RecipeIngredient>) : IngredientsTableEvent()
}
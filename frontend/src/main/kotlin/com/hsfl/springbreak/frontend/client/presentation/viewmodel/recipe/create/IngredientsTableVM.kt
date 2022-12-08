package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IngredientsTableVM {
    private val _ingredientsList = MutableStateFlow<MutableList<IngredientsTableRow>>(mutableListOf())
    val ingredientsList: StateFlow<List<IngredientsTableRow>> = _ingredientsList

    private val _selectedIngredients = MutableStateFlow<MutableList<IngredientsTableRow>>(mutableListOf())
    val selectedIngredients: StateFlow<List<IngredientsTableRow>> = _selectedIngredients

    // TODO: This is a state which update so the ui will rerender
    private val _randomState = MutableStateFlow(0)
    val randomState: StateFlow<Int> = _randomState

    fun onEvent(event: IngredientsTableEvent) {
        when (event) {
            is IngredientsTableEvent.OnSelectAllRows -> TODO()
            is IngredientsTableEvent.OnSelectRow -> onSelectRow(event.index)
            is IngredientsTableEvent.OnNewData -> addIngredient(event.list)
            is IngredientsTableEvent.OnDeleteRows -> removeOnList()
            is IngredientsTableEvent.OnEditRow -> TODO()
        }
        forceUpdate()
    }

    private fun removeOnList() {
        _ingredientsList.value.removeAll(_selectedIngredients.value)
        _selectedIngredients.value = mutableListOf()
    }

    private fun onSelectRow(index: Int) {
        if (_ingredientsList.value[index].selected) {
            _ingredientsList.value[index].selected = false
            _selectedIngredients.value.remove(_ingredientsList.value[index])
        } else {
            _ingredientsList.value[index].selected = true
            _selectedIngredients.value.add(_ingredientsList.value[index])
        }
    }

    private fun forceUpdate() {
        _randomState.value++
    }

    private fun addIngredient(list: List<RecipeIngredient>) {
        _ingredientsList.value.addAll(list.map { IngredientsTableRow(false, it) })
    }
}

data class IngredientsTableRow(var selected: Boolean, val item: RecipeIngredient)

sealed class IngredientsTableEvent {
    object OnSelectAllRows: IngredientsTableEvent()
    object OnDeleteRows: IngredientsTableEvent()
    object OnEditRow: IngredientsTableEvent()
    data class OnSelectRow(val index: Int): IngredientsTableEvent()
    data class OnNewData(val list: List<RecipeIngredient>) : IngredientsTableEvent()
}
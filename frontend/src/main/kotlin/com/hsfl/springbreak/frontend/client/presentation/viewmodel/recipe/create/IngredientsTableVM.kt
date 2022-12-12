package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.IngredientsTableEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class IngredientsTableVM {
    private val _ingredientsList = MutableStateFlow<MutableList<IngredientsTableRow>>(mutableListOf())
    val ingredientsList: StateFlow<List<IngredientsTableRow>> = _ingredientsList

    private val _selectedIngredients = MutableStateFlow<MutableList<IngredientsTableRow>>(mutableListOf())
    val selectedIngredients: StateFlow<List<IngredientsTableRow>> = _selectedIngredients

    private val _openEditDialog = MutableStateFlow(false)
    val openEditDialog: StateFlow<Boolean> = _openEditDialog

    val ingredientToEdit = MutableSharedFlow<RecipeIngredient>()

    // TODO: This is a state which update so the ui will rerender
    private val _randomState = MutableStateFlow(0)
    val randomState: StateFlow<Int> = _randomState

    fun onEvent(event: IngredientsTableEvent) {
        when (event) {
            is IngredientsTableEvent.OnSelectAllRows -> TODO()
            is IngredientsTableEvent.OnSelectRow -> onSelectRow(event.index)
            is IngredientsTableEvent.OnNewData -> addIngredient(event.list)
            is IngredientsTableEvent.OnDeleteRows -> removeOnList()
            is IngredientsTableEvent.OnEditRow -> onEditRow()
            is IngredientsTableEvent.OnCloseEditDialog -> closeEditDialog()
            is IngredientsTableEvent.OnSaveEditDialog -> saveEditIngredient(event.item)
            LifecycleEvent.OnMount -> { /* Nothing to do here */ }
            LifecycleEvent.OnUnMount -> clearStates()
        }
        forceUpdate()
    }

    private fun saveEditIngredient(updatedIngredient: RecipeIngredient) {
        _ingredientsList.value.map {
            // Has to be the only selected one
            if (it.selected) {
                it.item.name = updatedIngredient.name
                it.item.amount = updatedIngredient.amount
                it.item.unit = updatedIngredient.unit
            }
        }
        closeEditDialog()
    }

    private fun clearStates() {
        _ingredientsList.value = mutableListOf()
        _selectedIngredients.value = mutableListOf()
        _openEditDialog.value = false
        _randomState.value = 0
    }

    private fun onEditRow() {
        val item = selectedIngredients.value[0].item
        println(item.name)
        _openEditDialog.value = true
        MainScope().launch {
            ingredientToEdit.emit(item)
        }
    }

    private fun clearSelectedList() {
        _selectedIngredients.value = mutableListOf()
        _ingredientsList.value.map {
            it.selected = false
        }
    }

    private fun closeEditDialog() {
        _openEditDialog.value = false
        clearSelectedList()
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


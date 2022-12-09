package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IngredientsDialogVM(
    private val ingredientsTableVM: IngredientsTableVM
) {
    private val _ingredientsList = MutableStateFlow<MutableList<RecipeIngredient>>(mutableListOf())
    val ingredientsList: StateFlow<List<RecipeIngredient>> = _ingredientsList

    private val _openDialog = MutableStateFlow(false)
    val openDialog: StateFlow<Boolean> = _openDialog

    private val _ingredientName = MutableStateFlow("")
    val ingredientName: StateFlow<String> = _ingredientName

    private val _ingredientAmount = MutableStateFlow(0)
    val ingredientAmount: StateFlow<Int> = _ingredientAmount

    private val _ingredientUnit = MutableStateFlow("")
    val ingredientUnit: StateFlow<String> = _ingredientUnit

    fun onEvent(event: IngredientsDialogEvent) {
        when (event) {
            is IngredientsDialogEvent.OnOpen -> openDialog()
            is IngredientsDialogEvent.OnAbort -> closeDialog()
            is IngredientsDialogEvent.OnAddMoreIngredient -> addMoreIngredients()
            is IngredientsDialogEvent.OnFinished -> onFinished()
            is IngredientsDialogEvent.IngredientAmountChanged -> _ingredientAmount.value = event.value
            is IngredientsDialogEvent.IngredientNameChanged -> _ingredientName.value = event.value
            is IngredientsDialogEvent.IngredientUnitChanged -> _ingredientUnit.value = event.value
        }
    }

    private fun openDialog() {
        _openDialog.value = true
    }

    private fun resetTextFieldFlows() {
        _ingredientName.value = ""
        _ingredientAmount.value = 0
        _ingredientUnit.value = ""
    }

    private fun resetListFlow() {
        _ingredientsList.value = mutableListOf()
    }

    private fun addCurrentTextAsIngredient() {
        if (_ingredientName.value.isNotEmpty()) {
            _ingredientsList.value.add(
                RecipeIngredient(
                    name = ingredientName.value,
                    amount = ingredientAmount.value,
                    unit = ingredientUnit.value
                )
            )
        }
    }

    private fun addMoreIngredients() {
        addCurrentTextAsIngredient()
        resetTextFieldFlows()
    }

    private fun onFinished() {
        addCurrentTextAsIngredient()
        ingredientsTableVM.onEvent(IngredientsTableEvent.OnNewData(ingredientsList.value))
        resetTextFieldFlows()
        resetListFlow()
        closeDialog()
    }

    private fun closeDialog() {
        resetListFlow()
        _openDialog.value = false
    }
}

sealed class IngredientsDialogEvent {
    object OnAddMoreIngredient : IngredientsDialogEvent()
    object OnFinished : IngredientsDialogEvent()
    object OnAbort : IngredientsDialogEvent()
    object OnOpen : IngredientsDialogEvent()
    data class IngredientNameChanged(val value: String) : IngredientsDialogEvent()
    data class IngredientAmountChanged(val value: Int) : IngredientsDialogEvent()
    data class IngredientUnitChanged(val value: String) : IngredientsDialogEvent()
}
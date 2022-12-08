package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class IngredientEditDialogVM(
    private val ingredientsTableVM: IngredientsTableVM
) {
    val openDialog = ingredientsTableVM.openEditDialog
    var dialogTitle = ""

    private val _ingredientName = MutableStateFlow("")
    val ingredientName: StateFlow<String> = _ingredientName

    private val _ingredientAmount = MutableStateFlow(0)
    val ingredientAmount: StateFlow<Int> = _ingredientAmount

    private val _ingredientUnit = MutableStateFlow("")
    val ingredientUnit: StateFlow<String> = _ingredientUnit

    init {
        MainScope().launch {
            ingredientsTableVM.ingredientToEdit.collectLatest {
                dialogTitle = it.name
                _ingredientName.value = it.name
                _ingredientAmount.value = it.amount
                _ingredientUnit.value = it.unit
            }
        }
    }

    fun onEvent(event: IngredientEditDialogEvent) {
        when (event) {
            is IngredientEditDialogEvent.OnNameChanged -> _ingredientName.value = event.value
            is IngredientEditDialogEvent.OnAmountChanged -> _ingredientAmount.value = event.value
            is IngredientEditDialogEvent.OnUnitChanged -> _ingredientUnit.value = event.value
            is IngredientEditDialogEvent.OnClose -> closeDialog()
            is IngredientEditDialogEvent.OnSubmit -> submitChanges()
        }
    }


    private fun closeDialog() {
        ingredientsTableVM.onEvent(IngredientsTableEvent.OnCloseEditDialog)
    }

    private fun submitChanges() {
        //closeDialog()
        ingredientsTableVM.onEvent(
            IngredientsTableEvent.OnSaveEditDialog(
                RecipeIngredient(
                    name = ingredientName.value,
                    amount = ingredientAmount.value,
                    unit = ingredientUnit.value
                )
            )
        )
    }
}

sealed class IngredientEditDialogEvent {
    object OnSubmit : IngredientEditDialogEvent()
    object OnClose : IngredientEditDialogEvent()
    data class OnNameChanged(val value: String) : IngredientEditDialogEvent()
    data class OnAmountChanged(val value: Int) : IngredientEditDialogEvent()
    data class OnUnitChanged(val value: String) : IngredientEditDialogEvent()
}
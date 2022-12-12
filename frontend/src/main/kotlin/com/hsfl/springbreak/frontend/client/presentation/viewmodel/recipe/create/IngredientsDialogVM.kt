package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import com.hsfl.springbreak.frontend.client.data.model.Ingredient
import com.hsfl.springbreak.frontend.client.data.repository.IngredientRepository
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.IngredientsDialogEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.IngredientsTableEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class IngredientsDialogVM(
    private val ingredientsTableVM: IngredientsTableVM,
    private val ingredientRepository: IngredientRepository,
    private val scope: CoroutineScope = MainScope()
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

    private val _autoCompleteState = MutableStateFlow(IngredientAutocompleteState())
    val autoCompleteState: StateFlow<IngredientAutocompleteState> = _autoCompleteState

    fun onEvent(event: IngredientsDialogEvent) {
        when (event) {
            is IngredientsDialogEvent.OnOpen -> openDialog()
            is IngredientsDialogEvent.OnAbort -> closeDialog()
            is IngredientsDialogEvent.OnAddMoreIngredient -> addMoreIngredients()
            is IngredientsDialogEvent.OnFinished -> onFinished()
            is IngredientsDialogEvent.IngredientAmountChanged -> _ingredientAmount.value = event.value
            is IngredientsDialogEvent.IngredientNameChanged -> _ingredientName.value = event.value
            is IngredientsDialogEvent.IngredientUnitChanged -> _ingredientUnit.value = event.value
            is IngredientsDialogEvent.OnIngredientAutoComplete -> if (_autoCompleteState.value.allIngredients.isEmpty()) fetchIngredientsList()
            LifecycleEvent.OnMount -> { /* Nothing to do here */ }
            LifecycleEvent.OnUnMount -> clearAllStates()
        }
    }

    private fun clearAllStates() {
        resetListFlow()
        resetTextFieldFlows()
    }

    private fun fetchIngredientsList() = scope.launch {
        ingredientRepository.getAllIngredients().collectLatest { response ->
            response.handleDataResponse<List<Ingredient.Label>>(
                onLoading = { _autoCompleteState.value.loading = true },
                onSuccess = { list ->
                    _autoCompleteState.value.loading = false
                    _autoCompleteState.value.allIngredients =
                        list.toTypedArray()
                }
            )
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

data class IngredientAutocompleteState(
    var loading: Boolean = true,
    var allIngredients: Array<Ingredient.Label> = emptyArray()
)

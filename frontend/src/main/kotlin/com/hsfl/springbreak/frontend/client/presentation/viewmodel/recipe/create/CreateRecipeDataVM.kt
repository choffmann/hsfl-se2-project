package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateRecipeDataVM {
    private val _ingredientName = MutableStateFlow(FormTextFieldState("", required = true))
    val ingredientName: StateFlow<FormTextFieldState<String>> = _ingredientName

    private val _ingredientShortDesc = MutableStateFlow(FormTextFieldState(""))
    val ingredientShortDesc: StateFlow<FormTextFieldState<String>> = _ingredientShortDesc

    private val _ingredientPrice = MutableStateFlow(FormTextFieldState(0.0))
    val ingredientPrice: StateFlow<FormTextFieldState<Double>> = _ingredientPrice

    private val _ingredientDuration = MutableStateFlow(FormTextFieldState(0))
    val ingredientDuration: StateFlow<FormTextFieldState<Int>> = _ingredientDuration

    private val _ingredientDifficulty = MutableStateFlow(FormTextFieldState("", required = true))
    val ingredientDifficulty: StateFlow<FormTextFieldState<String>> = _ingredientDifficulty

    private val _ingredientCategory = MutableStateFlow(FormTextFieldState(""))
    val ingredientCategory: StateFlow<FormTextFieldState<String>> = _ingredientCategory

    private val _validateInputs = MutableStateFlow(true)
    val validateInputs: StateFlow<Boolean> = _validateInputs

    fun onEvent(event: CreateRecipeDataEvent) {
        when (event) {
            is CreateRecipeDataEvent.IngredientName -> setText(
                flow = _ingredientName,
                value = event.value,
                required = true
            )

            is CreateRecipeDataEvent.IngredientShortDesc -> setText(
                flow = _ingredientShortDesc,
                value = event.value,
                required = false
            )

            is CreateRecipeDataEvent.IngredientPrice -> setText(
                flow = _ingredientPrice,
                value = event.value,
                required = false
            )

            is CreateRecipeDataEvent.IngredientDuration -> setText(
                flow = _ingredientDuration,
                value = event.value,
                required = false
            )

            is CreateRecipeDataEvent.IngredientDifficulty -> setText(
                flow = _ingredientDifficulty,
                value = event.value,
                required = false
            )

            is CreateRecipeDataEvent.IngredientCategory -> setText(
                flow = _ingredientCategory,
                value = event.value,
                required = false
            )

            is CreateRecipeDataEvent.OnNext -> validateInput()
        }
    }

    private fun validateInput() {
        if (ingredientName.value.value.isEmpty() && ingredientDifficulty.value.value.isEmpty()) {
            setTextError(_ingredientName, "Rezeptname muss angegeben werden")
            setTextError(_ingredientDifficulty, "Schwierigkeitsgrad muss angegeben werden")
        } else if (ingredientName.value.value.isEmpty()) {
            setTextError(_ingredientName, "Rezeptname muss angegeben werden")
        } else if (ingredientDifficulty.value.value.isEmpty()) {
            setTextError(_ingredientDifficulty, "Schwierigkeitsgrad muss angegeben werden")
        } else {
            _validateInputs.value = true
        }
    }

    private fun <T> setTextError(flow: MutableStateFlow<FormTextFieldState<T>>, msg: String) {
        flow.value = flow.value.copy(error = true, errorMsg = msg)
        _validateInputs.value = false
    }

    private fun resetError() {
        _validateInputs.value = true
    }

    private fun <T> setText(flow: MutableStateFlow<FormTextFieldState<T>>, value: T, required: Boolean) {
        flow.value = flow.value.copy(value = value, error = false, errorMsg = "")
        if (required) resetError()
    }
}

data class FormTextFieldState<T>(
    val value: T,
    val required: Boolean = false,
    val error: Boolean = false,
    val errorMsg: String = ""
)

sealed class CreateRecipeDataEvent {
    data class IngredientName(val value: String) : CreateRecipeDataEvent()
    data class IngredientShortDesc(val value: String) : CreateRecipeDataEvent()
    data class IngredientPrice(val value: Double) : CreateRecipeDataEvent()
    data class IngredientDuration(val value: Int) : CreateRecipeDataEvent()
    data class IngredientDifficulty(val value: String) : CreateRecipeDataEvent()
    data class IngredientCategory(val value: String) : CreateRecipeDataEvent()
    object OnNext : CreateRecipeDataEvent()
}
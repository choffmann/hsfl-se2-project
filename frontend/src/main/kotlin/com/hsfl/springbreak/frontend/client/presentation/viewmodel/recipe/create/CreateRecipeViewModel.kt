package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateRecipeViewModel(
    private val stepperViewModel: CreateRecipeStepperViewModel,
    private val dataVM: CreateRecipeDataVM,
    private val tableVM: IngredientsTableVM,
    private val descriptionVM: CreateRecipeDescriptionVM
) {
    private val _openAbortDialog = MutableStateFlow(false)
    val openAbortDialog: StateFlow<Boolean> = _openAbortDialog

    val currentStepIndex = stepperViewModel.currentStepIndex
    val enableNextStepButton = stepperViewModel.enableNextStepButton

    val recipeName: StateFlow<FormTextFieldState<String>> = dataVM.recipeName
    val recipeShortDesc: StateFlow<FormTextFieldState<String>> = dataVM.recipeShortDesc
    val recipePrice: StateFlow<FormTextFieldState<Double>> = dataVM.recipePrice
    val recipeDuration: StateFlow<FormTextFieldState<Int>> = dataVM.recipeDuration
    val recipeDifficulty: StateFlow<FormTextFieldState<String>> = dataVM.recipeDifficulty
    val recipeCategory: StateFlow<FormTextFieldState<String>> = dataVM.recipeCategory
    val ingredientsList: StateFlow<List<IngredientsTableRow>> = tableVM.ingredientsList
    val descriptionText: StateFlow<String> = descriptionVM.descriptionText

    fun onEvent(event: CreateRecipeEvent) {
        when (event) {
            is CreateRecipeEvent.OnNextStep -> onNextStep()
            is CreateRecipeEvent.OnBackStep -> onBackStep()
            is CreateRecipeEvent.OnAbort -> onAbort()
            is CreateRecipeEvent.OnFinished -> TODO()
            is CreateRecipeEvent.OnCloseAbort -> closeAbortDialog()
            is CreateRecipeEvent.OnConfirmAbort -> onConfirmAbort()
        }
    }

    private fun onNextStep() {
        stepperViewModel.onEvent(StepperEvent.OnNextStep)
    }

    private fun onBackStep() {
        stepperViewModel.onEvent(StepperEvent.OnBackStep)
    }

    private fun onAbort() {
        _openAbortDialog.value = true
    }

    private fun closeAbortDialog() {
        _openAbortDialog.value = false
    }

    private fun onConfirmAbort() {
        stepperViewModel.onEvent(StepperEvent.ClearStates)
        dataVM.onEvent(CreateRecipeDataEvent.ClearStates)
        tableVM.onEvent(IngredientsTableEvent.ClearStates)
        descriptionVM.onEvent(CreateRecipeDescriptionEvent.ClearStates)
        descriptionVM.onEvent(CreateRecipeDescriptionEvent.ClearStates)
        closeAbortDialog()
    }
}

sealed class CreateRecipeEvent {
    object OnNextStep : CreateRecipeEvent()
    object OnBackStep : CreateRecipeEvent()
    object OnAbort : CreateRecipeEvent()
    object OnCloseAbort: CreateRecipeEvent()
    object OnConfirmAbort: CreateRecipeEvent()
    object OnFinished : CreateRecipeEvent()
}
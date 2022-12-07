package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import kotlinx.coroutines.flow.StateFlow

class CreateRecipeViewModel(
    private val stepperViewModel: CreateRecipeStepperViewModel,
    private val dataVM: CreateRecipeDataVM
) {
    val currentStepIndex = stepperViewModel.currentStepIndex
    val enableNextStepButton = stepperViewModel.enableNextStepButton

    val ingredientName: StateFlow<FormTextFieldState<String>> = dataVM.ingredientName
    val ingredientShortDesc: StateFlow<FormTextFieldState<String>> = dataVM.ingredientShortDesc
    val ingredientPrice: StateFlow<FormTextFieldState<Double>> = dataVM.ingredientPrice
    val ingredientDifficulty: StateFlow<FormTextFieldState<String>> = dataVM.ingredientDifficulty
    val ingredientCategory: StateFlow<FormTextFieldState<String>> = dataVM.ingredientCategory

    fun onEvent(event: CreateRecipeEvent) {
        when (event) {
            is CreateRecipeEvent.OnNextStep -> onNextStep()
            is CreateRecipeEvent.OnBackStep -> onBackStep()
            is CreateRecipeEvent.OnAbort -> onAbort()
            is CreateRecipeEvent.OnFinished -> TODO()
        }
    }

    private fun onNextStep() {
        stepperViewModel.onEvent(StepperEvent.OnNextStep)
    }

    private fun onBackStep() {
        stepperViewModel.onEvent(StepperEvent.OnBackStep)
    }

    private fun onAbort() {
        TODO("Show confirm dialog")
    }
}

sealed class CreateRecipeEvent {
    object OnNextStep : CreateRecipeEvent()
    object OnBackStep : CreateRecipeEvent()
    object OnAbort : CreateRecipeEvent()
    object OnFinished : CreateRecipeEvent()
}
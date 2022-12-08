package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import com.hsfl.springbreak.frontend.di.di
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.kodein.di.instance

class CreateRecipeStepperViewModel {
    private val recipeDataVM: CreateRecipeDataVM by di.instance()

    private val _allSteps = MutableStateFlow(
        mutableListOf(
            CreateRecipeStep(name = "Rezeptdaten"),
            CreateRecipeStep(name = "Zutaten"),
            CreateRecipeStep(name = "Beschreibung"),
            CreateRecipeStep(name = "Bild (optional)"),
        )
    )
    val allSteps: StateFlow<List<CreateRecipeStep>> = _allSteps

    private val _currentStep = MutableStateFlow(allSteps.value[0])
    val currentStep: StateFlow<CreateRecipeStep> = _currentStep

    private val _currentStepIndex = MutableStateFlow(1)
    val currentStepIndex: StateFlow<Int> = _currentStepIndex

    val enableNextStepButton: StateFlow<Boolean> = recipeDataVM.validateInputs

    fun onEvent(event: StepperEvent) {
        when (event) {
            is StepperEvent.OnNextStep -> nextStep()
            is StepperEvent.OnBackStep -> backStep()
        }
    }

    private fun nextStep() {
        when (currentStepIndex.value) {
            0 -> {
                recipeDataVM.onEvent(CreateRecipeDataEvent.OnNext)
                if (enableNextStepButton.value) {
                    allSteps.value[currentStepIndex.value].completed = true
                    _currentStepIndex.value++
                }
            }

            in 1 until allSteps.value.size -> {
                allSteps.value[currentStepIndex.value].completed = true
                _currentStepIndex.value++
            }
        }
    }

    private fun backStep() {
        if (currentStepIndex.value != 0)
            _currentStepIndex.value--
    }

}

data class CreateRecipeStep(
    val name: String,
    var completed: Boolean = false
)

sealed class StepperEvent {
    object OnNextStep : StepperEvent()
    object OnBackStep : StepperEvent()
}
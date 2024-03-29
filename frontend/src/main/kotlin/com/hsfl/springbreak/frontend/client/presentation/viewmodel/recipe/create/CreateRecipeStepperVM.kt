package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CreateRecipeDataEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CreateRecipeEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.StepperEvent
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
            CreateRecipeStep(name = "Überprüfen"),
        )
    )
    val allSteps: StateFlow<List<CreateRecipeStep>> = _allSteps

    private val _currentStepIndex = MutableStateFlow(0)
    val currentStepIndex: StateFlow<Int> = _currentStepIndex

    val enableNextStepButton: StateFlow<Boolean> = recipeDataVM.validateInputs

    fun onEvent(event: StepperEvent) {
        when (event) {
            is StepperEvent.OnNextStep -> nextStep()
            is StepperEvent.OnBackStep -> backStep()
            LifecycleEvent.OnMount -> { /* Nothing to do here */ }
            LifecycleEvent.OnUnMount -> clearStates()
        }
    }

    private fun clearStates() {
        _currentStepIndex.value = 0
        _allSteps.value.map {
            it.completed = false
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

            in 1 until allSteps.value.size - 1 -> {
                allSteps.value[currentStepIndex.value].completed = true
                _currentStepIndex.value++
            }

            allSteps.value.size - 1 -> {
                val recipeViewModel: CreateRecipeViewModel by di.instance()
                recipeViewModel.onEvent(CreateRecipeEvent.OnFinished)
                println("Abschließen")
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


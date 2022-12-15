package com.hsfl.springbreak.frontend.components.recipe.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeStepperViewModel
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import mui.material.*
import org.kodein.di.instance
import react.FC
import react.Props

val RecipeCreateStepper = FC<Props> {
    val viewModel: CreateRecipeStepperViewModel by di.instance()
    val allSteps = viewModel.allSteps.collectAsState()
    val currentStepIndex = viewModel.currentStepIndex.collectAsState()

    Stepper {
        activeStep = currentStepIndex
        orientation = Orientation.horizontal
        alternativeLabel = true
        allSteps.map { step ->
            Step {
                key = step.name
                completed = step.completed
                StepLabel { +step.name }
            }
        }
    }
}
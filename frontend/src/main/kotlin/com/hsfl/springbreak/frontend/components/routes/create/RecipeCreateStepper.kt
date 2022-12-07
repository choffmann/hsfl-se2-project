package com.hsfl.springbreak.frontend.components.routes.create

import mui.material.*
import react.FC
import react.Props

external interface RecipeCreateStepperProps: Props {
    var activeStep: Int
}

val RecipeCreateStepper = FC<RecipeCreateStepperProps> { props ->
    val stepNames = listOf("Rezeptdaten", "Zutaten", "Beschreibung", "Bild (optional)")


    Stepper {
        nonLinear = true
        activeStep = props.activeStep
        orientation = Orientation.horizontal
        alternativeLabel = true
        stepNames.map { name ->
            Step {
                key = name
                StepLabel {+name}
            }
        }
    }
}
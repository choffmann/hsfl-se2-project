package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.components.routes.create.ConfirmAbortDialog
import com.hsfl.springbreak.frontend.components.routes.create.RecipeCreateStepOne
import com.hsfl.springbreak.frontend.components.routes.create.RecipeCreateStepTwo
import com.hsfl.springbreak.frontend.components.routes.create.RecipeCreateStepper
import com.hsfl.springbreak.frontend.context.StepperContext
import csstype.*
import emotion.react.css
import mui.material.*
import mui.system.sx
import react.FC
import react.Props
import react.router.dom.NavLink
import react.useState

val CreateRecipe = FC<Props> {
    var openConfirmAbortDialog by useState(false)
    var currentStep by useState(0)

    Box {
        sx {
            margin = 8.px
        }
        StepperContext.Provider {
            value = currentStep
            RecipeCreateStepper {
                activeStep = currentStep
            }
            Box {
                sx {
                    marginTop = 16.px
                }
                when (currentStep) {
                    0 -> RecipeCreateStepOne()
                    1 -> RecipeCreateStepTwo()
                }
            }
        }
        ConfirmAbortDialog {
            open = openConfirmAbortDialog
            onClose = { openConfirmAbortDialog = false }
        }
        Box {
            sx {
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
            }
            if (currentStep == 0) {
                Button {
                    onClick = {
                        openConfirmAbortDialog = true
                    }
                    +"Abbrechen"
                }
            } else {
                Button {
                    onClick = {
                        if (currentStep != 0)
                            currentStep--
                    }
                    +"Zurück"
                }
            }
            Button {
                variant = ButtonVariant.contained
                onClick = {
                    if (currentStep in 0..2)
                        currentStep++
                }
                +if (currentStep == 3) "Abschließen" else "Weiter"
            }
        }
    }
}
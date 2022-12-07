package com.hsfl.springbreak.frontend.components.routes

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
        Box {
            sx {
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
            }
            if(currentStep == 0) {
                NavLink {
                    to = "/"
                    // ignore style
                    css {
                        textDecoration = None.none
                        color = Color.currentcolor
                    }
                    Button {
                        +"Abbrechen"
                    }
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
package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeViewModel
import com.hsfl.springbreak.frontend.components.routes.create.*
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import csstype.*
import mui.material.*
import mui.system.sx
import org.kodein.di.instance
import react.FC
import react.Props
import react.useState

val CreateRecipe = FC<Props> {
    val viewModel: CreateRecipeViewModel by di.instance()
    val currentStep = viewModel.currentStepIndex.collectAsState()
    val enableNextStepButton = viewModel.enableNextStepButton.collectAsState()
    var openConfirmAbortDialog by useState(false)

    Box {
        sx {
            margin = 8.px
        }
            RecipeCreateStepper()
            Box {
                sx {
                    marginTop = 16.px
                }
                when (currentStep) {
                    0 -> RecipeCreateData()
                    1 -> RecipeCreateIngredients()
                    2 -> RecipeCreateDescription()
                    3 -> CreateRecipeImage()
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
                    onClick = {viewModel.onEvent(CreateRecipeEvent.OnBackStep)}
                    +"Zurück"
                }
            }
            Button {
                variant = ButtonVariant.contained
                disabled = !enableNextStepButton
                onClick = {viewModel.onEvent(CreateRecipeEvent.OnNextStep)}
                +if (currentStep == 3) "Abschließen" else "Weiter"
            }
        }
    }
}
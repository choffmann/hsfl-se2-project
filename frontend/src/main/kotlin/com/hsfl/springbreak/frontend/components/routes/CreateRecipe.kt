package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CreateRecipeEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
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
import react.router.useNavigate
import react.useEffect

val CreateRecipe = FC<Props> {
    val viewModel: CreateRecipeViewModel by di.instance()
    val currentStep = viewModel.currentStepIndex.collectAsState()
    val enableNextStepButton = viewModel.enableNextStepButton.collectAsState()
    val openConfirmAbortDialog = viewModel.openAbortDialog.collectAsState()
    val navigator = useNavigate()

    useEffect(Unit) {
        cleanup {
            viewModel.onEvent(LifecycleEvent.OnUnMount)
        }
    }


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
                4 -> RecipeCreateCardPreview()
            }
        }
        ConfirmAbortDialog {
            open = openConfirmAbortDialog
            onClose = { viewModel.onEvent(CreateRecipeEvent.OnCloseAbort) }
            onConfirm = { viewModel.onEvent(CreateRecipeEvent.OnConfirmAbort) }
            navigateTo = "/"
        }
        Box {
            sx {
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
            }
            if (currentStep == 0) {
                Button {
                    onClick = {
                        viewModel.onEvent(CreateRecipeEvent.OnAbort)
                    }
                    +"Abbrechen"
                }
            } else {
                Button {
                    onClick = { viewModel.onEvent(CreateRecipeEvent.OnBackStep) }
                    +"Zurück"
                }
            }
            Button {
                variant = ButtonVariant.contained
                disabled = !enableNextStepButton
                onClick = {
                    viewModel.onEvent(CreateRecipeEvent.OnNextStep)
                    if (currentStep == 4) navigator("/")
                }
                +if (currentStep == 4) "Abschließen" else "Weiter"
            }
        }
    }
}
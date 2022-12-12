package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import com.hsfl.springbreak.frontend.client.data.model.Difficulty
import com.hsfl.springbreak.frontend.client.data.model.Ingredient
import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.client.data.repository.RecipeRepository
import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.di.di
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.instance
import web.file.File

class CreateRecipeViewModel(
    private val stepperViewModel: CreateRecipeStepperViewModel,
    private val dataVM: CreateRecipeDataVM,
    private val tableVM: IngredientsTableVM,
    private val descriptionVM: CreateRecipeDescriptionVM,
    private val imageVM: CreateRecipeImageVM,
    private val recipeRepository: RecipeRepository,
    private val scope: CoroutineScope = MainScope()
) {
    private val _openAbortDialog = MutableStateFlow(false)
    val openAbortDialog: StateFlow<Boolean> = _openAbortDialog

    val currentStepIndex = stepperViewModel.currentStepIndex
    val enableNextStepButton = stepperViewModel.enableNextStepButton

    val recipeName: StateFlow<FormTextFieldState<String>> = dataVM.recipeName
    val recipeShortDesc: StateFlow<FormTextFieldState<String>> = dataVM.recipeShortDesc
    val recipePrice: StateFlow<FormTextFieldState<Double>> = dataVM.recipePrice
    val recipeDuration: StateFlow<FormTextFieldState<Int>> = dataVM.recipeDuration
    val recipeDifficulty: StateFlow<Difficulty> = dataVM.selectedDifficulty
    val recipeImage: StateFlow<File?> = imageVM.recipeImage
    private val ingredientsList: StateFlow<List<IngredientsTableRow>> = tableVM.ingredientsList
    private val descriptionText: StateFlow<String> = descriptionVM.descriptionText

    fun onEvent(event: CreateRecipeEvent) {
        when (event) {
            is CreateRecipeEvent.OnNextStep -> onNextStep()
            is CreateRecipeEvent.OnBackStep -> onBackStep()
            is CreateRecipeEvent.OnAbort -> onAbort()
            is CreateRecipeEvent.OnFinished -> createRecipe()
            is CreateRecipeEvent.OnCloseAbort -> closeAbortDialog()
            is CreateRecipeEvent.OnConfirmAbort -> onConfirmAbort()
        }
    }

    private fun createRecipe() {
        scope.launch {
            val userState: UserState by di.instance()
            val recipe = Recipe.Create(
                title = recipeName.value.value,
                shortDescription = recipeShortDesc.value.value,
                price = recipePrice.value.value,
                duration = recipeDuration.value.value.toDouble(),
                difficultyId = recipeDifficulty.value.id,
                categoryId = 2 /*recipeCategory.value.value.toLong()*/,
                creatorId = userState.userState.value.id,
                longDescription = descriptionText.value,
                ingredients = ingredientsList.value.map {
                    Ingredient.Create(
                        name = it.item.name,
                        unit = it.item.unit,
                        amount = it.item.amount
                    )
                }
            )

            recipeRepository.createRecipe(recipe).collectLatest { response ->
                response.handleDataResponse<Recipe>(
                    onSuccess = { println(it) }
                )
            }
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
        imageVM.onEvent(CreateRecipeImageEvent.ClearStates)
        closeAbortDialog()
    }
}

sealed class CreateRecipeEvent {
    object OnNextStep : CreateRecipeEvent()
    object OnBackStep : CreateRecipeEvent()
    object OnAbort : CreateRecipeEvent()
    object OnCloseAbort : CreateRecipeEvent()
    object OnConfirmAbort : CreateRecipeEvent()
    object OnFinished : CreateRecipeEvent()
}
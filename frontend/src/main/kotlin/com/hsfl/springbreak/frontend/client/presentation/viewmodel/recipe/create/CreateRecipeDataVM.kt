package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import com.hsfl.springbreak.frontend.client.data.model.Category
import com.hsfl.springbreak.frontend.client.data.model.Difficulty
import com.hsfl.springbreak.frontend.client.data.repository.CategoryRepository
import com.hsfl.springbreak.frontend.client.data.repository.DifficultyRepository
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CreateRecipeDataEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CreateRecipeDataVM(
    private val difficultyRepository: DifficultyRepository,
    private val categoryRepository: CategoryRepository,
    private val scope: CoroutineScope = MainScope()
) {
    private val _recipeName = MutableStateFlow(FormTextFieldState("", required = true))
    val recipeName: StateFlow<FormTextFieldState<String>> = _recipeName

    private val _recipeShortDesc = MutableStateFlow(FormTextFieldState(""))
    val recipeShortDesc: StateFlow<FormTextFieldState<String>> = _recipeShortDesc

    private val _recipePrice = MutableStateFlow(FormTextFieldState(0.0))
    val recipePrice: StateFlow<FormTextFieldState<Double>> = _recipePrice

    private val _recipeDuration = MutableStateFlow(FormTextFieldState(0))
    val recipeDuration: StateFlow<FormTextFieldState<Int>> = _recipeDuration

    private val _recipeDifficulty = MutableStateFlow(FormTextFieldState("", required = true))
    val recipeDifficulty: StateFlow<FormTextFieldState<String>> = _recipeDifficulty

    private val _recipeCategory = MutableStateFlow(FormTextFieldState("", required = true))
    val recipeCategory: StateFlow<FormTextFieldState<String>> = _recipeCategory

    private val _validateInputs = MutableStateFlow(true)
    val validateInputs: StateFlow<Boolean> = _validateInputs

    private val _categoryList = MutableStateFlow<MutableList<Category>>(mutableListOf())
    val categoryList: StateFlow<List<Category>> = _categoryList

    private val _difficultyList = MutableStateFlow<MutableList<Difficulty>>(mutableListOf())
    val difficultyList: StateFlow<List<Difficulty>> = _difficultyList

    private val _selectedDifficulty = MutableStateFlow(Difficulty(-1, ""))
    val selectedDifficulty: StateFlow<Difficulty> = _selectedDifficulty

    private val _selectedCategory = MutableStateFlow(Category(-1, ""))
    val selectedCategory: StateFlow<Category> = _selectedCategory

    fun onEvent(event: CreateRecipeDataEvent) {
        when (event) {
            is CreateRecipeDataEvent.RecipeName -> setText(
                flow = _recipeName, value = event.value
            )

            is CreateRecipeDataEvent.RecipeShortDesc -> setText(
                flow = _recipeShortDesc, value = event.value
            )

            is CreateRecipeDataEvent.RecipePrice -> setText(
                flow = _recipePrice, value = event.value
            )

            is CreateRecipeDataEvent.RecipeDuration -> setText(
                flow = _recipeDuration, value = event.value
            )

            is CreateRecipeDataEvent.RecipeDifficulty -> {
                setText(
                    flow = _recipeDifficulty, value = event.value
                )
                // Komischer Fehler, welcher umgehen werden kann, wenn der String erst mit toString() aufgerufen wird
                _selectedDifficulty.value = difficultyList.value.find { it.id == event.value.toString().toInt() }!!
            }

            is CreateRecipeDataEvent.RecipeCategory -> {
                setText(
                    flow = _recipeCategory, value = event.value
                )
                // Komischer Fehler, welcher umgehen werden kann, wenn der String erst mit toString() aufgerufen wird
                _selectedCategory.value = categoryList.value.find { it.id == event.value.toString().toInt() }!!
            }

            is CreateRecipeDataEvent.OnNext -> validateInput()
            LifecycleEvent.OnMount -> {
                fetchCategories()
                fetchDifficulties()
            }

            LifecycleEvent.OnUnMount -> clearStates()
        }
    }

    private fun fetchDifficulties() = scope.launch {
        _difficultyList.value = mutableListOf()
        difficultyRepository.getAllDifficulties().collectLatest { response ->
            response.handleDataResponse<List<Difficulty>>(onSuccess = {
                UiEventState.onEvent(UiEvent.Idle)
                _difficultyList.value.addAll(it)
            })
        }
    }

    private fun fetchCategories() = scope.launch {
        _categoryList.value = mutableListOf()
        categoryRepository.getAllCategories().collectLatest { response ->
            response.handleDataResponse<List<Category>>(onSuccess = {
                UiEventState.onEvent(UiEvent.Idle)
                _categoryList.value.addAll(it)
            })
        }
    }

    private fun clearStates() {
        _recipeName.value = FormTextFieldState("", required = true)
        _recipeDifficulty.value = FormTextFieldState("", required = true)
        _recipeCategory.value = FormTextFieldState("", required = true)
        _recipePrice.value = FormTextFieldState(0.0)
        _recipeDuration.value = FormTextFieldState(0)
        _recipeShortDesc.value = FormTextFieldState("")
        _difficultyList.value = mutableListOf()
        _categoryList.value = mutableListOf()
        _validateInputs.value = true
    }

    private fun validateInput() {
        if (recipeName.value.value.isEmpty() && recipeDifficulty.value.value.isEmpty() && recipeCategory.value.value.isEmpty()) {
            setTextError(_recipeName, "Rezeptname muss angegeben werden")
            setTextError(_recipeDifficulty, "Schwierigkeitsgrad muss angegeben werden")
            setTextError(_recipeCategory, "Kategorie muss angegeben werden")
        } else if (recipeName.value.value.isEmpty()) {
            setTextError(_recipeName, "Rezeptname muss angegeben werden")
        } else if (recipeDifficulty.value.value.isEmpty()) {
            setTextError(_recipeDifficulty, "Schwierigkeitsgrad muss angegeben werden")
        } else if (recipeCategory.value.value.isEmpty()) {
            setTextError(_recipeCategory, "Kategorie muss angegeben werden")
        } else {
            _validateInputs.value = true
        }
    }

    private fun <T> setTextError(flow: MutableStateFlow<FormTextFieldState<T>>, msg: String) {
        flow.value = flow.value.copy(error = true, errorMsg = msg)
        _validateInputs.value = false
    }

    private fun resetError() {
        _validateInputs.value = true
    }

    private fun <T> setText(flow: MutableStateFlow<FormTextFieldState<T>>, value: T) {
        flow.value = flow.value.copy(value = value, error = false, errorMsg = "")
        if (flow.value.required) resetError()
    }
}

data class FormTextFieldState<T>(
    val value: T, val required: Boolean = false, val error: Boolean = false, val errorMsg: String = ""
)

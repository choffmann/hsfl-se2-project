package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.detail

import com.hsfl.springbreak.frontend.client.data.model.*
import com.hsfl.springbreak.frontend.client.data.repository.RecipeRepository
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.RecipeDetailEvent
import com.hsfl.springbreak.frontend.di.di
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.instance

class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository,
    private val scope: CoroutineScope = MainScope()
) {
    private val testRecipe = Recipe(
        id = 1,
        title = "Chicken Tikka Masala",
        creator = User(
            id = 1,
            firstName = "Jeeve",
            lastName = "Philodendron",
            email = "jeeve.philodendron@plants.com",
            password = "geheim",
            image = "https://fangblatt.de/media/image/12/b8/b5/Philodendron-Green-Princess-8_600x600.jpg"
        ),
        price = 15.0,
        createTime = "2022-12-12T20:30.20307",
        image = "https://image.essen-und-trinken.de/11854764/t/Gb/v8/w960/r1/-/chicken-tikka-masala-c3c77cebac45a391e04fcbcff54bee08-chicken-tikka-masala-jpg--20494-.jpg",
        //image = null,
        shortDescription =
        "Ein sehr leckeres Gericht. Es passt perfekt zu Basmatireis",
        duration = 60.0,
        difficulty = Difficulty(
            id = 1,
            name = "Mittel"
        ),
        category = Category(
            id = 1,
            name = "Asiatisch"
        ),
        ingredients = listOf(
            Ingredient(id = 1, name = "Knoblauchzehen", amount = 6, unit = ""),
            Ingredient(id = 2, name = "Senfsaat", amount = 1, unit = "EL"),
            Ingredient(id = 3, name = "Paprikapulver", amount = 1, unit = "EL"),
            Ingredient(id = 3, name = "Garam Masala", amount = 3, unit = "EL"),
            Ingredient(id = 4, name = "Tomatenmark", amount = 2, unit = "EL"),
            Ingredient(id = 5, name = "Naturjoghurt", amount = 200, unit = "g"),
        ),
        longDescription = "1. Knoblauch und Ingwer mit dem feinsten Aufsatz der Käsereibe reiben und in eine Schüssel geben. Die Chilis so fein wie möglich schneiden und mit dem Knoblauch und Ingwer mischen. Einen guten Schuss Olivenöl in einer Pfanne erhitzen und die Senfsaat hinengeben. Sobald sie zu platzen beginnen, zusammen mit Cumin, Paprikapulver, dem gemahlenen Koriander und 2 El Garam Masala zur Knoblauch-Ingwer-Mischung geben. Die Hälfte der Mischung mit dem Joghurt vermengen, mit den Putenstücken in eine Schüssel geben, umrühren und ca.eine halbe Stunde marinieren.\n\n2. Butter schmelzen, Zwiebeln und die übrige Hälfte der Gewürzmischung dazugeben, ca. 15 min. köcheln lassen ohne es zu sehr braun werden zu lassen- es sollte angenehm riechen! Tomatenmark, die gemahlenen Nüsse, einen halben Liter Wasser und einen halben Tl Salz dazugeben. Gut umrühren und einige Minuten köcheln lassen bis die Soße reduziert hat und leicht dick geworden ist und zur Seite stellen.\n\n3. Die marinierten Putenstücke in eine heiße Pfanne geben und anbraten.\n\n4. Die Soße erhitzen und Crème Double und den anderen El Garam Masala hinzugeben. Sobald es zu kochen beginnt, Herd abschalten und die gebratenen Putenstücke dazugeben. Gegebenenfalls nachwürzen und mit gehacktem Koriander und Limettensaft servieren. Dazu passt Basmatireis.",
        views = 120
    )

    private val _editMode = MutableStateFlow(true)
    val editMode: StateFlow<Boolean> = _editMode

    private val _isMyRecipe = MutableStateFlow(true)
    val isMyRecipe: StateFlow<Boolean> = _isMyRecipe

    private val _recipe = MutableStateFlow(testRecipe)
    val recipe: StateFlow<Recipe> = _recipe

    fun onEvent(event: RecipeDetailEvent) {
        when (event) {
            LifecycleEvent.OnMount -> TODO()
            LifecycleEvent.OnUnMount -> TODO()
            RecipeDetailEvent.OnDelete -> TODO()
            RecipeDetailEvent.OnEdit -> _editMode.value = true
            RecipeDetailEvent.CancelEdit -> _editMode.value = false
            RecipeDetailEvent.OnFavorite -> TODO()
            RecipeDetailEvent.OnUnFavorite -> TODO()
            is RecipeDetailEvent.RecipeId -> {
                fetchRecipe(event.id)
            }

        }
    }

    private fun fetchRecipe(recipeId: Int) = scope.launch {
        recipeRepository.getRecipeById(recipeId).collectLatest { response ->
            response.handleDataResponse<Recipe>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.Idle)
                    _recipe.value = it
                    checkIsMyRecipe(it)
                }
            )
        }

    }

    private fun checkIsMyRecipe(recipe: Recipe) {
        val userState: UserState by di.instance()
        _isMyRecipe.value = userState.userState.value.id == recipe.creator.id
    }
}
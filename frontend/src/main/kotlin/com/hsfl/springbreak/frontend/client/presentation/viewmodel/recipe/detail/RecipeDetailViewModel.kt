package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.detail

import com.hsfl.springbreak.frontend.client.data.model.*
import com.hsfl.springbreak.frontend.client.data.repository.FavoritesRepository
import com.hsfl.springbreak.frontend.client.data.repository.RatingRepository
import com.hsfl.springbreak.frontend.client.data.repository.RecipeRepository
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.MessageViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.RootViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.SnackbarEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.RecipeDetailEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.RootEvent
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
    private val favoritesRepository: FavoritesRepository,
    private val ratingRepository: RatingRepository,
    private val userState: UserState,
    private val authState: AuthState,
    private val scope: CoroutineScope = MainScope()
) {
    /*private val testRecipe = Recipe(
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
    )*/

    val emptyRecipe = Recipe(
        id = -1,
        title = "",
        creator = User(
            id = -1,
            firstName = "",
            lastName = "",
            email = "",
            password = "",
            image = ""
        ),
        price = 0.0,
        createTime = "",
        image = null,
        shortDescription = "",
        duration = 0.0,
        difficulty = Difficulty(id = -1, name = ""),
        category = Category(id = -1, name = ""),
        ingredients = listOf(),
        longDescription = "",
        views = 0,
        score = 0.0
    )

    private val _editMode = MutableStateFlow(false)
    val editMode: StateFlow<Boolean> = _editMode

    private val _isMyRecipe = MutableStateFlow(false)
    val isMyRecipe: StateFlow<Boolean> = _isMyRecipe

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _recipe = MutableStateFlow(emptyRecipe)
    val recipe: StateFlow<Recipe> = _recipe

    private val _openDeleteDialog = MutableStateFlow(false)
    val openDeleteDialog: StateFlow<Boolean> = _openDeleteDialog

    fun onEvent(event: RecipeDetailEvent) {
        when (event) {
            LifecycleEvent.OnMount -> {}
            LifecycleEvent.OnUnMount -> clearStates()
            RecipeDetailEvent.OnDelete -> openDeleteDialog()
            RecipeDetailEvent.OnDeleteDialogAbort -> closeDeleteDialog()
            RecipeDetailEvent.OnDeleteDialogConfirm -> deleteRecipe()
            RecipeDetailEvent.OnEdit -> _editMode.value = true
            RecipeDetailEvent.CancelEdit -> _editMode.value = false
            RecipeDetailEvent.OnFavorite -> onFavorite()
            RecipeDetailEvent.OnUnFavorite -> TODO()
            is RecipeDetailEvent.RecipeId -> {
                fetchRecipe(event.id)
                // Only fetch favorite when user is logged in
                if (authState.authorized.value) fetchIsFavorite()
            }

            is RecipeDetailEvent.OnScoreChanged -> {
                if (authState.authorized.value) sendRating(event.score)
                else UiEventState.onEvent(UiEvent.ShowMessage("Bitte melde dich an um dieses Rezept zu bewerten"))
            }
        }
    }

    private fun openDeleteDialog() {
        _openDeleteDialog.value = true
    }

    private fun closeDeleteDialog() {
        _openDeleteDialog.value = false
    }

    private fun deleteRecipe() = scope.launch {
        recipeRepository.deleteRecipe(recipe.value.id).collectLatest { response ->
            response.handleDataResponse<Recipe>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.ShowMessage("Das Rezept wurde erfolgreich gelöscht"))
                    closeDeleteDialog()
                    deleteRecipeInRouter(it)
                }
            )
        }
    }

    private fun deleteRecipeInRouter(recipe: Recipe) {
        val rootViewModel: RootViewModel by di.instance()
        rootViewModel.onEvent(RootEvent.OnDeleteRecipe(recipe))
    }

    private fun sendRating(stars: Double) = scope.launch {
        val rating = Recipe.Rating(stars = stars, recipeId = recipe.value.id, userId = userState.userState.value.id)
        ratingRepository.sendRating(rating).collectLatest { response ->
            response.handleDataResponse<Double>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.ShowMessage("Deine Bewertung wurde erfolgreich versendet"))
                    _recipe.value = recipe.value.copy(score = it)
                }
            )
        }
    }

    private fun clearStates() {
        _editMode.value = false
        _isMyRecipe.value = false
        _recipe.value = emptyRecipe
        _openDeleteDialog.value = true
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

    private fun fetchIsFavorite() = scope.launch {
        favoritesRepository.getFavorites(userState.userState.value.id).collectLatest { response ->
            response.handleDataResponse<List<Recipe>>(
                onSuccess = { list ->
                    _isFavorite.value = list.find { it.id == recipe.value.id } != null
                }
            )
        }
    }

    private fun checkIsMyRecipe(recipe: Recipe) {
        val userState: UserState by di.instance()
        _isMyRecipe.value = userState.userState.value.id == recipe.creator.id
    }

    private fun onFavorite() = scope.launch {
        if (!authState.authorized.value) {
            UiEventState.onEvent(UiEvent.ShowMessage("Bitte melde dich an um ein Rezept zu speichern."))
        } else if (isFavorite.value) {
            deleteFavorite()
        } else {
            setFavorite()
        }
    }

    private fun deleteFavorite() = scope.launch {
        favoritesRepository.deleteFavorite(userState.userState.value.id, recipe.value.id).collectLatest { response ->
            response.handleDataResponse<Recipe>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.Idle)
                    _isFavorite.value = false
                    MessageViewModel.onEvent(SnackbarEvent.Show("Das Rezept wurde von deiner Favoritenliste entfernt."))
                }
            )
        }
    }

    private fun setFavorite() = scope.launch {
        favoritesRepository.setFavorite(userState.userState.value.id, recipe.value.id).collectLatest { response ->
            response.handleDataResponse<Recipe>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.Idle)
                    _isFavorite.value = true
                    MessageViewModel.onEvent(SnackbarEvent.Show("Das Rezept wurde zu deinen Favoriten hinzugefügt."))
                }
            )
        }
    }
}
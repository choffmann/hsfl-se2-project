package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe

import com.hsfl.springbreak.frontend.client.data.DataResponse
import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.client.data.repository.CategoryRepository
import com.hsfl.springbreak.frontend.client.data.repository.FavoritesRepository
import com.hsfl.springbreak.frontend.client.data.repository.RecipeRepository
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.MessageViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.SnackbarEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.RecipeCardEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val categoryRepository: CategoryRepository,
    private val recipeRepository: RecipeRepository,
    private val userState: UserState,
    private val authState: AuthState,
    private val scope: CoroutineScope = MainScope()
) {
    private val listType = MutableStateFlow<RecipeListType>(RecipeListType.HomeList.CheapTab)

    private val _recipeList = MutableStateFlow(mutableListOf<RecipeType>())
    val recipeList: StateFlow<List<RecipeType>> = _recipeList

    private val _randomState = MutableStateFlow(0)
    val randomState: StateFlow<Int> = _randomState

    fun onEvent(event: RecipeCardEvent) {
        when (event) {
            is RecipeCardEvent.OnFavorite -> onFavorite(event.id)
            LifecycleEvent.OnMount -> {}
            LifecycleEvent.OnUnMount -> clearStates()
            is RecipeCardEvent.OnLaunch -> {
                onInit(event.type)
                listType.value = event.type
            }
        }
    }

    private fun clearStates() {
        _recipeList.value = mutableListOf()
        _randomState.value = 0
    }

    private fun onInit(listType: RecipeListType) {
        if (authState.authorized.value || listType is RecipeListType.HomeList || listType is RecipeListType.CategoryList) {
            scope.launch {
                when (listType) {
                    is RecipeListType.CategoryList -> categoryRepository.getRecipesByCategory(listType.id).saveToState()
                    RecipeListType.FavoriteList -> favoritesRepository.getFavorites(userState.userState.value.id)
                        .saveToState()

                    RecipeListType.HomeList.AllTab -> recipeRepository.getAllRecipes().saveToState()
                    RecipeListType.HomeList.CheapTab -> recipeRepository.getRecipeCheapOrder().saveToState()
                    RecipeListType.HomeList.FastTab -> recipeRepository.getRecipeFastOrder().saveToState()
                    RecipeListType.HomeList.PopularTab -> recipeRepository.getRecipePopularOrder().saveToState()
                    RecipeListType.MyRecipeList -> recipeRepository.getMyFavorites(userState.userState.value.id).saveToState()
                }
            }
        } else {
            _recipeList.value = mutableListOf()
        }
    }

    private suspend fun Flow<DataResponse<List<Recipe>>>.saveToState() {
        this.collectLatest { response ->
            response.handleDataResponse<List<Recipe>>(onSuccess = { list ->
                list.forEach { recipe ->
                    if (authState.authorized.value) {
                        favoritesRepository.getFavorites(userState.userState.value.id).collectLatest { response ->
                            response.handleDataResponse<List<Recipe>>(onSuccess = { list ->
                                _recipeList.value.add(
                                    RecipeType(
                                        recipe = recipe,
                                        isMyRecipe = recipe.creator.id == userState.userState.value.id,
                                        isFavorite = list.find { it.id == recipe.id } != null
                                    )
                                )
                            })
                        }
                    } else {
                        _recipeList.value.add(
                            RecipeType(
                                recipe = recipe,
                                isMyRecipe = false,
                                isFavorite = false
                            )
                        )
                    }
                }
                forceUpdate()
                UiEventState.onEvent(UiEvent.Idle)
            })
        }
    }


    private fun forceUpdate() {
        _randomState.value = randomState.value + 1
    }

    private fun onFavorite(id: Int) = scope.launch {
        // Only get favorites when user is logged in
        if (!authState.authorized.value) {
            UiEventState.onEvent(UiEvent.ShowMessage("Du musst dich zuerst anmelden um ein Rezept zu speichern."))
        } else if (recipeList.value.filter { it.isFavorite }.find { it.recipe.id == id } != null) {
            deleteFavorite(id)
        } else {
            setFavorite(id)
        }
    }

    private fun deleteFavorite(id: Int) = scope.launch {
        favoritesRepository.deleteFavorite(userState.userState.value.id, id).collectLatest { response ->
            response.handleDataResponse<Recipe>(onSuccess = {
                UiEventState.onEvent(UiEvent.Idle)
                // To delete on favorite view
                if(listType.value is RecipeListType.FavoriteList) {
                    val recipe = _recipeList.value.find { it.recipe.id == id }
                    _recipeList.value.remove(recipe)
                } else _recipeList.value.find { it.recipe.id == id }?.isFavorite = false
                MessageViewModel.onEvent(SnackbarEvent.Show("Das Rezept wurde von deiner Favoritenliste entfernt."))
                forceUpdate()
            })
        }
    }

    private fun setFavorite(id: Int) = scope.launch {
        favoritesRepository.setFavorite(userState.userState.value.id, id).collectLatest { response ->
            response.handleDataResponse<Recipe>(onSuccess = {
                UiEventState.onEvent(UiEvent.Idle)
                _recipeList.value.find { it.recipe.id == id }?.isFavorite = true
                MessageViewModel.onEvent(SnackbarEvent.Show("Das Rezept wurde zu deinen Favoriten hinzugefügt."))
                forceUpdate()
            })
        }
    }
}

sealed class RecipeListType {
    data class CategoryList(val id: Int) : RecipeListType()
    object FavoriteList : RecipeListType()
    object MyRecipeList: RecipeListType()
    sealed class HomeList : RecipeListType() {
        object CheapTab : HomeList()
        object FastTab : HomeList()
        object PopularTab : HomeList()
        object AllTab : HomeList()
    }
}

data class RecipeType(val recipe: Recipe, var isFavorite: Boolean, val isMyRecipe: Boolean)
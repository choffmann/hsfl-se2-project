package com.hsfl.springbreak.frontend.client.presentation.viewmodel

import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.data.repository.RecipeRepository
import com.hsfl.springbreak.frontend.client.presentation.state.*
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.RootEvent
import com.hsfl.springbreak.frontend.di.di
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.instance
import web.storage.localStorage

class RootViewModel(
    private val recipeRepository: RecipeRepository,
    private val scope: CoroutineScope = MainScope()
) {

    private val _recipeList = MutableStateFlow<List<Recipe>>(emptyList())
    val recipeList: StateFlow<List<Recipe>> = _recipeList

    fun onEvent(event: RootEvent) {
        when (event) {
            LifecycleEvent.OnMount -> {
                fetchRecipeList()
                checkIsLoggedIn()
            }
            LifecycleEvent.OnUnMount -> clearStates()
        }
    }

    private fun clearStates() {
        _recipeList.value = emptyList()
    }

    private fun fetchRecipeList() = scope.launch {
        recipeRepository.getAllRecipes().collectLatest { response ->
            response.handleDataResponse<List<Recipe>>(
                onSuccess = {
                    UiEventState.onEvent(UiEvent.Idle)
                    _recipeList.value = it
                }
            )
        }
    }

    private fun checkIsLoggedIn() {
        val authState: AuthState by di.instance()
        if (localStorage.getItem("isLoggedIn").toBoolean()) {
            authState.onEvent(AuthEvent.IsAuthorized)
            readUserState()
        } else {
            authState.onEvent(AuthEvent.IsUnauthorized)
        }
    }

    private fun readUserState() {
        val userState: UserState by di.instance()
        userState.onEvent(UserStateEvent.SetUser(
            User(
                id = localStorage.getItem("userId")?.toInt() ?: -1,
                firstName = localStorage.getItem("userFirstName") ?: "",
                lastName = localStorage.getItem("userLastName")?: "",
                email = localStorage.getItem("userEmail")?: "",
                image = localStorage.getItem("userImage"),
                password = ""
            )
        ))
    }
}
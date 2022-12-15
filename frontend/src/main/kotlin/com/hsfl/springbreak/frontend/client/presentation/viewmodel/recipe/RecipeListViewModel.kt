package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe

import com.hsfl.springbreak.frontend.client.data.repository.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class RecipeListViewModel(
    private val recipeRepository: RecipeRepository,
    private val scope: CoroutineScope = MainScope()
) {
}
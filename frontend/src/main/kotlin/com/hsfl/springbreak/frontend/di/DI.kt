package com.hsfl.springbreak.frontend.di

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.repository.*
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.AuthDialogViewModel
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.CategoryListViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.DebugViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.ProfileViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val di = DI {
    // Client
    bindSingleton { Client() }

    // Repositories
    bindSingleton<UserRepository> { UserRepositoryImpl(instance()) }
    bindSingleton<IngredientRepository> { IngredientRepositoryImpl(instance()) }
    bindSingleton<DifficultyRepository> { DifficultyRepositoryImpl(instance()) }
    bindSingleton<RecipeRepository> { RecipeRepositoryImpl(instance()) }
    bindSingleton<CategoryRepository> { CategoryRepositoryImpl(instance()) }

    // ViewModels
    bindSingleton { DebugViewModel(instance()) }
    bindSingleton { AuthDialogViewModel(instance()) }
    bindSingleton { CreateRecipeStepperViewModel() }
    bindSingleton { CreateRecipeViewModel(instance(), instance(), instance(), instance(), instance(), instance()) }
    bindSingleton { CreateRecipeDataVM(instance(), instance()) }
    bindSingleton { IngredientsTableVM() }
    bindSingleton { IngredientsDialogVM(instance(), instance()) }
    bindSingleton { CreateRecipeIngredientsVM(instance()) }
    bindSingleton { CreateRecipeDescriptionVM() }
    bindSingleton { CreateRecipeImageVM() }
    bindSingleton { IngredientEditDialogVM(instance()) }
    bindSingleton { ProfileViewModel(instance()) }
    bindSingleton { CategoryListViewModel(instance()) }

    // States
    bindSingleton { AuthState() }
    bindSingleton { UserState() }
}
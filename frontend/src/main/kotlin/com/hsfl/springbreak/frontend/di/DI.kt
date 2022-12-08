package com.hsfl.springbreak.frontend.di

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.repository.UserRepository
import com.hsfl.springbreak.frontend.client.data.repository.UserRepositoryImpl
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.AuthDialogViewModel
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.DebugViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val di = DI {
    // Client
    bindSingleton { Client() }

    // Repositories
    bindSingleton<UserRepository> { UserRepositoryImpl(instance()) }

    // ViewModels
    bindSingleton { DebugViewModel(instance()) }
    bindSingleton { AuthDialogViewModel(instance()) }
    bindSingleton { CreateRecipeStepperViewModel() }
    bindSingleton { CreateRecipeViewModel(instance(), instance()) }
    bindSingleton { CreateRecipeDataVM() }
    bindSingleton { IngredientsTableVM() }
    bindSingleton { IngredientsDialogVM(instance()) }
    bindSingleton { CreateRecipeIngredientsVM(instance()) }
    bindSingleton { CreateRecipeDescriptionVM() }

    // States
    bindSingleton { AuthState() }
}
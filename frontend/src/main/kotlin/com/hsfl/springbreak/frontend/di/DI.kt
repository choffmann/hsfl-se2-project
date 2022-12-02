package com.hsfl.springbreak.frontend.di

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.repository.UserRepository
import com.hsfl.springbreak.frontend.client.data.repository.UserRepositoryImpl
import com.hsfl.springbreak.frontend.client.presentation.controller.AuthDialogController
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.DebugViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.LoginViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.RegisterViewModel
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val di = DI {
    // Client
    bindSingleton { Client() }

    // Repositories
    bindSingleton<UserRepository> { UserRepositoryImpl(instance()) }

    // ViewModels
    bindSingleton { LoginViewModel(instance()) }
    bindSingleton { RegisterViewModel(instance()) }
    bindSingleton { DebugViewModel(instance()) }

    // Controllers
    bindSingleton { AuthDialogController(instance(), instance()) }

    // States
    bindSingleton { AuthState() }
}
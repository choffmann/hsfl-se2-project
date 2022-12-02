package com.hsfl.springbreak.frontend.components.auth

import com.hsfl.springbreak.frontend.client.Client
import com.hsfl.springbreak.frontend.client.repository.UserRepositoryImpl
import com.hsfl.springbreak.frontend.client.viewmodel.LoginEvent
import com.hsfl.springbreak.frontend.client.viewmodel.LoginViewModel
import com.hsfl.springbreak.frontend.client.viewmodel.RegisterEvent
import com.hsfl.springbreak.frontend.client.viewmodel.RegisterViewModel
import com.hsfl.springbreak.frontend.utils.collectAsState
import react.FC
import react.Props

external interface LoginDialogProviderProps : Props {
    var open: Boolean
}

val AuthDialogProvider = FC<LoginDialogProviderProps> { props ->
    val loginViewModel = LoginViewModel(
        UserRepositoryImpl(Client())
    )
    val registerViewModel = RegisterViewModel()
    val openLoginDialog = loginViewModel.openDialog.collectAsState()
    val openRegisterDialog = registerViewModel.openDialog.collectAsState()

    LoginDialog {
        open = openLoginDialog
        onClose = { event, reason ->
            loginViewModel.onEvent(LoginEvent.OnCloseDialog(event, reason))
        }
        onLogin = { loginViewModel.onEvent(LoginEvent.OnLogin) }
        onRegister = { loginViewModel.onEvent(LoginEvent.OnRegister) }
        onEmailTextChanged = { loginViewModel.onEvent(LoginEvent.EnteredEmail(it)) }
        onPasswordTextChanged = { loginViewModel.onEvent(LoginEvent.EnteredPassword(it)) }
    }

    RegisterDialog {
        open = openRegisterDialog
        onClose = { event, reason ->
            registerViewModel.onEvent(RegisterEvent.OnCloseDialog(event, reason))
        }
    }
}
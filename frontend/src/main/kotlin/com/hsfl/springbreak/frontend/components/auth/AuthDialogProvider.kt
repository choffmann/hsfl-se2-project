package com.hsfl.springbreak.frontend.components.auth

import com.hsfl.springbreak.frontend.client.data.Client
import com.hsfl.springbreak.frontend.client.data.repository.UserRepositoryImpl
import com.hsfl.springbreak.frontend.client.presentation.controller.AuthController
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.LoginEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.LoginViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.RegisterEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.RegisterViewModel
import com.hsfl.springbreak.frontend.utils.collectAsState
import react.FC
import react.Props

external interface LoginDialogProviderProps : Props {
    var loginButtonPressed: Boolean
}

val AuthDialogProvider = FC<LoginDialogProviderProps> { props ->
    val loginViewModel = LoginViewModel(
        UserRepositoryImpl(Client())
    )
    //val loginController = AuthController()
    /*val openLoginDialog = loginViewModel.openDialog.collectAsState()
    val openRegisterDialog = registerViewModel.openDialog.collectAsState()*/

    /*if (props.loginButtonPressed) {
        loginViewModel.onEvent(LoginEvent.OnOpenDialog)
        //props.loginButtonPressed = false
    }

    LoginDialog {
        open = openLoginDialog
        onClose = { event, reason ->
            //props.onClose(event, reason)
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
    }*/
}
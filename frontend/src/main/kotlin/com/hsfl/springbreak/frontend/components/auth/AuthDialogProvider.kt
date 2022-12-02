package com.hsfl.springbreak.frontend.components.auth

import com.hsfl.springbreak.frontend.client.presentation.controller.AuthDialogController
import com.hsfl.springbreak.frontend.client.presentation.controller.AuthDialogControllerEvent
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import org.kodein.di.instance
import react.FC
import react.Props

external interface LoginDialogProviderProps : Props {
    var loginButtonPressed: Boolean
}

val AuthDialogProvider = FC<LoginDialogProviderProps> { props ->
    val controller: AuthDialogController by di.instance()
    val openLoginDialog = controller.loginDialogOpen.collectAsState()
    val openRegisterDialog = controller.registerDialogOpen.collectAsState()

    LoginDialog {
        open = openLoginDialog
        onClose = { event, reason ->
            controller.onEvent(AuthDialogControllerEvent.OnCloseLoginDialog(event, reason))
        }
        onLogin = { controller.onEvent(AuthDialogControllerEvent.OnLogin) }
        onRegister = { controller.onEvent(AuthDialogControllerEvent.OpenRegisterDialog) }
        onEmailTextChanged = { /*controller.onEvent(LoginEvent.EnteredEmail(it))*/ }
        onPasswordTextChanged = { /*controller.onEvent(LoginEvent.EnteredPassword(it)) */ }
    }

    RegisterDialog {
        open = openRegisterDialog
        onClose = { event, reason ->
            controller.onEvent(AuthDialogControllerEvent.OnCloseRegisterDialog(event, reason))
        }
    }
}
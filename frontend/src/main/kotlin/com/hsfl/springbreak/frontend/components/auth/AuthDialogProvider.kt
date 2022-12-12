package com.hsfl.springbreak.frontend.components.auth

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.AuthDialogViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.AuthDialogEvent
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import org.kodein.di.instance
import react.FC
import react.Props
import react.useState
import web.file.File


val AuthDialogProvider = FC<Props> {
    val viewModel: AuthDialogViewModel by di.instance()
    val openLoginDialog = viewModel.loginDialogOpen.collectAsState()
    val openRegisterDialog = viewModel.registerDialogOpen.collectAsState()

    var loginEmailText by useState("")
    var loginPasswordText by useState("")

    var registerFirstNameText by useState("")
    var registerLastNameText by useState("")
    var registerEmailText by useState("")
    var registerPasswordText by useState("")
    var registerConfirmedPasswordText by useState("")
    var registerProfileImage by useState<File>()
    val passwordTextState = viewModel.passwordTextConfirmation.collectAsState()


    LoginDialog {
        open = openLoginDialog
        onClose = { event, reason ->
            viewModel.onEvent(AuthDialogEvent.OnCloseLoginDialog(event, reason))
        }
        onLogin = {
            viewModel.onEvent(
                AuthDialogEvent.OnLogin(
                    email = loginEmailText,
                    password = loginPasswordText
                )
            )
        }
        onRegister = { viewModel.onEvent(AuthDialogEvent.OpenRegisterDialog) }
        onEmailTextChanged = { loginEmailText = it }
        onPasswordTextChanged = { loginPasswordText = it }
    }

    RegisterDialog {
        open = openRegisterDialog
        onClose = { event, reason ->
            viewModel.onEvent(AuthDialogEvent.OnCloseRegisterDialog(event, reason))
        }
        onRegister = {
            viewModel.onEvent(
                AuthDialogEvent.OnRegister(
                    firstName = registerFirstNameText,
                    lastName = registerLastNameText,
                    email = registerEmailText,
                    password = registerPasswordText,
                    confirmedPassword = registerConfirmedPasswordText,
                    profileImage = registerProfileImage,
                )
            )
        }
        onFirstNameText = { registerFirstNameText = it }
        onLastNameNameText = { registerLastNameText = it }
        onEmailText = { registerEmailText = it }
        onPasswordText = { registerPasswordText = it }
        onConfirmedPasswordText = { registerConfirmedPasswordText = it }
        onProfileImageChanged = { registerProfileImage = it }
        confirmedPasswordTextState = passwordTextState
    }
}
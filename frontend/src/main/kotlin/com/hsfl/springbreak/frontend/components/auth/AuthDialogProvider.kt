package com.hsfl.springbreak.frontend.components.auth

import com.hsfl.springbreak.frontend.client.presentation.controller.AuthDialogController
import com.hsfl.springbreak.frontend.client.presentation.controller.AuthDialogControllerEvent
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import org.kodein.di.instance
import react.FC
import react.Props
import react.useState
import web.file.File


val AuthDialogProvider = FC<Props> {
    val controller: AuthDialogController by di.instance()
    val openLoginDialog = controller.loginDialogOpen.collectAsState()
    val openRegisterDialog = controller.registerDialogOpen.collectAsState()

    var loginEmailText by useState("")
    var loginPasswordText by useState("")

    var registerFirstNameText by useState("")
    var registerLastNameText by useState("")
    var registerEmailText by useState("")
    var registerPasswordText by useState("")
    var registerConfirmedPasswordText by useState("")
    var registerProfileImage by useState<File>()


    LoginDialog {
        open = openLoginDialog
        onClose = { event, reason ->
            controller.onEvent(AuthDialogControllerEvent.OnCloseLoginDialog(event, reason))
        }
        onLogin = {
            controller.onEvent(
                AuthDialogControllerEvent.OnLogin(
                    email = loginEmailText,
                    password = loginPasswordText
                )
            )
        }
        onRegister = { controller.onEvent(AuthDialogControllerEvent.OpenRegisterDialog) }
        onEmailTextChanged = { loginEmailText = it }
        onPasswordTextChanged = { loginPasswordText = it }
    }

    RegisterDialog {
        open = openRegisterDialog
        onClose = { event, reason ->
            controller.onEvent(AuthDialogControllerEvent.OnCloseRegisterDialog(event, reason))
        }
        onRegister = {
            controller.onEvent(
                AuthDialogControllerEvent.OnRegister(
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
    }
}
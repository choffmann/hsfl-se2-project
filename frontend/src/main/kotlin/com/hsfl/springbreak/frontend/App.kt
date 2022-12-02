package com.hsfl.springbreak.frontend

import browser.document
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.AuthViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.UiEventViewModel
import com.hsfl.springbreak.frontend.components.Header
import com.hsfl.springbreak.frontend.components.auth.AuthDialogProvider
import com.hsfl.springbreak.frontend.components.routes.Home
import com.hsfl.springbreak.frontend.components.snackbar.MessageSnackbar
import com.hsfl.springbreak.frontend.context.AuthorizedContext
import com.hsfl.springbreak.frontend.context.UiStateContext
import com.hsfl.springbreak.frontend.utils.collectAsState
import dom.html.HTMLButtonElement
import mui.material.CssBaseline
import react.*
import react.dom.client.createRoot
import react.dom.events.MouseEventHandler

fun main() {
    createRoot(document.createElement("div").also { document.body.appendChild(it) }).render(Root.create())
}

private val Root = FC<Props> {
    val uiState = UiEventViewModel.uiState.collectAsState()
    val authorized = AuthViewModel.authorized.collectAsState()

    AuthorizedContext.Provider(value = authorized) {
        UiStateContext.Provider(value = uiState) {
            App()
        }
    }
}

private val App = FC<Props> {props ->
    var loginDialogOpen by useState(false)

    val handleOnLoginButtonClicked: MouseEventHandler<HTMLButtonElement> = {
        loginDialogOpen = true
    }

    // Default Css
    CssBaseline()

    // Login Dialog
    AuthDialogProvider {
        open = loginDialogOpen
    }

    MessageSnackbar()

    // Display Header
    Header {
        onLogoClicked = { println("click") }
        onLoginButtonClicked = handleOnLoginButtonClicked
        Home()
    }
}
package com.hsfl.springbreak.frontend

import browser.document
import com.hsfl.springbreak.frontend.client.viewmodel.AuthViewModel
import com.hsfl.springbreak.frontend.client.viewmodel.UiEvent
import com.hsfl.springbreak.frontend.client.viewmodel.UiEventViewModel
import com.hsfl.springbreak.frontend.components.Header
import com.hsfl.springbreak.frontend.components.auth.AuthDialogProvider
import com.hsfl.springbreak.frontend.components.routes.Home
import com.hsfl.springbreak.frontend.components.snackbar.MessageSnackbar
import com.hsfl.springbreak.frontend.context.AuthorizedContext
import com.hsfl.springbreak.frontend.context.UiStateContext
import com.hsfl.springbreak.frontend.utils.collectAsState
import com.hsfl.springbreak.frontend.utils.color
import csstype.FontWeight
import dom.html.HTMLButtonElement
import mui.material.CssBaseline
import mui.material.Typography
import mui.system.sx
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
    val uiState = useContext(UiStateContext)
    val isAuthorized = useContext(AuthorizedContext)

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
        Typography { +"Hello World" }

        Typography {
            sx { fontWeight = FontWeight.bold }
            color = "text.secondary"
            +"Debug"
        }
        val uiStateString = when (uiState) {
            is UiEvent.Idle -> "Idle"
            is UiEvent.ShowError -> "ShowError(msg: ${uiState.error})"
            is UiEvent.ShowMessage -> "ShowMessage(msg: ${uiState.msg})"
            is UiEvent.ShowLoading -> "Loading"
        }
        Typography { +"\tUiState: $uiStateString" }
        Typography { +"\tisAuthorized: $isAuthorized" }
        Home()
    }

}
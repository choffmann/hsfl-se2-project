package com.hsfl.springbreak.frontend

import browser.document
import com.hsfl.springbreak.frontend.client.viewmodel.UiEventViewModel
import com.hsfl.springbreak.frontend.components.Header
import com.hsfl.springbreak.frontend.components.login.LoginDialogProvider
import com.hsfl.springbreak.frontend.context.AppContext
import com.hsfl.springbreak.frontend.context.UiStateContext
import com.hsfl.springbreak.frontend.utils.collectAsState
import dom.html.HTMLButtonElement
import mui.material.CssBaseline
import mui.material.Typography
import react.*
import react.dom.client.createRoot
import react.dom.events.MouseEventHandler

fun main() {
    createRoot(document.createElement("div").also { document.body.appendChild(it) }).render(Root.create())
}

private val Root = FC<Props> {
    val uiState = UiEventViewModel.uiState.collectAsState()
    AppContext.Provider {
        UiStateContext.Provider {
            value = uiState
            App()
        }
    }
}

private val App = FC<Props> {
    var authorized by useState(true)
    var loginDialogOpen by useState(false)

    val handleOnToggleAuthorized: MouseEventHandler<HTMLButtonElement> = {
        authorized = !authorized
    }

    val handleOnLoginButtonClicked: MouseEventHandler<HTMLButtonElement> = {
        loginDialogOpen = true
    }

    val handleOnLoginDialogClose = { _: dynamic, _: String ->
        loginDialogOpen = false
    }

    // Default Css
    CssBaseline()

    // Login Dialog
    LoginDialogProvider {
        open = loginDialogOpen
        onClose = handleOnLoginDialogClose
    }

    // Display Header
    Header {
        isAuthorized = authorized
        onLogoClicked = { println("click") }
        onLoginButtonClicked = handleOnLoginButtonClicked
        onToggleAuthorized = handleOnToggleAuthorized
        Typography { +"Hello World" }
    }

}
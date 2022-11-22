package com.hsfl.springbreak.frontend

import browser.document
import com.hsfl.springbreak.frontend.components.Header
import com.hsfl.springbreak.frontend.components.login.LoginDialog
import dom.html.HTMLButtonElement
import mui.material.CssBaseline
import mui.material.Typography
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.dom.events.MouseEventHandler
import react.useState

fun main() {
    createRoot(document.createElement("div").also { document.body.appendChild(it) }).render(App.create())
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
    LoginDialog {
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
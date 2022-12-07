package com.hsfl.springbreak.frontend

import browser.document
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.components.Header
import com.hsfl.springbreak.frontend.components.routes.Home
import com.hsfl.springbreak.frontend.components.routes.RecipeFormular
import com.hsfl.springbreak.frontend.components.snackbar.MessageSnackbar
import com.hsfl.springbreak.frontend.context.AuthorizedContext
import com.hsfl.springbreak.frontend.context.UiStateContext
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import mui.material.CssBaseline
import mui.material.Typography
import org.kodein.di.instance
import react.*
import react.dom.client.createRoot
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter
import react.router.dom.HashRouter

fun main() {
    createRoot(document.createElement("div").also { document.body.appendChild(it) }).render(Root.create())
}

private val Root = FC<Props> {
    val authorizedState: AuthState by di.instance()
    val uiState = UiEventState.uiState.collectAsState()
    val authorized = authorizedState.authorized.collectAsState()

    AuthorizedContext.Provider(value = authorized) {
        UiStateContext.Provider(value = uiState) {
            App()
        }
    }
}

private val App = FC<Props> { props ->
    // Default Css
    CssBaseline()

    MessageSnackbar()

    // Display Header
    BrowserRouter {
        Header {
            onLogoClicked = { println("click") }
            Routes {
                Route {
                    index = true
                    element = Home.create()
                }
                Route {
                    index = false
                    path = "create"
                    element = RecipeFormular.create()
                }
                Route {
                    path = "*"
                    element = Typography.create { +"404 not found" }
                }
            }
        }
    }
}
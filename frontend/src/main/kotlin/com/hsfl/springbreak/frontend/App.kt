package com.hsfl.springbreak.frontend

import browser.document
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.state.UiEventState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.RootViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.components.Header
import com.hsfl.springbreak.frontend.components.routes.*
import com.hsfl.springbreak.frontend.components.snackbar.MessageSnackbar
import com.hsfl.springbreak.frontend.context.AuthorizedContext
import com.hsfl.springbreak.frontend.context.UiStateContext
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import mui.material.CssBaseline
import org.kodein.di.instance
import react.*
import react.dom.client.createRoot
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter

fun main() {
    createRoot(document.createElement("div").also { document.body.appendChild(it) }).render(Root.create())
}

private val Root = FC<Props> {
    val authorizedState: AuthState by di.instance()
    val viewModel: RootViewModel by di.instance()
    val uiState = UiEventState.uiState.collectAsState()
    val authorized = authorizedState.authorized.collectAsState()

    useEffect(Unit) {
        viewModel.onEvent(LifecycleEvent.OnMount)
    }

    AuthorizedContext.Provider(value = authorized) {
        UiStateContext.Provider(value = uiState) {
            App()
        }
    }
}

private val App = FC<Props> {
    // Default Css
    CssBaseline()

    MessageSnackbar()

    // Display Header
    HashRouter {
        Header {
            Routes {
                // TODO: Private routes
                Route {
                    index = true
                    path = "/"
                    element = Home.create()
                }
                Route {
                    path = "/create-recipe"
                    element = ProtectedRoute.create { CreateRecipe() }
                }
                Route {
                    path = "/favorite"
                    element = ProtectedRoute.create { Favorites() }
                }
                Route {
                    path = "/categories"
                    element = RecipeCategories.create()
                }
                Route {
                    path = "/my-recipes"
                    element = ProtectedRoute.create { MyRecipes() }
                }
                Route {
                    path = "/settings"
                    element = ProtectedRoute.create { Settings() }
                }
                Route {
                    path = "/user"
                    element = ProtectedRoute.create { MyUser() }
                }
                Route {
                    path = "*"
                    element = NotFound404.create()
                }
            }
        }
    }
}
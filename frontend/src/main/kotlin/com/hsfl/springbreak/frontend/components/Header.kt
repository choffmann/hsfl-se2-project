package com.hsfl.springbreak.frontend.components

import com.hsfl.springbreak.frontend.client.presentation.controller.AuthDialogController
import com.hsfl.springbreak.frontend.client.presentation.controller.AuthDialogControllerEvent
import com.hsfl.springbreak.frontend.components.auth.AuthDialogProvider
import com.hsfl.springbreak.frontend.components.drawer.NavDrawer
import com.hsfl.springbreak.frontend.context.AuthorizedContext
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.toMuiPx
import csstype.*
import dom.html.HTMLButtonElement
import mui.icons.material.*
import mui.material.*
import mui.material.Size
import mui.material.styles.TypographyVariant
import mui.system.sx
import org.kodein.di.instance
import react.FC
import react.PropsWithChildren
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.main
import react.useContext

external interface HeaderProps : PropsWithChildren {
    var onLogoClicked: MouseEventHandler<HTMLButtonElement>?
    var onToggleAuthorized: MouseEventHandler<HTMLButtonElement>?
    //var onLoginButtonClicked: MouseEventHandler<HTMLButtonElement>?
}

val Header = FC<HeaderProps> { props ->
    val isAuthorized = useContext(AuthorizedContext)
    val authDialogController: AuthDialogController by di.instance()

    Box {
        sx { display = Display.flex }
        AppBar {
            position = AppBarPosition.fixed
            // Change zIndex, so the AppBar is in front of the drawer
            sx { zIndex = integer(1201) }
            Toolbar {
                // Logo
                IconButton {
                    size = Size.large
                    edge = IconButtonEdge.start
                    color = IconButtonColor.inherit
                    sx { marginRight = 1.toMuiPx() }
                    onClick = props.onLogoClicked
                    SoupKitchen()
                }
                // Text
                Typography {
                    variant = TypographyVariant.h6
                    component = div
                    noWrap = true
                    sx { flexGrow = number(1.0) }
                    +"Studentenküche"
                }
                // Search Icon
                IconButton {
                    size = Size.large
                    color = IconButtonColor.inherit
                    Search()
                }
                // If authorized, show login button
                if (!isAuthorized) {
                    Button {
                        color = ButtonColor.inherit
                        onClick = {
                            authDialogController.onEvent(AuthDialogControllerEvent.OpenLoginDialog)
                        }
                        +"Anmelden"
                    }
                }
            }
            LoadingBar()
        }

        NavDrawer {
            onToggleAuthorized = props.onToggleAuthorized
            onLoginButtonClicked = {
                //handleOnLoginButtonClicked()
            }
        }
        // Main component
        Box {
            component = main
            sx {
                flexGrow = number(1.0)
                padding = 1.toMuiPx()
            }
            Toolbar()
            +props.children
        }

        // Login Dialog
        AuthDialogProvider {}
    }
}


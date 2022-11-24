package com.hsfl.springbreak.frontend.components

import com.hsfl.springbreak.frontend.components.drawer.NavDrawer
import com.hsfl.springbreak.frontend.utils.inMuiPx
import csstype.*
import dom.html.HTML
import dom.html.HTMLButtonElement
import dom.html.HTMLElement
import mui.icons.material.*
import mui.material.*
import mui.material.Size
import mui.material.styles.TypographyVariant
import mui.system.SxProps
import mui.system.sx
import react.FC
import react.PropsWithChildren
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.main
import react.useContext

external interface HeaderProps : PropsWithChildren {
    var onLogoClicked: MouseEventHandler<HTMLButtonElement>?
    var isAuthorized: Boolean
    var onToggleAuthorized: MouseEventHandler<HTMLButtonElement>?
    var onLoginButtonClicked: MouseEventHandler<HTMLButtonElement>?
}

val Header = FC<HeaderProps> { props ->

    val handleOnDrawerLoginButtonClicked: MouseEventHandler<HTMLElement> = {
        props.onLoginButtonClicked
    }

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
                    sx { marginRight = 8.px }
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
                if (!props.isAuthorized) {
                    Button {
                        color = ButtonColor.inherit
                        onClick = props.onLoginButtonClicked
                        +"Anmelden"
                    }
                }
            }
            LoadingBar()
        }

        NavDrawer {
            isAuthorized = props.isAuthorized
            onToggleAuthorized = props.onToggleAuthorized
            onLoginButtonClicked = props.onLoginButtonClicked as MouseEventHandler<HTMLElement>?
        }
        // Main component
        Box {
            component = main
            sx {
                flexGrow = number(1.0)
                padding = 3.inMuiPx()
            }
            Toolbar()
            +props.children
        }
    }
}


package com.hsfl.springbreak.frontend.components

import com.hsfl.springbreak.frontend.components.drawer.NavDrawer
import com.hsfl.springbreak.frontend.utils.inMuiPx
import csstype.*
import dom.html.HTMLButtonElement
import mui.icons.material.*
import mui.material.*
import mui.material.Size
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.PropsWithChildren
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.main

interface HeaderProps : PropsWithChildren {
    var onLogoClicked: MouseEventHandler<HTMLButtonElement>?
    var isAuthorized: Boolean
}

// TODO: Get drawer width
val drawerWidth = 328.inMuiPx()

val Header = FC<HeaderProps> { props ->
    Box {
        sx { display = Display.flex }
        AppBar {
            position = AppBarPosition.fixed
            sx { zIndex = integer(1201) }
            Toolbar {
                IconButton {
                    size = Size.large
                    edge = IconButtonEdge.start
                    color = IconButtonColor.inherit
                    sx { marginRight = 8.px }
                    onClick = props.onLogoClicked
                    SoupKitchen()
                }
                Typography {
                    variant = TypographyVariant.h6
                    component = div
                    noWrap = true
                    sx { flexGrow = number(1.0) }
                    +"Studentenküche"
                }
                IconButton {
                    size = Size.large
                    color = IconButtonColor.inherit
                    Search()
                }
                if (!props.isAuthorized) {
                    Button {
                        color = ButtonColor.inherit
                        +"Anmelden"
                    }
                }
            }
        }
        NavDrawer { isAuthorized = props.isAuthorized }
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


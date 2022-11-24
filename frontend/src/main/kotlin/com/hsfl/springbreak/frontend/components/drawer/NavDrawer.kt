package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.context.AuthorizedContext
import com.hsfl.springbreak.frontend.utils.inMuiPx
import csstype.BoxSizing
import csstype.Overflow
import csstype.number
import dom.html.HTMLButtonElement
import dom.html.HTMLElement
import kotlinx.js.jso
import mui.material.*
import mui.material.styles.createStyles
import mui.system.createTheme
import mui.system.sx
import react.FC
import react.PropsWithChildren
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.style
import react.useContext

external interface NavDrawerProps : PropsWithChildren {
    var onToggleAuthorized: MouseEventHandler<HTMLButtonElement>?
    var onLoginButtonClicked: MouseEventHandler<HTMLElement>?
}

val drawerWidth = 328.inMuiPx()

val NavDrawer = FC<NavDrawerProps> { props ->
    val isAuthorized = useContext(AuthorizedContext)
    Drawer {
        sx {
            flexShrink = number(0.0)
            width = drawerWidth
            MuiPaper.root {
                width = drawerWidth
                boxSizing = BoxSizing.borderBox
            }
        }
        variant = DrawerVariant.permanent
        anchor = DrawerAnchor.left
        Toolbar()
        Box {
            sx { overflow = Overflow.hidden }
        }
        if (isAuthorized) {
            AuthorizedList()
        } else {
            UnauthorizedList()
        }
        DebugListProvider()
    }
}
package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.components.drawerWidth
import com.hsfl.springbreak.frontend.utils.color
import csstype.FontWeight
import csstype.number
import dom.html.HTMLButtonElement
import mui.icons.material.*
import mui.material.*
import mui.material.List
import mui.system.sx
import react.FC
import react.PropsWithChildren
import react.dom.events.MouseEventHandler
import react.useState

external interface NavDrawerProps: PropsWithChildren {
    var isAuthorized: Boolean
    var onToggleAuthorized: MouseEventHandler<HTMLButtonElement>?
}

val NavDrawer = FC<NavDrawerProps> { props ->

    Drawer {
        sx {
            flexShrink = number(0.0)
            width = drawerWidth
        }
        variant = DrawerVariant.permanent
        anchor = DrawerAnchor.left
        Toolbar()
        if (props.isAuthorized) {
            AuthorizedList()
        } else {
            UnauthorizedList()
        }
        DebugList {
            isAuthorized = props.isAuthorized
            onToggleAuthorized = props.onToggleAuthorized
        }
    }
}
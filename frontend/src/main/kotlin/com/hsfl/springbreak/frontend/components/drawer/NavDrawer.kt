package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.components.drawerWidth
import com.hsfl.springbreak.frontend.utils.color
import csstype.FontWeight
import csstype.number
import mui.icons.material.*
import mui.material.*
import mui.material.List
import mui.system.sx
import react.FC
import react.PropsWithChildren
import react.useState



val NavDrawer = FC<PropsWithChildren> {
    val isAuthorized by useState(true)
    Drawer {
        sx {
            flexShrink = number(0.0)
            width = drawerWidth
        }
        variant = DrawerVariant.permanent
        anchor = DrawerAnchor.left
        Toolbar()
        if (isAuthorized) {
            AuthorizedList()
        } else {
            UnauthorizedList()
        }
    }
}
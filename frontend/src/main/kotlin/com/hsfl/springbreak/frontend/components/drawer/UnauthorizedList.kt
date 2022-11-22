package com.hsfl.springbreak.frontend.components.drawer

import mui.icons.material.Category
import mui.icons.material.Login
import mui.material.*
import react.FC
import react.Props

val UnauthorizedList = FC<Props> {
    List {
        ListItemButton {
            ListItemIcon {
                Login()
            }
            ListItemText {
                Typography { +"Anmelden" }
            }
        }
        Divider()
        ListItemButton {
            ListItemIcon {
                Category()
            }
            ListItemText {
                Typography { +"Kategorien" }
            }
        }
    }
}
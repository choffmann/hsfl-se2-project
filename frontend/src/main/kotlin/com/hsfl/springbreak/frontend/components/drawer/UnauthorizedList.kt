package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.NavEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.NavViewModel
import mui.icons.material.Category
import mui.icons.material.Login
import mui.material.*
import react.FC
import react.Props

val UnauthorizedList = FC<Props> { props ->
    List {
        ListItemButton {
            onClick = { NavViewModel.onEvent(NavEvent.OnOpenLoginDialog) }
            ListItemIcon {
                Login()
            }
            ListItemText {
                Typography { +"Anmelden" }
            }
        }
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
package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.client.presentation.controller.AuthDialogController
import com.hsfl.springbreak.frontend.client.presentation.controller.AuthDialogControllerEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.NavEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.NavViewModel
import com.hsfl.springbreak.frontend.di.di
import mui.icons.material.Category
import mui.icons.material.Login
import mui.material.*
import org.kodein.di.instance
import react.FC
import react.Props

val UnauthorizedList = FC<Props> { props ->
    val authDialogController: AuthDialogController by di.instance()
    List {
        ListItemButton {
            onClick = { authDialogController.onEvent(AuthDialogControllerEvent.OpenLoginDialog) }
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
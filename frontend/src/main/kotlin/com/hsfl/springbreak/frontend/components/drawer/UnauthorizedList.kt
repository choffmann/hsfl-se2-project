package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.AuthDialogViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.AuthDialogEvent
import com.hsfl.springbreak.frontend.di.di
import mui.icons.material.Category
import mui.icons.material.Home
import mui.icons.material.Login
import mui.material.*
import org.kodein.di.instance
import react.FC
import react.Props
import react.router.useNavigate

val UnauthorizedList = FC<Props> { props ->
    val authDialogViewModel: AuthDialogViewModel by di.instance()
    val navigator = useNavigate()
    List {
        ListItemButton {
            onClick = { authDialogViewModel.onEvent(AuthDialogEvent.OpenLoginDialog) }
            ListItemIcon {
                Login()
            }
            ListItemText {
                Typography { +"Anmelden" }
            }
        }
        ListItemButton {
            onClick = {navigator("/")}
            ListItemIcon {
                Home()
            }
            ListItemText {
                Typography { +"Übersicht" }
            }
        }
        ListItemButton {
            onClick = {navigator("/categories")}
            ListItemIcon {
                Category()
            }
            ListItemText {
                Typography { +"Kategorien" }
            }
        }
    }
}
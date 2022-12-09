package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.AuthDialogViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.AuthDialogEvent
import com.hsfl.springbreak.frontend.di.di
import csstype.Color
import csstype.None
import emotion.react.css
import mui.icons.material.Category
import mui.icons.material.Login
import mui.material.*
import org.kodein.di.instance
import react.FC
import react.Props
import react.router.dom.NavLink

val UnauthorizedList = FC<Props> { props ->
    val authDialogViewModel: AuthDialogViewModel by di.instance()
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
        NavLink {
            to ="/categories"
            // ignore style
            css {
                textDecoration = None.none
                color = Color.currentcolor
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
}
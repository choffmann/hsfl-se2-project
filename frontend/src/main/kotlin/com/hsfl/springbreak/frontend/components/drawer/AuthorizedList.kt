package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.*
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import com.hsfl.springbreak.frontend.utils.color
import csstype.FontWeight
import mui.icons.material.*
import mui.material.*
import mui.material.List
import mui.system.sx
import org.kodein.di.instance
import react.FC
import react.Props
import react.router.useNavigate

data class NavBarItem(
    val name: String,
    val icon: SvgIconComponent,
    val linkTo: String
)

val AuthorizedList = FC<Props> {
    val listItems = listOf(
        NavBarItem("Übersicht", Home, "/"),
        NavBarItem("Favoriten", Favorite, "/favorite"),
        NavBarItem("Kategorien", Category, "/categories"),
        NavBarItem("Rezept erstellen", Add, "/create-recipe"),
        NavBarItem("Meine Rezepte", Book, "/my-recipes"),
        NavBarItem("Einstellungen", Settings, "/settings"),
    )
    val userState: UserState by di.instance()
    val user = userState.userState.collectAsState()
    val navigator = useNavigate()

    List {
        ListItemButton {
            onClick = {
                navigator("/user")
            }
            ListItemAvatar {
                Avatar {
                    //src = user.image
                    user.image?.let {
                        src = it
                    } ?: +"${user.firstName[0]}${user.lastName[0]}"
                }
            }
            ListItemText {
                Typography {
                    sx { fontWeight = FontWeight.bold }
                    +"${user.firstName} ${user.lastName}"
                }
                Typography {
                    color = "text.secondary"
                    +user.email
                }
            }
        }
        listItems.forEach { item ->
            ListItemButton {
                onClick = {
                    navigator(item.linkTo)
                }
                ListItemIcon {
                    item.icon()
                }
                ListItemText {
                    Typography { +item.name }
                }
            }

        }
        ListItemButton {
            onClick = {
                NavViewModel.onEvent(NavEvent.OnLogout)
                navigator("/")
            }
            ListItemIcon {
                Logout()
            }
            ListItemText {
                Typography { +"Abmelden" }
            }
        }
    }
}
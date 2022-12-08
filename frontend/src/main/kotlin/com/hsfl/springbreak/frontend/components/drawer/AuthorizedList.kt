package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.NavEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.NavViewModel
import com.hsfl.springbreak.frontend.utils.color
import csstype.FontWeight
import emotion.react.css
import csstype.Color
import csstype.None.none
import mui.icons.material.*
import mui.material.*
import mui.material.List
import mui.system.sx
import react.FC
import react.Props
import react.router.dom.NavLink

data class NavBarItem(
    val name: String,
    val icon: SvgIconComponent,
    val linkTo: String
)

val AuthorizedList = FC<Props> {
    val listItems = listOf(
        NavBarItem("Favoriten", Favorite, "/favorite"),
        NavBarItem("Kategorien", Category, "/categories"),
        NavBarItem("Rezept erstellen", Add, "/create-recipe"),
        NavBarItem("Meine Rezepte", Book, "/my-recipes"),
        NavBarItem("Einstellungen", Settings, "/settings"),
    )
    List {
        NavLink {
            to = "/user"
            // ignore style
            css {
                textDecoration = none
                color = Color.currentcolor
            }
            ListItemButton {
                ListItemAvatar {
                    Avatar { +"RH" }
                }
                ListItemText {
                    Typography {
                        sx { fontWeight = FontWeight.bold }
                        +"Ryan Hughes"
                    }
                    Typography {
                        color = "text.secondary"
                        +"ryan.hughes@mail-address.com"
                    }
                }
            }
        }
        listItems.forEach { item ->
            NavLink {
                to = item.linkTo
                // ignore style
                css {
                    textDecoration = none
                    color = Color.currentcolor
                }
                ListItemButton {
                    ListItemIcon {
                        item.icon()
                    }
                    ListItemText {
                        Typography { +item.name }
                    }
                }
            }
        }
        ListItemButton {
            onClick = {
                NavViewModel.onEvent(NavEvent.OnLogout)
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
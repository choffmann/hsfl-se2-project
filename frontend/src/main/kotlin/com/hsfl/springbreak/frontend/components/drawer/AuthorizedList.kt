package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.utils.color
import csstype.FontWeight
import mui.icons.material.Add
import mui.icons.material.Book
import mui.icons.material.Category
import mui.icons.material.Favorite
import mui.material.*
import mui.system.sx
import react.FC
import react.Props

val AuthorizedList = FC<Props> {
    List {
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
        Divider()
        ListItemButton {
            ListItemIcon {
                Favorite()
            }
            ListItemText {
                Typography { +"Favoriten" }
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
        ListItemButton {
            ListItemIcon {
                Book()
            }
            ListItemText {
                Typography { +"Meine Rezepte" }
            }
        }
        ListItemButton {
            ListItemIcon {
                Add()
            }
            ListItemText {
                Typography { +"Rezept erstellen" }
            }
        }
    }
}
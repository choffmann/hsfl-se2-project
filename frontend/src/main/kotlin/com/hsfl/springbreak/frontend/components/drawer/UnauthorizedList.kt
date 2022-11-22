package com.hsfl.springbreak.frontend.components.drawer

import dom.html.HTMLElement
import mui.icons.material.Category
import mui.icons.material.Login
import mui.material.*
import react.FC
import react.Props
import react.dom.events.MouseEventHandler

external interface UnauthorizedListProps: Props {
    var onLoginButtonClicked: MouseEventHandler<HTMLElement>?
}

val UnauthorizedList = FC<UnauthorizedListProps> { props ->
    List {
        ListItemButton {
            onClick = props.onLoginButtonClicked
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
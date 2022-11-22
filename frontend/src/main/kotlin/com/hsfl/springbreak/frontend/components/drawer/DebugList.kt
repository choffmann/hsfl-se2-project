package com.hsfl.springbreak.frontend.components.drawer

import dom.html.HTMLButtonElement
import react.FC
import react.Props
import mui.material.*
import react.dom.events.MouseEventHandler

external interface DebugListProps: Props {
    var isAuthorized: Boolean
    var onToggleAuthorized: MouseEventHandler<HTMLButtonElement>?
}


val DebugList = FC<DebugListProps> { props ->
    List {
        Divider()
        ListItem {
            ListItemIcon {
                Switch {
                    defaultChecked = props.isAuthorized
                    onClick = props.onToggleAuthorized
                }
            }
            ListItemText {+"Authorized"}
        }
    }
}
package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.client.viewmodel.DebugEvent
import com.hsfl.springbreak.frontend.client.viewmodel.DebugViewModel
import com.hsfl.springbreak.frontend.context.AuthorizedContext
import com.hsfl.springbreak.frontend.utils.collectAsState
import mui.icons.material.Error
import mui.material.*
import react.*


val DebugListProvider = FC<Props> {
    val loading = DebugViewModel.showLoading.collectAsState()
    val authorized = DebugViewModel.authState.collectAsState()
    DebugList {
        showLoading = loading
        isAuthorized = authorized
    }
}

external interface DebugListProps : Props {
    var showLoading: Boolean
    var isAuthorized: Boolean
}

private val DebugList = FC<DebugListProps> { props ->

    List {
        Divider()
        ListSubheader { +"Debug List" }
        ListItem {
            ListItemIcon {
                Switch {
                    checked = props.isAuthorized
                    onClick = { _ ->
                        DebugViewModel.onEvent(DebugEvent.OnSwitchAuthorized)
                    }
                }
            }
            ListItemText { +"Authorized" }
        }
        ListItem {
            ListItemIcon {
                Switch {
                    checked = props.showLoading
                    onClick = { _ ->
                        DebugViewModel.onEvent(DebugEvent.OnSwitchShowLoading)
                    }
                }
            }
            ListItemText { +"Show Loading" }
        }
        ListItemButton {
            onClick = {
                DebugViewModel.onEvent(DebugEvent.OnThrowError("Throw an error on the debug list"))
            }
            ListItemIcon {
                Error()
            }
            ListItemText { +"Throw Error" }
        }
    }
}
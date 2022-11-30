package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.client.viewmodel.DebugEvent
import com.hsfl.springbreak.frontend.client.viewmodel.DebugViewModel
import com.hsfl.springbreak.frontend.client.viewmodel.UiEvent
import com.hsfl.springbreak.frontend.context.AuthorizedContext
import com.hsfl.springbreak.frontend.context.UiStateContext
import com.hsfl.springbreak.frontend.utils.collectAsState
import mui.icons.material.*
import mui.material.*
import mui.material.List
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
    var open by useState(false)
    val uiState = useContext(UiStateContext)
    val isAuthorized = useContext(AuthorizedContext)

    List {
        Divider()
        ListItemButton {
            onClick = {
                open = !open
            }
            ListItemIcon { BugReport() }
            ListItemText { primary = ReactNode("Debug List") }
            if (open) ExpandLess() else ExpandMore()
        }
        Collapse {
            `in` = open
            timeout = "auto".asDynamic()
            List {
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
                        DebugViewModel.onEvent(DebugEvent.OnThrowError("Throw an error from the debug list"))
                    }
                    ListItemIcon {
                        Error()
                    }
                    ListItemText { +"Throw Error" }
                }
                ListItemButton {
                    onClick = {
                        DebugViewModel.onEvent(DebugEvent.SendMessage("A friendly message to the user \uD83C\uDF89"))
                    }
                    ListItemIcon {
                        Message()
                    }
                    ListItemText { +"Send info message" }
                }

                // Show information
                ListSubheader { +"Debug View" }
                ListItem {
                    val uiStateString = when (uiState) {
                        is UiEvent.Idle -> "Idle"
                        is UiEvent.ShowError -> "ShowError(msg: ${uiState.error})"
                        is UiEvent.ShowMessage -> "ShowMessage(msg: ${uiState.msg})"
                        is UiEvent.ShowLoading -> "Loading"
                    }
                    ListItemText { +"UiState: $uiStateString" }
                }
                ListItem {
                    ListItemText { +"isAuthorized: $isAuthorized" }
                }
            }
        }
    }
}
package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.DebugEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.DebugViewModel
import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.context.AuthorizedContext
import com.hsfl.springbreak.frontend.context.UiStateContext
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import mui.icons.material.*
import mui.material.*
import mui.material.List
import org.kodein.di.instance
import react.*
import react.router.useNavigate
import web.location.location


val DebugListProvider = FC<Props> {
    val debugViewModel: DebugViewModel by di.instance()
    val loading = debugViewModel.showLoading.collectAsState()
    val authorized = debugViewModel.authState.collectAsState()
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
    val debugViewModel: DebugViewModel by di.instance()
    var open by useState(false)
    val uiState = useContext(UiStateContext)
    val isAuthorized = useContext(AuthorizedContext)
    val routes = useNavigate()

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
                                debugViewModel.onEvent(DebugEvent.OnSwitchAuthorized)
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
                                debugViewModel.onEvent(DebugEvent.OnSwitchShowLoading)
                            }
                        }
                    }
                    ListItemText { +"Show Loading" }
                }
                ListItemButton {
                    onClick = {
                        debugViewModel.onEvent(DebugEvent.OnThrowError("Throw an error from the debug list"))
                    }
                    ListItemIcon {
                        Error()
                    }
                    ListItemText { +"Throw Error" }
                }
                ListItemButton {
                    onClick = {
                        debugViewModel.onEvent(DebugEvent.SendMessage("A friendly message to the user \uD83C\uDF89"))
                    }
                    ListItemIcon {
                        Message()
                    }
                    ListItemText { +"Send info message" }
                }
                ListItemButton {
                    onClick = {
                        location.reload()
                    }
                    ListItemIcon {
                        Refresh()
                    }
                    ListItemText { +"Refresh Page" }
                }
                ListItemButton {
                    onClick = {
                        routes("/blabla")
                    }
                    ListItemIcon {
                        Directions()
                    }
                    ListItemText { +"Redirect programmatically" }
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
package com.hsfl.springbreak.frontend.components.drawer

import com.hsfl.springbreak.frontend.client.viewmodel.DebugEvent
import com.hsfl.springbreak.frontend.client.viewmodel.DebugListData
import com.hsfl.springbreak.frontend.client.viewmodel.DebugViewModel
import com.hsfl.springbreak.frontend.client.viewmodel.UiEvent
import com.hsfl.springbreak.frontend.context.UiStateContext
import com.hsfl.springbreak.frontend.utils.collectAsState
import dom.html.HTMLButtonElement
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import mui.icons.material.ThumbUp
import mui.material.*
import react.*
import react.dom.events.MouseEventHandler


val DebugContext = createContext(DebugListData())
val DebugListProvider = FC<Props> {
    val auth = DebugViewModel.authState.collectAsState()


    DebugContext.Provider {
        value = auth
        DebugListChild()
    }
}

val DebugListChild = FC<Props> {
    val auth = useContext(DebugContext)
    val uiEvent = useContext(UiStateContext)

    List {
        Divider()
        ListItem {
            ListItemIcon {
                Switch {
                    defaultChecked = auth.auth
                    onClick = { _ ->
                        DebugViewModel.onEvent(DebugEvent.OnSwitchAuthorized)
                    }
                }
            }
            ListItemText { +"Authorized" }
        }
        if (auth.auth) {
            ListItem {
                ListItemIcon {
                    ThumbUp()
                }
                ListItemText { +"Is Authorized" }
            }
        }
        ListItem {
            ListItemIcon {
                Switch {
                    defaultChecked = uiEvent is UiEvent.ShowLoading
                    onClick = { _ ->
                        DebugViewModel.onEvent(DebugEvent.OnSwitchShowLoading)
                    }
                }
            }
            ListItemText { +"Show Loading" }
        }
    }
}
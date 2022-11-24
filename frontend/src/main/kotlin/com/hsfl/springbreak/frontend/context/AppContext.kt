package com.hsfl.springbreak.frontend.context

import com.hsfl.springbreak.frontend.client.viewmodel.RootData
import com.hsfl.springbreak.frontend.client.viewmodel.UiEvent
import react.createContext

val AppContext = createContext(RootData())
val UiStateContext = createContext<UiEvent>(UiEvent.ShowLoading)
package com.hsfl.springbreak.frontend.context

import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import react.createContext

val AuthorizedContext = createContext(false)
val UiStateContext = createContext<UiEvent>(UiEvent.Idle)
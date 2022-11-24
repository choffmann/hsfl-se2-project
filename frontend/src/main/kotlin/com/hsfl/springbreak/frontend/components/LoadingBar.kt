package com.hsfl.springbreak.frontend.components

import com.hsfl.springbreak.frontend.client.viewmodel.UiEvent
import com.hsfl.springbreak.frontend.context.UiStateContext
import mui.material.LinearProgress
import react.FC
import react.Props
import react.useContext

val LoadingBar = FC<Props> {
    when (useContext(UiStateContext)) {
        is UiEvent.ShowLoading -> {
            LinearProgress()
        }
        else -> {}
    }
}
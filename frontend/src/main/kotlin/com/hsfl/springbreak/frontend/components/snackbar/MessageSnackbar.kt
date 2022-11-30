package com.hsfl.springbreak.frontend.components.snackbar

import com.hsfl.springbreak.frontend.client.viewmodel.ErrorViewModel
import com.hsfl.springbreak.frontend.client.viewmodel.MessageViewModel
import com.hsfl.springbreak.frontend.client.viewmodel.SnackbarEvent
import com.hsfl.springbreak.frontend.utils.collectAsState
import react.FC
import react.Props

val MessageSnackbar = FC <Props> {
    val show = MessageViewModel.openSnackbar.collectAsState()
    val message = MessageViewModel.message.collectAsState()

    SnackbarChild {
        open = show
        msg = message
        onClose = { event, reason ->
            MessageViewModel.onEvent(SnackbarEvent.Close(event, reason))
        }
    }
}
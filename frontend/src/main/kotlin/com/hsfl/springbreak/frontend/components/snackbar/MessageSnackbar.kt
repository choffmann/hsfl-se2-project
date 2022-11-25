package com.hsfl.springbreak.frontend.components.snackbar

import com.hsfl.springbreak.frontend.client.viewmodel.MessageViewModel
import com.hsfl.springbreak.frontend.client.viewmodel.SnackbarEvent
import com.hsfl.springbreak.frontend.utils.collectAsState
import mui.material.*
import react.FC
import react.Props
import react.ReactNode

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

external interface SnackbarProps: Props {
    var open: Boolean
    var msg: String
    var onClose: (event: dynamic, reason: SnackbarCloseReason) -> Unit
}

val SnackbarChild = FC<SnackbarProps> { props ->
    Snackbar {
        open = props.open
        onClose = props.onClose
        anchorOrigin = object : SnackbarOrigin {
            override var vertical = SnackbarOriginVertical.bottom
            override var horizontal = SnackbarOriginHorizontal.center
        }
        message = ReactNode(props.msg)
    }
}
package com.hsfl.springbreak.frontend.components

import com.hsfl.springbreak.frontend.client.viewmodel.ErrorEvent
import com.hsfl.springbreak.frontend.client.viewmodel.ErrorViewModel
import com.hsfl.springbreak.frontend.client.viewmodel.UiEvent
import com.hsfl.springbreak.frontend.context.UiStateContext
import com.hsfl.springbreak.frontend.utils.collectAsState
import mui.material.*
import react.*


val ErrorSnackbar = FC<Props> {
    val show = ErrorViewModel.openErrorSnackbar.collectAsState()
    val errorMessage = ErrorViewModel.errorMessage.collectAsState()

    ErrorSnackbarChild {
        open = show
        msg = errorMessage
        onClose = { event, reason ->
            ErrorViewModel.onEvent(ErrorEvent.CloseError(event, reason))
        }
    }
}

external interface ErrorSnackbarProps : Props {
    var open: Boolean
    var msg: String
    var onClose: (event: dynamic, reason: SnackbarCloseReason) -> Unit
}


val ErrorSnackbarChild = FC<ErrorSnackbarProps> { props ->
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
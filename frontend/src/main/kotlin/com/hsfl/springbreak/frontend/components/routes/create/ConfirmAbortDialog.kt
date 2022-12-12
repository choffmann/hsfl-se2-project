package com.hsfl.springbreak.frontend.components.routes.create

import mui.material.*
import mui.material.styles.TypographyVariant
import react.FC
import react.Props
import react.router.useNavigate

external interface ConfirmAbortDialogProps : Props {
    var open: Boolean
    var onClose: () -> Unit
    var onConfirm: () -> Unit
    var navigateTo: String
}

val ConfirmAbortDialog = FC<ConfirmAbortDialogProps> { props ->
    val navigator = useNavigate()
    Dialog {
        open = props.open
        onClose = { _, _ ->
            props.onClose()
        }
        DialogTitle { +"Vorgang abbrechen?" }
        DialogContent {
            Typography {
                variant = TypographyVariant.subtitle1
                +"Wenn du den Vorgang abbrichst, wird das aktuelle Rezept nicht gespeichert."
            }
        }
        DialogActions {
            Button {
                onClick = {
                    props.onClose()
                }
                +"Abbrechen"
            }
            Button {
                onClick = {
                    props.onConfirm()
                    navigator(props.navigateTo)
                }
                +"Verlassen"
            }

        }
    }
}
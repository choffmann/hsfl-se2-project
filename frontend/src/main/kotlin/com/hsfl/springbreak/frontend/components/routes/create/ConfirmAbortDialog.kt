package com.hsfl.springbreak.frontend.components.routes.create

import csstype.Color
import csstype.None
import emotion.react.css
import mui.material.*
import mui.material.styles.TypographyVariant
import react.FC
import react.Props
import react.router.dom.NavLink

external interface ConfirmAbortDialogProps : Props {
    var open: Boolean
    var onClose: () -> Unit
    var onConfirm: () -> Unit
}

val ConfirmAbortDialog = FC<ConfirmAbortDialogProps> { props ->
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
            NavLink {
                to = "/"
                // ignore style
                css {
                    textDecoration = None.none
                    color = Color.currentcolor
                }
                Button {
                    onClick = {
                        props.onConfirm()
                    }
                    +"Verlassen"
                }
            }
        }
    }
}
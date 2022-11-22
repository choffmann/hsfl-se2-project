package com.hsfl.springbreak.frontend.components.login

import csstype.integer
import csstype.number
import csstype.px
import dom.html.HTMLButtonElement
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props
import react.ReactNode
import react.create
import react.dom.events.MouseEventHandler
import react.dom.html.InputType
import react.dom.onChange

external interface LoginDialogProps : Props {
    var open: Boolean
    var onClose: (event: dynamic, reason: String) -> Unit
}

val LoginDialog = FC<LoginDialogProps> { props ->

    val handleOnCancelClicked: MouseEventHandler<HTMLButtonElement> = {
        props.onClose(it, "onCancelButton")
    }

    Dialog {
        open = props.open
        onClose = props.onClose
        fullWidth = true

        DialogTitle { +"Anmelden" }
        DialogContent {
            Box {
                sx { flexGrow = number(1.0) }
                Grid {
                    container = true
                    direction = responsive(GridDirection.column)
                    spacing = responsive(2)
                    sx { paddingTop = 8.px }

                    // Email TextField
                    Grid {
                        item = true
                        TextField {
                            type = InputType.email
                            label = Typography.create { +"Email" }
                            fullWidth = true
                            // TODO: onChange = ...
                        }
                    }

                    // Password TextField
                    Grid {
                        item = true
                        TextField {
                            type = InputType.password
                            label = Typography.create { +"Password" }
                            fullWidth = true
                            // TODO: onChange = ...
                        }
                    }

                    // Register Button
                    Grid {
                        item = true
                        Button {
                            +"Oder registriere dich hier"
                        }
                    }
                }
            }
        }
        DialogActions {
            Button {
                variant = ButtonVariant.outlined
                color = ButtonColor.secondary
                onClick = handleOnCancelClicked
                +"Abbrechen"
            }
            Button {
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                +"Anmelden"
            }
        }
    }
}
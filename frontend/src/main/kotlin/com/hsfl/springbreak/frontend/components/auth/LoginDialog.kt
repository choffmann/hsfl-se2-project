package com.hsfl.springbreak.frontend.components.auth

import com.hsfl.springbreak.frontend.components.LoadingBar
import csstype.number
import csstype.px
import dom.html.HTMLButtonElement
import dom.html.HTMLInputElement
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.events.MouseEventHandler
import react.dom.html.InputType
import react.dom.onChange

external interface LoginDialogProps : Props {
    var open: Boolean
    var onClose: (event: dynamic, reason: String) -> Unit
    var onRegister: () -> Unit
    var onLogin: () -> Unit
    var onEmailTextChanged: (String) -> Unit
    var onPasswordTextChanged: (String) -> Unit
}

val LoginDialog = FC<LoginDialogProps> { props ->
    val handleOnCancelClicked: MouseEventHandler<HTMLButtonElement> = {
        props.onClose(it, "onCancelButton")
    }

    Dialog {
        open = props.open
        onClose = props.onClose
        fullWidth = true
        LoadingBar()
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
                            onChange = { event ->
                                val target = event.target as HTMLInputElement
                                props.onEmailTextChanged(target.value)
                            }
                        }
                    }

                    // Password TextField
                    Grid {
                        item = true
                        TextField {
                            type = InputType.password
                            label = Typography.create { +"Password" }
                            fullWidth = true
                            onChange = { event ->
                                val target = event.target as HTMLInputElement
                                props.onPasswordTextChanged(target.value)
                            }
                        }
                    }

                    // Register Button
                    Grid {
                        item = true
                        Button {
                            onClick = { _ ->
                                props.onRegister()
                            }
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
                onClick = {
                    props.onLogin()
                }
                +"Anmelden"
            }
        }
    }
}
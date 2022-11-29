package com.hsfl.springbreak.frontend.components.auth

import com.hsfl.springbreak.frontend.components.LoadingBar
import csstype.*
import mui.icons.material.CloseOutlined
import mui.icons.material.Upload
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input

external interface RegisterDialogProps : Props {
    var open: Boolean
    var onClose: (event: dynamic, reason: String) -> Unit
    var onRegister: () -> Unit
    var onLogin: () -> Unit
    var onEmailTextChanged: (String) -> Unit
    var onPasswordTextChanged: (String) -> Unit
}

val RegisterDialog = FC<RegisterDialogProps> { props ->
    Dialog {
        open = props.open
        onClose = props.onClose
        fullScreen = true

        DialogTitle { +"Hello World!" }
        AppBar {
            Toolbar {
                IconButton {
                    edge = IconButtonEdge.start
                    color = IconButtonColor.inherit
                    onClick = { props.onClose(it, "OnAppBarClose") }
                    CloseOutlined()
                }
                Typography {
                    sx {
                        marginLeft = 16.px
                        flex = number(1.0)
                    }
                    variant = TypographyVariant.h6
                    component = div
                    +"Erstelle dein Account"
                }
            }
            LoadingBar()
        }

        Box {
            component = form
            sx {
                margin = 8.px
                padding = 8.px
                justifyContent = JustifyContent.center
            }
            Stack {
                spacing = responsive(2)
                direction = responsive(StackDirection.column)

                Stack {
                    spacing = responsive(2)
                    direction = responsive(StackDirection.row)
                    TextField {
                        fullWidth = true
                        label = Typography.create { +"Vorname" }
                    }
                    TextField {
                        fullWidth = true
                        label = Typography.create { +"Nachname" }
                    }
                }
                Stack {
                    spacing = responsive(2)
                    direction = responsive(StackDirection.column)
                    TextField {
                        fullWidth = true
                        type = InputType.email
                        label = Typography.create { +"Email" }
                    }
                    TextField {
                        fullWidth = true
                        type = InputType.password
                        label = Typography.create { +"Password" }
                    }

                    Button {
                        variant = ButtonVariant.outlined
                        startIcon = Icon.create { Upload() }
                        +"Profilbild"
                        onClick = {
                            input {
                                hidden = true
                                accept = "image/*"
                                multiple = false
                                type = InputType.file
                            }
                        }
                    }

                }
            }
        }

    }
}
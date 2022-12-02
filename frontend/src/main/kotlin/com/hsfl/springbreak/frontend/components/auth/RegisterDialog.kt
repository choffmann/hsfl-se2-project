package com.hsfl.springbreak.frontend.components.auth

import csstype.*
import mui.icons.material.Person
import mui.icons.material.Upload
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML.form

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
        fullScreen = false

        DialogTitle { +"Erstelle dein Account" }
        DialogContent {
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
                    Box {
                        sx {
                            display = Display.flex
                            justifyContent = JustifyContent.spaceEvenly
                            alignItems = AlignItems.center
                        }
                        Badge {
                            overlap = BadgeOverlap.circular
                            anchorOrigin = object : BadgeOrigin {
                                override var horizontal = BadgeOriginHorizontal.right
                                override var vertical = BadgeOriginVertical.bottom

                            }
                            badgeContent = Tooltip.create {
                                title = Typography.create { +"Profilbild hochladen" }
                                IconButton {
                                    // TODO: Overwrite IconButton style class
                                    sx { backgroundColor = Color("white") }
                                    Upload()
                                }
                            }
                            Avatar {
                                sx {
                                    width = 100.px
                                    height = 100.px
                                }
                                Person {
                                    sx {
                                        width = 80.px
                                        height = 80.px
                                    }
                                }
                            }
                        }
                    }

                    TextField {
                        fullWidth = true
                        label = Typography.create { +"Vorname" }
                    }
                    TextField {
                        fullWidth = true
                        label = Typography.create { +"Nachname" }
                    }
                    Stack {
                        spacing = responsive(2)
                        direction = responsive(StackDirection.column)
                        TextField {
                            fullWidth = true
                            type = InputType.email
                            label = Typography.create { +"Email" }
                        }
                        Stack {
                            spacing = responsive(2)
                            direction = responsive(StackDirection.row)

                            TextField {
                                fullWidth = true
                                type = InputType.password
                                label = Typography.create { +"Passwort" }
                            }

                            TextField {
                                fullWidth = true
                                type = InputType.password
                                label = Typography.create { +"Passwort bestätigen" }
                            }
                        }
                    }
                }
            }
        }
        DialogActions {
            Button {
                variant = ButtonVariant.outlined
                color = ButtonColor.secondary
                onClick = {
                    props.onClose(it, "onCancelButton")
                }
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
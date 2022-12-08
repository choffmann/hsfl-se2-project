package com.hsfl.springbreak.frontend.components.auth

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.auth.RegisterPasswordTextState
import com.hsfl.springbreak.frontend.components.avatar.UploadAvatar
import csstype.*
import dom.html.HTMLInputElement
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML.form
import react.dom.onChange
import web.file.File

external interface RegisterDialogProps : Props {
    var open: Boolean
    var onClose: (event: dynamic, reason: String) -> Unit
    var onRegister: () -> Unit
    var onFirstNameText: (String) -> Unit
    var onLastNameNameText: (String) -> Unit
    var onEmailText: (String) -> Unit
    var onPasswordText: (String) -> Unit
    var onConfirmedPasswordText: (String) -> Unit
    var onProfileImageChanged: (File) -> Unit
    var confirmedPasswordTextState: RegisterPasswordTextState
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

                    UploadAvatar {
                        size = 100.px
                        onProfileImageChanged = props.onProfileImageChanged
                    }

                    TextField {
                        fullWidth = true
                        label = Typography.create { +"Vorname" }
                        onChange = {
                            val target = it.target as HTMLInputElement
                            props.onFirstNameText(target.value)
                        }
                    }
                    TextField {
                        fullWidth = true
                        label = Typography.create { +"Nachname" }
                        onChange = {
                            val target = it.target as HTMLInputElement
                            props.onLastNameNameText(target.value)
                        }
                    }
                    Stack {
                        spacing = responsive(2)
                        direction = responsive(StackDirection.column)
                        TextField {
                            fullWidth = true
                            type = InputType.email
                            label = Typography.create { +"Email" }
                            onChange = {
                                val target = it.target as HTMLInputElement
                                props.onEmailText(target.value)
                            }
                        }
                        Stack {
                            spacing = responsive(2)
                            direction = responsive(StackDirection.row)

                            TextField {
                                fullWidth = true
                                type = InputType.password
                                label = Typography.create { +"Passwort" }
                                onChange = {
                                    val target = it.target as HTMLInputElement
                                    props.onPasswordText(target.value)
                                }
                            }

                            TextField {
                                fullWidth = true
                                type = InputType.password
                                label = Typography.create { +"Passwort bestätigen" }
                                onChange = {
                                    val target = it.target as HTMLInputElement
                                    props.onConfirmedPasswordText(target.value)
                                }
                                //value = props.confirmedPasswordTextError.toString()
                                error = props.confirmedPasswordTextState.error
                                helperText = Typography.create { +props.confirmedPasswordTextState.message }
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
                    props.onRegister()
                }
                +"Registrieren"
            }
        }
    }
}
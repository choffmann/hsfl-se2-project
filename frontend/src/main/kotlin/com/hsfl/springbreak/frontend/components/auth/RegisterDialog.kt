package com.hsfl.springbreak.frontend.components.auth

import com.hsfl.springbreak.frontend.utils.component
import csstype.*
import dom.html.HTMLInputElement
import kotlinx.js.get
import mui.icons.material.Person
import mui.icons.material.Upload
import mui.material.*
import mui.system.responsive
import mui.system.sx
import org.w3c.dom.url.URL
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
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
}

val RegisterDialog = FC<RegisterDialogProps> { props ->
    var profileImage by useState<String>()
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
                                    sx {
                                        backgroundColor = Color("white")
                                        boxShadow = BoxShadow(
                                            offsetX = 3.px,
                                            offsetY = 3.px,
                                            blurRadius = 3.px,
                                            color = Color("lightgrey")
                                        )
                                        hover {
                                            backgroundColor = Color("white")
                                        }

                                    }
                                    component = label
                                    ReactHTML.input {
                                        hidden = true
                                        accept = "image/*"
                                        type = InputType.file
                                        onChange = {
                                            if (it.target.files?.length != 0) {
                                                it.target.files?.get(0)?.let { file ->
                                                    profileImage = URL.Companion.createObjectURL(file)
                                                    props.onProfileImageChanged(file)
                                                }
                                            }
                                        }
                                    }
                                    Upload()
                                }
                            }
                            Avatar {
                                sx {
                                    width = 100.px
                                    height = 100.px
                                }
                                profileImage?.let {
                                    src = it
                                } ?: Person {
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

        input {
            hidden = true
            accept = "image/*"
            type = InputType.file
        }
    }
}
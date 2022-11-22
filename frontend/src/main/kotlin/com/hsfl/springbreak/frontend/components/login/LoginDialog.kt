package com.hsfl.springbreak.frontend.components.login

import com.hsfl.springbreak.frontend.client.Client
import com.hsfl.springbreak.frontend.client.repository.UserRepository
import com.hsfl.springbreak.frontend.client.repository.UserRepositoryImpl
import com.hsfl.springbreak.frontend.client.usecases.LoginUseCase
import com.hsfl.springbreak.frontend.client.viewmodel.LoginEvent
import com.hsfl.springbreak.frontend.client.viewmodel.LoginViewModel
import csstype.integer
import csstype.number
import csstype.px
import dom.html.HTMLButtonElement
import dom.html.HTMLDivElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props
import react.ReactNode
import react.create
import react.dom.events.FormEvent
import react.dom.events.MouseEventHandler
import react.dom.html.InputType
import react.dom.onChange
import web.events.EventType
import web.events.addEventHandler

external interface LoginDialogProps : Props {
    var open: Boolean
    var onClose: (event: dynamic, reason: String) -> Unit
}

val LoginDialog = FC<LoginDialogProps> { props ->
    val viewModel: LoginViewModel = LoginViewModel(LoginUseCase(UserRepositoryImpl(Client())), CoroutineScope(
        Dispatchers.Main + SupervisorJob()))
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
                            onChange = { event ->
                                println(event)
                                viewModel.onEvent(LoginEvent.EnteredEmail(event.target.toString()))
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
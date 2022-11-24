package com.hsfl.springbreak.frontend.components.login

import com.hsfl.springbreak.frontend.client.Client
import com.hsfl.springbreak.frontend.client.repository.UserRepositoryImpl
import com.hsfl.springbreak.frontend.client.usecases.LoginUseCase
import com.hsfl.springbreak.frontend.client.viewmodel.LoginEvent
import com.hsfl.springbreak.frontend.client.viewmodel.LoginViewModel
import com.hsfl.springbreak.frontend.components.LoadingBar
import com.hsfl.springbreak.frontend.utils.collectAsState
import csstype.number
import csstype.px
import dom.html.HTMLButtonElement
import dom.html.HTMLInputElement
import kotlinx.coroutines.*
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.events.MouseEventHandler
import react.dom.html.InputType
import react.dom.onChange

external interface LoginDialogProviderProps : Props {
    var open: Boolean
}

val LoginDialogProvider = FC<LoginDialogProviderProps> { props ->
    val viewModel = LoginViewModel(
        LoginUseCase(UserRepositoryImpl(Client()))
    )
    val openDialog = viewModel.openDialog.collectAsState()
    useEffect(props.open) {
        if (props.open) {
            println("LoginDialogProvider::${props.open}")
            viewModel.onEvent(LoginEvent.OnOpenDialog)
        }
    }

    LoginDialog {
        open = openDialog
        onClose = { event, reason ->
            viewModel.onEvent(LoginEvent.OnCloseDialog(event, reason))
        }
        onLogin = { viewModel.onEvent(LoginEvent.OnLogin) }
        onRegister = { viewModel.onEvent(LoginEvent.OnRegister) }
        onEmailTextChanged = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) }
        onPasswordTextChanged = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) }
    }
}

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
package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.ProfileEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.ProfileViewModel
import com.hsfl.springbreak.frontend.components.avatar.UploadAvatar
import com.hsfl.springbreak.frontend.components.recipe.ShowMyRecipes
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import csstype.*
import dom.html.HTMLInputElement
import mui.icons.material.Edit
import mui.icons.material.Save
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import org.kodein.di.instance
import org.w3c.dom.url.URL
import react.*
import react.dom.html.InputType
import react.dom.onChange
import web.file.File

val MyUser = FC<Props> {
    val viewModel: ProfileViewModel by di.instance()
    val editModeState = viewModel.editMode.collectAsState()
    val firstNameState = viewModel.firstNameState.collectAsState()
    val lastNameState = viewModel.lastNameState.collectAsState()
    val emailState = viewModel.emailState.collectAsState()
    val passwordState = viewModel.passwordState.collectAsState()
    val confirmedPasswordState = viewModel.confirmedPasswordState.collectAsState()
    val profileImageState = viewModel.profileImage.collectAsState()




    Typography {
        variant = TypographyVariant.h6
        +"Mein Profil"
    }
    Divider()
    ShowMyUser {
        editMode = editModeState
        firstName = firstNameState
        lastName = lastNameState
        email = emailState
        password = passwordState
        profileImage = ""
        confirmedPassword = confirmedPasswordState
        onEditButton = { viewModel.onEvent(ProfileEvent.OnEdit) }
        onSaveButton = { viewModel.onEvent(ProfileEvent.OnSave) }
        onAbort = { viewModel.onEvent(ProfileEvent.OnAbort) }
        onFirstNameChanged = { viewModel.onEvent(ProfileEvent.FirstNameChanged(it)) }
        onLastNameChanged = { viewModel.onEvent(ProfileEvent.LastNameChanged(it)) }
        onEmailChanged = { viewModel.onEvent(ProfileEvent.EmailChanged(it)) }
        onPasswordChanged = { viewModel.onEvent(ProfileEvent.PasswordChanged(it)) }
        onConfirmedPasswordChanged = { viewModel.onEvent(ProfileEvent.ConfirmedPasswordChanged(it)) }
        onProfileImageChanged = { viewModel.onEvent(ProfileEvent.ProfileImageChanged(it)) }
    }

    Typography {
        variant = TypographyVariant.h6
        +"Meine Rezepte"
    }
    Divider()
    Box {
        sx {
            marginRight = 64.px
            marginLeft = 64.px
        }
        ShowMyRecipes()
    }
}

external interface ShowMyUserProps : Props {
    var editMode: Boolean
    var firstName: String
    var lastName: String
    var email: String
    var password: String
    var confirmedPassword: String
    var profileImage: String
    var onEditButton: () -> Unit
    var onSaveButton: () -> Unit
    var onAbort: () -> Unit
    var onFirstNameChanged: (String) -> Unit
    var onLastNameChanged: (String) -> Unit
    var onEmailChanged: (String) -> Unit
    var onPasswordChanged: (String) -> Unit
    var onConfirmedPasswordChanged: (String) -> Unit
    var onProfileImageChanged: (File) -> Unit
}

val ShowMyUser = FC<ShowMyUserProps> { props ->
    Box {
        sx {
            margin = 16.px
            display = Display.flex
            justifyContent = JustifyContent.center
        }
        if (props.editMode) UploadAvatar {
            size = 150.px
            onProfileImageChanged = { props.onProfileImageChanged(it) }
        } else Avatar {
            sx {
                width = 150.px
                height = 150.px
            }
            src = props.profileImage
        }
    }
    Stack {
        sx {
            marginRight = 64.px
            marginLeft = 64.px
        }
        spacing = responsive(2)
        direction = responsive(StackDirection.column)
        TextField {
            fullWidth = true
            disabled = !props.editMode
            label = Typography.create { +"Vorname" }
            value = props.firstName
            onChange = {
                val target = it.target as HTMLInputElement
                props.onFirstNameChanged(target.value)
            }
        }
        TextField {
            fullWidth = true
            disabled = !props.editMode
            label = Typography.create { +"Nachname" }
            value = props.lastName
            onChange = {
                val target = it.target as HTMLInputElement
                props.onLastNameChanged(target.value)
            }
        }
        TextField {
            fullWidth = true
            disabled = !props.editMode
            type = InputType.email
            color = TextFieldColor.primary
            label = Typography.create { +"Email" }
            value = props.email
            onChange = {
                val target = it.target as HTMLInputElement
                props.onEmailChanged(target.value)
            }
        }
        if (props.editMode) {
            Stack {
                direction = responsive(StackDirection.row)
                spacing = responsive(2)
                TextField {
                    fullWidth = true
                    type = InputType.password
                    disabled = !props.editMode
                    color = TextFieldColor.primary
                    label = Typography.create { +"Passwort" }
                    value = props.password
                    onChange = {
                        val target = it.target as HTMLInputElement
                        props.onPasswordChanged(target.value)
                    }
                }
                TextField {
                    fullWidth = true
                    type = InputType.password
                    disabled = !props.editMode
                    color = TextFieldColor.primary
                    label = Typography.create { +"Passwort bestätigen" }
                    value = props.confirmedPassword
                    onChange = {
                        val target = it.target as HTMLInputElement
                        props.onPasswordChanged(target.value)
                    }
                }
            }
        }
    }
    Box {
        sx {
            display = Display.flex
            justifyContent = JustifyContent.end
            marginTop = 16.px
            marginRight = 64.px
        }
        if (props.editMode) {
            Button {
                onClick = { props.onAbort() }
                +"Abbrechen"
            }
        }
        Button {
            variant = ButtonVariant.contained
            startIcon = Icon.create {
                if (props.editMode) Save() else Edit()
            }
            onClick = {
                if (props.editMode) props.onSaveButton()
                else props.onEditButton()
            }
            +if (props.editMode) "Speichern" else "Bearbeiten"
        }

    }
}
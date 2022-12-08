package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.components.avatar.UploadAvatar
import com.hsfl.springbreak.frontend.components.recipe.ShowMyRecipes
import csstype.*
import mui.icons.material.Edit
import mui.icons.material.Save
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.*

val MyUser = FC<Props> {
    var editModeState by useState(false)
    Typography {
        variant = TypographyVariant.h6
        +"Mein Profil"
    }
    Divider()
    ShowMyUser {
        editMode = editModeState
        onEditButton = { editModeState = true }
        onSaveButton = { editModeState = false }
        onAbort = { editModeState = false }
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
    var profileImage: String
    var onEditButton: () -> Unit
    var onSaveButton: () -> Unit
    var onAbort: () -> Unit
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
            value = "Ryan"
        }
        TextField {
            fullWidth = true
            disabled = !props.editMode
            value = "Hughes"
            label = Typography.create { +"Nachname" }
        }
        TextField {
            fullWidth = true
            disabled = !props.editMode
            color = TextFieldColor.primary
            value = "ryan.hughes@mail-address.com"
            label = Typography.create { +"Email" }
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
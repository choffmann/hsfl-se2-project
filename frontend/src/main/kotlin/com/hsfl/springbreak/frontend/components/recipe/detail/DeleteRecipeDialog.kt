package com.hsfl.springbreak.frontend.components.recipe.detail

import mui.material.*
import react.FC
import react.Props
import react.create
import react.router.useNavigate

external interface DeleteRecipeDialogProps : Props {
    var open: Boolean
    var recipeTitle: String
    var onAbort: () -> Unit
    var onConfirm: () -> Unit
}

val DeleteRecipeDialog = FC<DeleteRecipeDialogProps> { props ->
    val navigator = useNavigate()
    Dialog {
        open = props.open
        onClose = { _, _ -> props.onAbort() }
        DialogTitle { +"Rezept '${props.recipeTitle}' löschen?" }
        DialogContent {
            DialogContentText { +"Soll dieses Rezept wirklich gelöscht werden? Diese Aktion kann nicht rückgängig gemacht werden." }
        }
        DialogActions {
            Button {
                onClick = { props.onAbort() }
                +"Abbrechen"
            }
            Button {
                variant = ButtonVariant.contained
                color = ButtonColor.error
                startIcon = Icon.create { mui.icons.material.DeleteForever() }
                onClick = {
                    props.onConfirm()
                    navigator("/")
                }
                +"Rezept löschen"
            }
        }
    }
}

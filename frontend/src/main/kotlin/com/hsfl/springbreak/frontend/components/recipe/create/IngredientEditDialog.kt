package com.hsfl.springbreak.frontend.components.recipe.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.IngredientEditDialogEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.IngredientEditDialogVM
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import mui.material.*
import org.kodein.di.instance
import react.FC
import react.Props

val IngredientEditDialog = FC<Props> {
    val viewModel: IngredientEditDialogVM by di.instance()
    val openDialog = viewModel.openDialog.collectAsState()
    val ingredientName = viewModel.ingredientName.collectAsState()
    val ingredientAmount = viewModel.ingredientAmount.collectAsState()
    val ingredientUnit = viewModel.ingredientUnit.collectAsState()

    Dialog {
        open = openDialog
        fullWidth = true
        DialogTitle {+"${viewModel.dialogTitle} bearbeiten"}
        DialogContent {
            IngredientsFormular {
                isEditing = true
                name = ingredientName
                amount = ingredientAmount
                unit = ingredientUnit
                onNameChanged = {
                    viewModel.onEvent(IngredientEditDialogEvent.OnNameChanged(it))
                }
                onAmountChanged = {
                    viewModel.onEvent(IngredientEditDialogEvent.OnAmountChanged(it))
                }
                onUnitChanged = {
                    viewModel.onEvent(IngredientEditDialogEvent.OnUnitChanged(it))
                }
            }
        }
        DialogActions {
            Button {
                onClick = {
                    viewModel.onEvent(IngredientEditDialogEvent.OnClose)
                }
                +"Abbrechen"
            }
            Button {
                variant = ButtonVariant.contained
                onClick = {
                    viewModel.onEvent(IngredientEditDialogEvent.OnSubmit)
                }
                +"Speichern"
            }
        }
    }
}
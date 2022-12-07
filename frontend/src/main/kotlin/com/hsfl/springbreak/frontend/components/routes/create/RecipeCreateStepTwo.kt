package com.hsfl.springbreak.frontend.components.routes.create

import csstype.Display
import csstype.FlexDirection
import csstype.JustifyContent
import csstype.px
import mui.icons.material.Add
import mui.material.*
import mui.system.sx
import react.FC
import react.Props
import react.create
import react.useState

data class RecipeIngredient(val name: String, val amount: Int, val unit: String)

val RecipeCreateStepTwo = FC<Props> {
    var openDialog by useState(false)
    var ingredients: List<RecipeIngredient> by useState(emptyList())

    val addIngredientsFromDialog: (List<RecipeIngredient>) -> Unit = {
        val ingredientsList: MutableList<RecipeIngredient> = ingredients.toMutableList()
        ingredientsList.addAll(it)
        ingredients = ingredientsList.toList()
    }

    Box {
        sx {
            padding = 16.px
            display = Display.flex
            flexDirection = FlexDirection.column
            justifyContent = JustifyContent.center
        }

        IngredientDialog {
            open = openDialog
            onClose = {
                openDialog = false
            }
            onFinished = {
                addIngredientsFromDialog(it)
                openDialog = false
            }
        }

        IngredientsTable {
            ingredientsList = ingredients
        }

        Button {
            sx { marginTop = 8.px }
            variant = ButtonVariant.outlined
            startIcon = Icon.create { Add() }
            onClick = { openDialog = true }
            +"Zutat hinzufügen"
        }
    }
}
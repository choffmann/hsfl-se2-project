package com.hsfl.springbreak.frontend.components.recipe.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CreateRecipeIngredientsEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.*
import com.hsfl.springbreak.frontend.di.di
import csstype.Display
import csstype.FlexDirection
import csstype.JustifyContent
import csstype.px
import mui.icons.material.Add
import mui.material.*
import mui.system.sx
import org.kodein.di.instance
import react.FC
import react.Props
import react.create

val RecipeCreateIngredients = FC<Props> {
    val viewModel: CreateRecipeIngredientsVM by di.instance()

    Box {
        sx {
            padding = 16.px
            display = Display.flex
            flexDirection = FlexDirection.column
            justifyContent = JustifyContent.center
        }

        Button {
            sx { marginTop = 8.px }
            variant = ButtonVariant.outlined
            startIcon = Icon.create { Add() }
            onClick = {
                viewModel.onEvent(CreateRecipeIngredientsEvent.OnAddIngredient)
            }
            +"Zutat hinzufügen"
        }

        IngredientDialog()
        IngredientEditDialog()
        IngredientsTable()
    }
}
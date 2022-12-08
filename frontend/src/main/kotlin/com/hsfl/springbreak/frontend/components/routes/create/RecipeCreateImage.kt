package com.hsfl.springbreak.frontend.components.routes.create

import csstype.Display
import csstype.FlexDirection
import csstype.JustifyContent
import csstype.px
import mui.icons.material.Upload
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.create

val CreateRecipeImage = FC<Props> {
    Typography {
        variant = TypographyVariant.h6
        +"Rezeptbild"
    }
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
            startIcon = Icon.create { Upload() }
            onClick = {
                //viewModel.onEvent(CreateRecipeIngredientsEvent.OnAddIngredient)
            }
            +"Bild zum rezept hochladen"
        }


    }
}
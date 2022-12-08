package com.hsfl.springbreak.frontend.components.routes.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeImageEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeImageVM
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import com.hsfl.springbreak.frontend.utils.component
import csstype.*
import kotlinx.js.get
import mui.icons.material.Upload
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import org.kodein.di.instance
import react.FC
import react.Props
import react.create
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.label

val CreateRecipeImage = FC<Props> {
    val viewModel: CreateRecipeImageVM by di.instance()
    val recipeImage = viewModel.recipeImage.collectAsState()
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
            component = label
            recipeImage?.let {
                +it.name
            } ?: +"Bild zum rezept hochladen"
            ReactHTML.input {
                hidden = true
                accept = "image/*"
                type = InputType.file
                onChange = {
                    if (it.target.files?.length != 0) {
                        it.target.files?.get(0)?.let { file ->
                            viewModel.onEvent(CreateRecipeImageEvent.SelectedFile(file))
                        }
                    }
                }
            }
        }
    }
}
package com.hsfl.springbreak.frontend.components.routes.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeDescriptionEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeDescriptionVM
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import csstype.px
import dom.html.HTMLTextAreaElement
import mui.material.TextField
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import org.kodein.di.instance
import react.FC
import react.Props
import react.dom.onChange
import react.useState

val RecipeCreateDescription = FC<Props> {
    val viewModel: CreateRecipeDescriptionVM by di.instance()
    val descriptionText = viewModel.descriptionText.collectAsState()
    var description by useState(descriptionText)
    Typography {
        variant = TypographyVariant.h6
        +"Rezeptbeschreibung"
    }
    Typography {
        variant = TypographyVariant.subtitle2
        +"Hier kannst du die Schritte für dein Rezept aufschreiben."
    }
    TextField {
        sx { marginBottom = 16.px }
        fullWidth = true
        multiline = true
        value = description
        onChange = {
            val target = it.target as HTMLTextAreaElement
            description = target.value
            viewModel.onEvent(CreateRecipeDescriptionEvent.TextChanged(target.value))
        }
    }
}
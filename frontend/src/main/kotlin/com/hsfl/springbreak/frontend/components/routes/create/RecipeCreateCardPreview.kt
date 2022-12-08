package com.hsfl.springbreak.frontend.components.routes.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeViewModel
import com.hsfl.springbreak.frontend.components.recipe.RecipeCard
import com.hsfl.springbreak.frontend.di.di
import csstype.Display
import csstype.JustifyContent
import mui.material.Box
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import org.kodein.di.instance
import react.FC
import react.Props

val RecipeCreateCardPreview = FC<Props> {
    val viewModel: CreateRecipeViewModel by di.instance()
    Typography {
        variant = TypographyVariant.h6
        +"Übersicht"
    }
    Typography {
        variant = TypographyVariant.subtitle2
        +"So wird dein Rezept aud der Seite aussehen-"
    }
    Box {
        sx {
            display = Display.flex
            justifyContent = JustifyContent.center
        }
        RecipeCard {
            title = "Ratatouille ala Remy"
            createdDate = "November 13, 2022"
            creator = "Remy Ratte"
            creatorImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRajFS3H5bfZokcLlPh6gBgLj7CAa5UU1z7sQ&usqp=CAU"
            imageSrc =
                "https://stillcracking.com/wp-content/uploads/2016/02/Ratatouille1-550x375.jpg"
            shortDescription =
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut"
            cost = "3"
            duration = "45 min"
            difficulty = "Mittel"
        }
    }
}
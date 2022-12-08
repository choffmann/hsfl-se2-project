package com.hsfl.springbreak.frontend.components.routes.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeViewModel
import com.hsfl.springbreak.frontend.components.recipe.RecipeCard
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import csstype.Display
import csstype.JustifyContent
import mui.material.Box
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import org.kodein.di.instance
import org.w3c.dom.url.URL
import react.FC
import react.Props

val RecipeCreateCardPreview = FC<Props> {
    val viewModel: CreateRecipeViewModel by di.instance()
    val recipeTitle = viewModel.recipeName.collectAsState()
    val recipeShortDesc = viewModel.recipeShortDesc.collectAsState()
    val recipePrice = viewModel.recipePrice.collectAsState()
    val recipeDuration = viewModel.recipeDuration.collectAsState()
    val recipeDifficulty = viewModel.recipeDifficulty.collectAsState()
    val recipeImage = viewModel.recipeImage.collectAsState()
    val imageBlob = recipeImage?.let { URL.Companion.createObjectURL(it) }

    Typography {
        variant = TypographyVariant.h6
        +"Übersicht"
    }
    Typography {
        variant = TypographyVariant.subtitle2
        +"So wird dein Rezept auf der Seite aussehen."
    }
    Box {
        sx {
            display = Display.flex
            justifyContent = JustifyContent.center
        }
        RecipeCard {
            title = recipeTitle.value
            createdDate = "November 13, 2022"
            creator = "My Name"
            creatorImg = ""
            imageSrc = imageBlob ?: ""
            shortDescription = recipeShortDesc.value
            cost = recipePrice.value.toString()
            duration = recipeDuration.value.toString()
            difficulty = recipeDifficulty.value
        }
    }
}
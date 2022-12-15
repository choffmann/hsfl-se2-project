package com.hsfl.springbreak.frontend.components.recipe.create

import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeViewModel
import com.hsfl.springbreak.frontend.components.recipe.RecipeCardItem
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import com.hsfl.springbreak.frontend.utils.convertToDate
import csstype.Display
import csstype.JustifyContent
import csstype.px
import mui.material.Box
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import org.kodein.di.instance
import org.w3c.dom.url.URL
import react.FC
import react.Props
import kotlin.js.Date

val RecipeCreateCardPreview = FC<Props> {
    val viewModel: CreateRecipeViewModel by di.instance()
    val recipeTitle = viewModel.recipeName.collectAsState()
    val recipeShortDesc = viewModel.recipeShortDesc.collectAsState()
    val recipePrice = viewModel.recipePrice.collectAsState()
    val recipeDuration = viewModel.recipeDuration.collectAsState()
    val recipeDifficulty = viewModel.recipeDifficulty.collectAsState()
    val recipeImage = viewModel.recipeImage.collectAsState()
    val imageBlob = recipeImage?.let { URL.Companion.createObjectURL(it) }
    val userState: UserState by di.instance()
    val myUser = userState.userState.collectAsState()

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
            paddingTop = 16.px
            paddingBottom = 16.px
        }
        RecipeCardItem {
            title = recipeTitle.value
            createdDate = Date(Date.now()).convertToDate()
            creator = "${myUser.firstName} ${myUser.lastName}"
            creatorImg = myUser.image
            imageSrc = imageBlob ?: ""
            shortDescription = recipeShortDesc.value
            cost = recipePrice.value.toString()
            duration = recipeDuration.value.toString()
            difficulty = recipeDifficulty.name /* difficultyList.find { it.id.toInt() == recipeDifficulty.value.toInt() }?.name */
        }
    }
}
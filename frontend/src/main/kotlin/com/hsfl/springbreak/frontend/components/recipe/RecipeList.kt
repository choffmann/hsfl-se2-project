package com.hsfl.springbreak.frontend.components.recipe

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.RecipeState
import csstype.Display
import csstype.JustifyContent
import csstype.number
import csstype.px
import mui.lab.Masonry
import mui.material.Box
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props

external interface RecipeListProps : Props {
    var list: List<RecipeState>
}

val RecipeList = FC<RecipeListProps> { props ->
    Box {
        sx {
            flexGrow = number(1.0)
            margin = 8.px
            display = Display.flex
            justifyContent = JustifyContent.center
        }

        Masonry {
            sx {
                minWidth = 1000.px
                maxWidth = 1400.px
            }
            columns = responsive(3)
            spacing = responsive(2)
            props.list.forEach {
                RecipeCard {
                    isMyRecipe = it.isMyRecipe
                    isFavorite = it.isMyFavorite
                    id = it.recipe.id
                    title = it.recipe.title
                    createdDate = "Bla"
                    creator = "${it.recipe.creator.firstName} ${it.recipe.creator.lastName}"
                    imageSrc = it.recipe.image ?: ""
                    shortDescription = it.recipe.shortDescription
                    cost = it.recipe.price.toInt().toString()
                    duration = it.recipe.duration.toInt().toString()
                    difficulty = it.recipe.difficulty.name
                    creatorImg = it.recipe.creator.image
                }
            }
        }
    }
}
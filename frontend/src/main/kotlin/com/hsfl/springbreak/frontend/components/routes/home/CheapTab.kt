package com.hsfl.springbreak.frontend.components.routes.home

import com.hsfl.springbreak.frontend.components.recipe.RecipeCard
import csstype.Display
import csstype.JustifyContent
import csstype.number
import csstype.px
import mui.lab.Masonry
import mui.material.Box
import mui.system.responsive
import mui.system.sx
import react.FC


val CheapTab = FC<HomeTabProps> {props ->
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
            props.recipeList.forEach {
                RecipeCard {
                    title = it.title
                    createdDate = it.createdDate
                    creator = it.creator
                    imageSrc = it.imageSrc
                    shortDescription = it.shortDescription
                    cost = it.cost
                    duration = it.duration
                    difficulty = it.difficulty
                    creatorImg = it.creatorImg
                }
            }
        }
    }
}
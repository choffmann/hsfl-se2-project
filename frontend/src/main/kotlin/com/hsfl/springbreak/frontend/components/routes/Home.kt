package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.components.recipe.RecipeCard
import csstype.JustifyContent
import csstype.number
import csstype.px
import mui.material.Box
import mui.material.Grid
import mui.material.GridDirection
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props

var Home = FC<Props> {
    Box {
        sx {
            flexGrow = number(1.0)
            margin = 8.px
        }
        Grid {
            sx { justifyContent = JustifyContent.center }
            container = true
            spacing = responsive(2)
            direction = responsive(GridDirection.row)
            for (i in 0 until 10) {
                Grid {
                    item = true
                    RecipeCard()
                }
            }
        }
    }
}
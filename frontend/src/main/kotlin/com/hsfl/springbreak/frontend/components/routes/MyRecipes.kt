package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.components.notfound.EmptyOwnRecipe
import mui.icons.material.*
import mui.material.*
import mui.material.Tab
import react.FC
import react.Props
import react.create
import react.dom.events.SyntheticEvent
import react.useState

val MyRecipes = FC<Props> {
    EmptyOwnRecipe()
}
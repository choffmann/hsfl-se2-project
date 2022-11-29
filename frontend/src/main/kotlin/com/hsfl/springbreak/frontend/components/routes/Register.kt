package com.hsfl.springbreak.frontend.components.routes

import mui.material.Box
import mui.material.Typography
import mui.material.styles.TypographyVariant
import react.FC
import react.Props

val RegisterScreen = FC<Props> {
    Box {
        Typography {
            variant = TypographyVariant.h3
            +"Erstelle dein Account"
        }
    }
}
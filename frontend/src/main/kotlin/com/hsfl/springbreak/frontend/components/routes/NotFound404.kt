package com.hsfl.springbreak.frontend.components.routes

import csstype.*
import emotion.react.css
import mui.material.Box
import mui.material.Button
import mui.material.ButtonVariant
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.dom.html.ReactHTML.img
import react.router.dom.NavLink

val NotFound404 = FC<Props> {

    Box {
        sx {
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = AlignItems.center
            flexDirection = FlexDirection.column
        }
        img {
            src =
                "https://media4.giphy.com/media/3o7aTskHEUdgCQAXde/giphy.gif?cid=ecf05e47wwyf5uolme13n99gngiql42tw8xziz1own58ywn3&rid=giphy.gif&ct=g"
        }
        Typography {
            variant = TypographyVariant.h5
            +"Ooops."
        }
        Typography {
            variant = TypographyVariant.subtitle1
            +"Das konnten wir leider nicht finden."
        }
        NavLink {
            css {
                textDecoration = None.none
                color = Color.currentcolor
            }
            to = "/"
            Button {
                variant = ButtonVariant.outlined
                sx { margin = 16.px }
                +"Zurück zur Startseite"
            }
        }
    }
}
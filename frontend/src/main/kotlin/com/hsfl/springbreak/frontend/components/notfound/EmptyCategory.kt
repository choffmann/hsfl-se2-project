package com.hsfl.springbreak.frontend.components.notfound

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
import react.dom.html.ReactHTML
import react.router.dom.NavLink
import react.router.useNavigate

val EmptyCategory = FC<Props> {
    val navigator = useNavigate()
    Box {
        sx {
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = AlignItems.center
            flexDirection = FlexDirection.column
        }
        ReactHTML.img {
            css {
                borderRadius = 8.px
                boxShadow = BoxShadow(
                    offsetX = 3.px,
                    offsetY = 3.px,
                    blurRadius = 8.px,
                    color = Color("lightgrey")
                )
            }
            src =
                "https://media3.giphy.com/media/6a67zVJ0wMMOzg3YKA/giphy.gif?cid=ecf05e475f37kkppep3n1w1i4mpsocg5fuuc10l1q06zz2iq&rid=giphy.gif&ct=g"
        }
        Typography {
            sx {
                paddingTop = 16.px
                fontWeight = FontWeight.bold
            }
            variant = TypographyVariant.h4
            +"Ohhhhh :("
        }
        Typography {
            variant = TypographyVariant.body1
            +"Diese Kategorie hat noch keine Rezepte."
        }
        Button {
            onClick = { navigator("/categories") }
            variant = ButtonVariant.outlined
            sx { margin = 16.px }
            +"Zurück zur Übersicht"
        }
    }
}
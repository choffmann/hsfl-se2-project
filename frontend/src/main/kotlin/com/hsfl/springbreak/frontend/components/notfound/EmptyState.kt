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

external interface EmptyStateProps: Props {
    var imageSrc: String
    var title: String
    var subtitle: String
    var buttonTitle: String
    var onButtonClick: () -> Unit
}
val EmptyState = FC<EmptyStateProps> { props ->
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
            src = props.imageSrc
        }
        Typography {
            sx {
                paddingTop = 16.px
                fontWeight = FontWeight.bold
            }
            variant = TypographyVariant.h4
            +props.title
        }
        Typography {
            sx { textAlign = TextAlign.center }
            variant = TypographyVariant.body1
            +props.subtitle
        }
        Button {
            onClick = { props.onButtonClick() }
            variant = ButtonVariant.outlined
            sx { margin = 16.px }
            +props.buttonTitle
        }
    }
}
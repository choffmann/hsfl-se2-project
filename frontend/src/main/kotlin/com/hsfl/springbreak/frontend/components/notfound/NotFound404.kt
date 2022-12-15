package com.hsfl.springbreak.frontend.components.notfound

import csstype.*
import emotion.react.css
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.dom.html.ReactHTML.img
import react.router.dom.NavLink
import react.router.useNavigate

val NotFound404 = FC<Props> {
    val navigator = useNavigate()
    EmptyState {
        imageSrc = "https://media4.giphy.com/media/3o7aTskHEUdgCQAXde/giphy.gif?cid=ecf05e47wwyf5uolme13n99gngiql42tw8xziz1own58ywn3&rid=giphy.gif&ct=g"
        title = "Ooops."
        subtitle = "Deine Anfrage konnten wir leider nicht bearbeiten."
        buttonTitle = "Zurück zur Startseite"
        onButtonClick = {
            navigator("/")
        }
    }
}
package com.hsfl.springbreak.frontend.components.notfound

import react.FC
import react.Props
import react.router.useNavigate

val EmptyCategory = FC<Props> {
    val navigator = useNavigate()
    EmptyState {
        imageSrc = "https://media3.giphy.com/media/6a67zVJ0wMMOzg3YKA/giphy.gif?cid=ecf05e475f37kkppep3n1w1i4mpsocg5fuuc10l1q06zz2iq&rid=giphy.gif&ct=g"
        title = "Ohhhhh :("
        subtitle = "Diese Kategorie hat noch keine Rezepte."
        buttonTitle = "Zurück zur Übersicht"
        onButtonClick = {
            navigator("/categories")
        }
    }
}